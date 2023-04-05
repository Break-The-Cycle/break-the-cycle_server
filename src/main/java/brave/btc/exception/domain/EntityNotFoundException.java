package brave.btc.exception.domain;

public class EntityNotFoundException extends RuntimeException{

	public EntityNotFoundException(String className, Long id) {
		super("Entity name: ".concat(className)
			.concat(" / ")
			.concat(" id: ".concat(id.toString())
				.concat(" -> db에 존재하지 않습니다.")));
	}
}
