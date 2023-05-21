package brave.btc.util.converter;

import brave.btc.constant.enums.SubmissionDivision;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SubmissionDivisionToCodeConverter implements AttributeConverter<SubmissionDivision, String> {

	@Override
	public String convertToDatabaseColumn(SubmissionDivision attribute) {
		return attribute.getCode();
	}

	@Override
	public SubmissionDivision convertToEntityAttribute(String dbData) {
		return SubmissionDivision.findByCode(dbData);
	}
}
