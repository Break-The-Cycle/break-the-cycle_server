package brave.btc.exception.auth;

public class LoginIdDuplicateException extends RuntimeException{
	public LoginIdDuplicateException(String message) {
		super(message);
	}
}
