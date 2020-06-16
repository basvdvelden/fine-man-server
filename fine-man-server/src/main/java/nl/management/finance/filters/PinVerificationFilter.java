package nl.management.finance.filters;

import nl.management.finance.exceptions.PinNotRegisteredException;
import nl.management.finance.exceptions.PinVerificationFailedException;
import nl.management.finance.session.SessionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class PinVerificationFilter extends GenericFilterBean {
    private static final Logger LOG = LoggerFactory.getLogger(PinVerificationFilter.class);
    // TODO: not safe
    private static final List<String> NO_FILTER = Arrays.asList("/register", "/pin/verify");
    private static final String PIN_HEADER = "Pin-Code";

    private final PinVerificationFilterIntermediary service;

    @Autowired
    public PinVerificationFilter(PinVerificationFilterIntermediary service) {
        this.service = service;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        if (shouldFilter(req)) {
            String token = req.getHeader(SessionConstants.TOKEN_HEADER).replace(SessionConstants.TOKEN_PREFIX, "");
            try {
                String pin = req.getHeader(PIN_HEADER);
                if (pin == null) {
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Pin code missing!");
                } else {
                    service.verifyPin(token, pin);
                }
                // TODO: these exceptions should be merged
            } catch (PinVerificationFailedException e) {
                LOG.warn("pin verification failed! message: {}", e.getMessage());
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid or wrong pin code!");
            } catch (PinNotRegisteredException e) {
                LOG.warn("The user corresponding to the token subject could most likely not be found because no pin was registered e.g. the user is not active");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Please register a pin code!");
            } catch (Exception e) {
                LOG.error("error while verifying pin code! ERROR:", e);
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred!");
            }
        }
        chain.doFilter(request, response);
    }

    public static boolean shouldFilter(HttpServletRequest request) {
        for (String url: NO_FILTER) {
            if (request.getRequestURI().contains(url)) {
                LOG.info("Not filtering : {}", request.getRequestURI());
                return false;
            }
        }
        return true;
    }
}
