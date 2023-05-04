package brave.btc.domain.app.record;

import java.time.LocalDate;
import java.time.Period;

import org.hibernate.annotations.Comment;

import brave.btc.config.enums.RecordDivision;
import brave.btc.dto.app.menstruation.MenstruationDto;
import brave.btc.util.converter.PeriodToIntegerConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue(RecordDivision.Values.MENSTRUATION)
@Table(name = "MENSTRUATION")
public class Menstruation extends Record{

	@Comment("생리시작일자")
	@Column(name = "MNSTT_START_DATE", columnDefinition = "DATE NOT NULL")
	private LocalDate startDate;

	@Comment("생리종료일자")
	@Column(name = "MNSTT_END_DATE", columnDefinition = "DATE NULL")
	private LocalDate endDate;

	@Convert(converter= PeriodToIntegerConverter.class)
	@Comment("생리기간")
	@Column(name = "MNSTT_PERIOD", columnDefinition = "INT NULL")
	private Period period;

	public MenstruationDto.Response toDto() {
		return MenstruationDto.Response.builder()
			.id(id)
			.startDate(startDate)
			.endDate(endDate)
			.build();
	}
}
