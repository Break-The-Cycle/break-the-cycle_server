package brave.btc.constant.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ManageDivision {

	BACKOFFICE_MANAGE_PERSON("000"),COUNSELOR("001"), POLICE_OFFICER("002");
	private final String code;


	public String getCode() {
		return this.code;
	}

	static public ManageDivision findByCode(String code) {
		return Arrays.stream(values())
			.filter(value -> value.code.equals(code))
			.findAny()
			.orElse(null);
	}

	public static class Values{
		public static final String BACKOFFICE_MANAGE_PERSON = "000";
		public static final String COUNSELOR="001";
		public static final String POLICE_OFFICER= "002";
	}
}
