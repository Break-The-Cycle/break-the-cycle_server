package brave.btc.constant.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MenstruationDivision {
	REAL("실제"), EXPECTED_MENSTRUATION("예상 생리일"), EXPECTED_OVULATION("배란일"), EXPECTED_CHILDBEARING_PERIOD("가임기");

	private final String label;

	public String getLabel(){return label; }

	public MenstruationDivision findByLabel(String label){
		return Arrays.stream(values())
			.filter(value->value.label.equals(label))
			.findAny()
			.orElse(null);
	}
	
}
