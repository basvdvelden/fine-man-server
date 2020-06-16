package nl.management.finance.bankclient;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Bank authentication info invalid or missing!")
public class BankClientAuthorizationException extends RuntimeException {
}
