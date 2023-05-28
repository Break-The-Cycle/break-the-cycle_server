package brave.btc.repository.bo;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.common.submissionrecord.CounselingPersonSubmission;
import brave.btc.domain.common.submissionrecord.CounselingPersonSubmissionId;

public interface CounselingPersonSubmissionRepository extends JpaRepository<CounselingPersonSubmission, CounselingPersonSubmissionId> {
}
