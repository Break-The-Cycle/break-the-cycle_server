package brave.btc.exception.sms;

public class SmsCertificationNumberNotSameException extends RuntimeException{
    public SmsCertificationNumberNotSameException(String message) {
        super(message);
    }
}
