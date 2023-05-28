package brave.btc.domain.common.submissionrecord;

import org.hibernate.annotations.Comment;

import brave.btc.domain.bo.user.ManagePerson;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@Table(name = "COUNSELING_PERSON_SUBMISSION")
public class CounselingPersonSubmission {

	@EmbeddedId
	private CounselingPersonSubmissionId id;

	@Comment("관리 개인 ID")
	@MapsId("managePersonId")
	@JoinColumn(name = "MANAGE_PERSON_ID", columnDefinition = "INT NOT NULL")
	@ManyToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
	private ManagePerson managePerson;

	@Comment("제출 기록 ID")
	@MapsId("submissionRecordId")
	@JoinColumn(name = "SUBMISSION_RECORD_ID", columnDefinition = "INT NOT NULL")
	@OneToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
	private SubmissionRecord submissionRecord;
}
