package brave.btc.util.converter;

import brave.btc.constant.enums.AddressDivision;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class AddressDivisionToCodeConverter implements AttributeConverter<AddressDivision, String> {

	@Override
	public String convertToDatabaseColumn(AddressDivision attribute) {
		return attribute.getCode();
	}

	@Override
	public AddressDivision convertToEntityAttribute(String dbData) {
		return AddressDivision.findByCode(dbData);
	}
}
