package brave.btc.domain.app.submission_record;

import java.time.LocalDate;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.RecordDivision;
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
@DiscriminatorColumn(name="RECORD_DVSN", discriminatorType = DiscriminatorType.STRING)
@Table(name = "USE_PERSON_SUBMISSION_RECORD")
public class UsePersonSubmissionRecord {

	@Comment("사용 개인 기록 ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USE_PERSON_SBMSN_RECORD_ID", columnDefinition = "INT NOT NULL")
	protected Integer id;

	@Comment("기록 일자")
	@Column(name = "RECORD_DATE", columnDefinition = "DATE NOT NULL", nullable = false)
	protected LocalDate recordDate;

	@Convert(converter = RecordDivisionToCodeConverter.class)
	@Comment("기록 구분")
	@Column(name = "RECORD_DVSN", insertable = false, updatable = false)
	protected RecordDivision recordDivision;

	@ToString.Exclude
	@Comment("제출 기록")
	@JoinColumn(name = "SUBMISSION_RECORD_ID", columnDefinition = "INT NOT NULL", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	protected SubmissionRecord submissionRecord;

}
