package brave.btc.constant.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RecordDivision {

	DIARY("001"), RECORDING("002"), PICTURE("003"), SEX("004"), EMOTION("005"), MENSTRUATION("006");

	private final String code;

	public String getCode() {
		return this.code;
	}

	static public RecordDivision findByCode(String code) {
		return Arrays.stream(values())
			.filter(value -> value.code.equals(code))
			.findAny()
			.orElse(null);
	}
}
