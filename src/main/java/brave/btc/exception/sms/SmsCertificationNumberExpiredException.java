package brave.btc.exception.sms;

public class SmsCertificationNumberExpiredException extends RuntimeException{
    public SmsCertificationNumberExpiredException(String message) {
        super(message);
    }
}
