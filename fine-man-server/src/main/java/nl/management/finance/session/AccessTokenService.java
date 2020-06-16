package nl.management.finance.session;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import nl.management.finance.exceptions.JWTParsingFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Service
public class AccessTokenService {
    private static final Logger LOG = LoggerFactory.getLogger(AccessTokenService.class);

    public AccessTokenService() {}

    public String extractSub(String accessToken) throws JWTParsingFailedException {
        Jws<Claims> parsedToken = getClaims(accessToken);
        return parsedToken.getBody().getSubject();
    }

    private Jws<Claims> getClaims(String accessToken) throws JWTParsingFailedException {
        try (InputStream publicKeyStream = getClass().getClassLoader().getResourceAsStream("jwt/auth-public.der")) {
            assert publicKeyStream != null;

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyStream.readAllBytes());
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publicKey = kf.generatePublic(keySpec);
            return Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(accessToken);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOG.error("Exception was thrown while parsing jwt to get claims. ERROR:", e);
            throw new JWTParsingFailedException("Error parsing jwt", e);
        }
    }
}
