package brave.btc.domain.app.submission_record;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.SubmissionDivision;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.util.converter.SubmissionDivisionToCodeConverter;
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

	@Comment("사용개인기록ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUBMISSION_RECORD_ID", columnDefinition = "INT NOT NULL")
	protected Integer id;

	@Comment("기록 구분")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USE_PERSON_ID", columnDefinition = "INT NOT NULL", nullable = false)
	@ToString.Exclude
	protected UsePerson usePerson;


	@Comment("제출일시")
	@Column(name = "SUBMISSION_DATETIME", columnDefinition = "TIMESTAMP NOT NULL", nullable = false)
	protected LocalDateTime submissionDatetime;


	@Comment("유효일시")
	@Column(name = "EFFECTiVE_DATETIME", columnDefinition = "TIMESTAMP NOT NULL", nullable = false)
	protected LocalDateTime effectiveDatetime;

	@Convert(converter = SubmissionDivisionToCodeConverter.class)
	@Comment("제출 기록 구분")
	@Column(name = "SUBMISSION_DVSN", insertable = false, updatable = false)
	protected SubmissionDivision submissionDivision;

}
