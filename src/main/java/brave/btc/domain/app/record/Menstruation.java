package brave.btc.domain.app.record;

import java.time.LocalDate;
import java.time.Period;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.MenstruationDivision;
import brave.btc.constant.enums.RecordDivision;
import brave.btc.dto.app.menstruation.MenstruationDto;
import brave.btc.util.converter.PeriodToIntegerConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

	@Builder.Default
	@Transient
	private MenstruationDivision division=MenstruationDivision.REAL;

	public MenstruationDivision getDivision() {
		if (this.division == null) {
			return MenstruationDivision.REAL;
		}
		return this.division;
	}

	public MenstruationDto.Response toDto() {
		return MenstruationDto.Response.builder()
			.id(id)
			.startDate(startDate)
			.endDate(endDate)
			.division(getDivision())
			.build();
	}
}
