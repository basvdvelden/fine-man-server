package nl.management.finance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "match could not be found for this uuid")
public class PinNotRegisteredException extends RuntimeException {
    public PinNotRegisteredException(String msg) {
        super(msg);
    }
}
