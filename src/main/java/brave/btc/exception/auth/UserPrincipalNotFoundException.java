package brave.btc.exception.auth;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserPrincipalNotFoundException extends RuntimeException{
	public UserPrincipalNotFoundException(String message) {
		super(message);
	}
}
