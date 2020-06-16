package nl.management.finance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Pin code was incorrect!")
public class PinVerificationFailedException extends RuntimeException {
    public PinVerificationFailedException(String msg) {
        super(msg);
    }
}
