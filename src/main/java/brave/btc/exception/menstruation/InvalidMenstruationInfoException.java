package brave.btc.exception.menstruation;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidMenstruationInfoException extends RuntimeException{

	public InvalidMenstruationInfoException(String message) {
		super(message);
	}
}
