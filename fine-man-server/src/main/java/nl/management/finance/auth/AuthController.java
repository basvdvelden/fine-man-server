package nl.management.finance.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

@Transactional
@RestController
@RequestMapping("users/{userId}/banks/{bankId}/auth")
public class AuthController {
    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @GetMapping("/consent-url")
    public String getConsentUrl(@PathVariable("bankId") int bankId) {
        return service.getConsentUrl(bankId);
    }
}
