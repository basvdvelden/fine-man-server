package nl.management.finance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This user is not registered.")
public class UserNotRegisteredException extends RuntimeException {
    public UserNotRegisteredException(String msg) {
        super(msg);
    }
}
