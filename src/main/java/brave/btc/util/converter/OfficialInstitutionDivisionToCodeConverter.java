package brave.btc.util.converter;

import brave.btc.constant.enums.OfficialInstitutionDivision;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class OfficialInstitutionDivisionToCodeConverter implements AttributeConverter<OfficialInstitutionDivision, String> {

	@Override
	public String convertToDatabaseColumn(OfficialInstitutionDivision attribute) {
		return attribute.getCode();
	}

	@Override
	public OfficialInstitutionDivision convertToEntityAttribute(String dbData) {
		return OfficialInstitutionDivision.findByCode(dbData);
	}
}
