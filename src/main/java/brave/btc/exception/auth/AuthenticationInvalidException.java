package brave.btc.exception.auth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationInvalidException extends RuntimeException{
	public AuthenticationInvalidException(String message) {
		super(message);
	}
}
