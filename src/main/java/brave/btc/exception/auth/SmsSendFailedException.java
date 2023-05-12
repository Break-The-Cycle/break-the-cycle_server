package brave.btc.exception.auth;

public class SmsSendFailedException extends RuntimeException {
    public SmsSendFailedException(String message) {
        super(message);
    }
}
