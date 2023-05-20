package brave.btc.constant.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OfficialInstitutionDivision {

	NATIONAL_POLICE_STATION("011"), SELF_FOREIGN_SERVICE("021"), SELF_COUNSELLING_AGENCY("022");

	private final String code;


	public String getCode() {
		return this.code;
	}

	static public OfficialInstitutionDivision findByCode(String code) {
		return Arrays.stream(values())
			.filter(value -> value.code.equals(code))
			.findAny()
			.orElse(null);
	}

	public static class Values{
		public static final String NATIONAL_POLICE_STATION="011";
		public static final String SELF_FOREIGN_SERVICE= "021";
		public static final String SELF_COUNSELLING_AGENCY = "022";
	}


}
