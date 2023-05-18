package brave.btc.util.converter;

import brave.btc.constant.enums.ManageDivision;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ManageDivisionToCodeConverter implements AttributeConverter<ManageDivision, String> {

	@Override
	public String convertToDatabaseColumn(ManageDivision attribute) {
		return attribute.getCode();
	}

	@Override
	public ManageDivision convertToEntityAttribute(String dbData) {
		return ManageDivision.findByCode(dbData);
	}
}
