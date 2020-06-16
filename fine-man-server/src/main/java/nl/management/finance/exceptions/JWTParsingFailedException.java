package nl.management.finance.exceptions;

public class JWTParsingFailedException extends Exception {
    public JWTParsingFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
