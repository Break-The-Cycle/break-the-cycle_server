package brave.btc.exception.menstruation;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidMenstruationException extends RuntimeException{

	public InvalidMenstruationException(String message) {
		super(message);
	}
}
