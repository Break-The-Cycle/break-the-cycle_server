package brave.btc.util.converter;

import java.time.Period;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PeriodToIntegerConverter implements AttributeConverter<Period, Integer> {
	@Override
	public Integer convertToDatabaseColumn(Period period) {
		return period.getDays();
	}

	@Override
	public Period convertToEntityAttribute(Integer dbData) {
		return Period.ofDays(dbData);
	}
}
