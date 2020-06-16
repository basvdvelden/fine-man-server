package nl.management.finance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This user is already registered.")
public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
