package brave.btc.domain.bo.submissionrecord;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.SubmissionDivision;
import brave.btc.domain.bo.user.UsePersonView;
import brave.btc.util.converter.SubmissionDivisionToCodeConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SUBMISSION_RECORD")
public class SubmissionRecord {

	@Comment("제출 기록 ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUBMISSION_RECORD_ID", columnDefinition = "INT NOT NULL")
	private Integer id;

	@Comment("사용 개인 ID")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USE_PERSON_ID", columnDefinition = "INT NOT NULL", nullable = false)
	@ToString.Exclude
	private UsePersonView usePersonView;


	@Comment("제출일시")
	@Column(name = "SUBMISSION_DATETIME", columnDefinition = "TIMESTAMP NOT NULL", nullable = false)
	private LocalDateTime submissionDatetime;


	@Comment("유효일시")
	@Column(name = "EFFECTiVE_DATETIME", columnDefinition = "TIMESTAMP NOT NULL", nullable = false)
	private LocalDateTime effectiveDatetime;

	@Convert(converter = SubmissionDivisionToCodeConverter.class)
	@Comment("제출 기록 구분")
	@Column(name = "SUBMISSION_DVSN", nullable = true)
	private SubmissionDivision submissionDivision;

	@Comment("사용 개인 제출 기록 리스트")
	@OneToMany(mappedBy = "submissionRecord", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private List<UsePersonSubmissionRecord> usePersonSubmissionRecordList;

	public void changeUsePersonSubmissionRecordList(List<UsePersonSubmissionRecord> usePersonSubmissionRecordList) {
		this.usePersonSubmissionRecordList = usePersonSubmissionRecordList;
	}

	public void changeSubmissionDivision(SubmissionDivision submissionDivision) {
		this.submissionDivision = submissionDivision;
	}

}
