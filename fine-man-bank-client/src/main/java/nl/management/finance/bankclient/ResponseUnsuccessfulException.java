package nl.management.finance.bankclient;

public class ResponseUnsuccessfulException extends Exception {
    public ResponseUnsuccessfulException(String msg) {
        super(msg);
    }
}
