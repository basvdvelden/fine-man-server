package nl.management.finance.filters;

import nl.management.finance.exceptions.JWTParsingFailedException;
import nl.management.finance.exceptions.PinVerificationFailedException;
import nl.management.finance.exceptions.PinNotRegisteredException;
import nl.management.finance.session.AccessTokenService;
import nl.management.finance.user.UserService;
import nl.management.finance.user.models.PinDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Intermediary for the {@link PinVerificationFilter} to be able to verify the pin-code
 */
@Service
public class PinVerificationFilterIntermediary {
    private final UserService userService;
    private final AccessTokenService accessTokenService;

    @Autowired
    public PinVerificationFilterIntermediary(UserService userService, AccessTokenService accessTokenService) {
        this.userService = userService;
        this.accessTokenService = accessTokenService;
    }

    public void verifyPin(String token, String pin) throws PinNotRegisteredException, PinVerificationFailedException, JWTParsingFailedException {
        UUID uuid = UUID.fromString(accessTokenService.extractSub(token));
        PinDto dto = new PinDto();
        dto.setPin(pin);
        userService.verifyPin(uuid, dto);
    }
}
