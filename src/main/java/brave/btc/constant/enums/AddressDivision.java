package brave.btc.constant.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AddressDivision {

	KOREA_SEOUL("111"),
	KOREA_BUSAN("112"),
	KOREA_DAEGU("113"),
	KOREA_INCHEON("114"),
	KOREA_GWANGJU("115"),
	KOREA_DAEJEON("116"),
	KOREA_ULSAN("117"),
	KOREA_SEJONG("118"),
	KOREA_GYEONGGIDO("121"),
	KOREA_GANGWONDO("122"),
	KOREA_CHUNGCHEONGBUKDO("123"),
	KOREA_CHUNGCHEONGNAMDO("124"),
	KOREA_JEOLLABUKDO("125"),
	KOREA_JEOLLANAMDO("126"),
	KOREA_GEONGSANGBUKDO("127"),
	KOREA_GEONGSANGNAMDO("128"),
	KOREA_JEJUDO("129");

	private final String code;


	public String getCode() {
		return this.code;
	}

	static public AddressDivision findByCode(String code) {
		return Arrays.stream(values())
			.filter(value -> value.code.equals(code))
			.findAny()
			.orElse(null);
	}

	public static class Values{
		public static final String KOREA_SEOUL="111";
		public static final String KOREA_BUSAN="112";
		public static final String KOREA_DAEGU="113";
		public static final String KOREA_INCHEON="114";
		public static final String KOREA_GWANGJU="115";
		public static final String KOREA_DAEJEON="116";
		public static final String KOREA_ULSAN="117";
		public static final String KOREA_SEJONG="118";
		public static final String KOREA_GYEONGGIDO="121";
		public static final String KOREA_GANGWONDO="122";
		public static final String KOREA_CHUNGCHEONGBUKDO="123";
		public static final String KOREA_CHUNGCHEONGNAMDO="124";
		public static final String KOREA_JEOLLABUKDO="125";
		public static final String KOREA_JEOLLANAMDO="126";
		public static final String KOREA_GEONGSANGBUKDO="127";
		public static final String KOREA_GEONGSANGNAMDO="128";
		public static final String KOREA_JEJUDO="129";
	}

}
