package nl.management.finance.filters;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import nl.management.finance.AuthContext;
import nl.management.finance.bank.Bank;
import nl.management.finance.bank.BankRepository;
import nl.management.finance.bankclient.BankClientAuthorizationException;
import nl.management.finance.session.SessionConstants;
import nl.management.finance.user.dao.UserBankRepository;
import nl.management.finance.user.dao.UserRepository;
import nl.management.finance.user.models.User;
import nl.management.finance.userbank.UserBank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JWTFilter extends GenericFilterBean {
    private static final Logger LOG = LoggerFactory.getLogger(JWTFilter.class);
    private static final Pattern BANK_ID_PATTERN = Pattern.compile("banks/([^/]*)");

    private final AuthContext context;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final UserBankRepository userBankRepository;

    public JWTFilter(AuthContext context, UserRepository userRepository, BankRepository bankRepository, UserBankRepository userBankRepository) {
        this.context = context;
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
        this.userBankRepository = userBankRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        String token = req.getHeader(SessionConstants.TOKEN_HEADER);
        if (token != null && !token.trim().isEmpty() && token.startsWith(SessionConstants.TOKEN_PREFIX)) {
            try {
                // TODO: Set path in config
                try (InputStream publicKeyStream = getClass().getClassLoader().getResourceAsStream("jwt/auth-public.der")) {
                    assert publicKeyStream != null;

                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyStream.readAllBytes());
                    KeyFactory kf = KeyFactory.getInstance("RSA");
                    PublicKey publicKey = kf.generatePublic(keySpec);
                    Claims claims = Jwts.parser()
                            .setSigningKey(publicKey)
                            .parseClaimsJws(token.replace(SessionConstants.TOKEN_PREFIX, "")).getBody();

                    setAuthContext(req, UUID.fromString(claims.getSubject()));
                    filterChain.doFilter(request, response);
                    return;
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    LOG.error("Error opening public key", e);
                } catch (BankClientAuthorizationException e) {
                    ((HttpServletResponse) response).sendError(460);
                    filterChain.doFilter(request, response);
                    return;
                }
            } catch (ExpiredJwtException exception) {
                LOG.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                LOG.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            } catch (MalformedJwtException exception) {
                LOG.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
            } catch (SignatureException exception) {
                LOG.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                LOG.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            }
        }
        LOG.warn("Token null or invalid scheme");
        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please login!");
    }

    // TODO: Handle in separate filter!!!!!!
    private void setAuthContext(HttpServletRequest req, UUID userId) {
        if (PinVerificationFilter.shouldFilter(req)) {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new BadCredentialsException("Unknown user id");
            }

            context.setUser(user);

            Matcher bankM = BANK_ID_PATTERN.matcher(req.getRequestURI());
            if (bankM.find()) {
                int bankId = Integer.parseInt(bankM.group(1));
                Optional<Bank> bank = bankRepository.findById(bankId);
                bank.ifPresent(context::setBank);

                UserBank userBank = userBankRepository.findByUserUserIdAndBankId(userId, bankId);
                if (userBank != null) {
                    context.setUserBank(userBank);
                }
            }
        }
    }
}
