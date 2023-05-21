package brave.btc.domain.app.record;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.RecordDivision;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.util.converter.RecordDivisionToCodeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="REPORT_DVSN", discriminatorType = DiscriminatorType.STRING)
@Table(name = "USE_PERSON_RECORD")
public class Record {

	@Comment("사용개인기록ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USE_PERSON_RECORD_ID", columnDefinition = "INT NOT NULL")
	protected Integer id;

	@Comment("기록 구분")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USE_PERSON_ID", columnDefinition = "INT NOT NULL", nullable = false)
	@ToString.Exclude
	protected UsePerson usePerson;

	@Builder.Default
	@Comment("기록일")
	@Column(name = "REPORT_DATE", columnDefinition = "DATE NOT NULL", nullable = false)
	protected LocalDate reportDate = LocalDate.now();


	@Builder.Default
	@Comment("생성일시")
	@Column(name = "CREATED_AT", columnDefinition = "TIMESTAMP NOT NULL", nullable = false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@Convert(converter = RecordDivisionToCodeConverter.class)
	@Comment("기록 구분")
	@Column(name = "REPORT_DVSN", insertable = false, updatable = false)
	protected RecordDivision recordDivision;

}
