package brave.btc.exception.auth;

public class SmsCertificationNumberExpiredException extends RuntimeException{
    public SmsCertificationNumberExpiredException(String message) {
        super(message);
    }
}
