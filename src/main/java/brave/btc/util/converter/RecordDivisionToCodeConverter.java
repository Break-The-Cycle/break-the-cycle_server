package brave.btc.util.converter;

import brave.btc.constant.enums.RecordDivision;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RecordDivisionToCodeConverter implements AttributeConverter<RecordDivision, String> {

	@Override
	public String convertToDatabaseColumn(RecordDivision attribute) {
		return attribute.getCode();
	}

	@Override
	public RecordDivision convertToEntityAttribute(String dbData) {
		return RecordDivision.findByCode(dbData);
	}
}
