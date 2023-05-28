package brave.btc.repository.bo;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.bo.submissionrecord.PolicePersonSubmission;
import brave.btc.domain.bo.submissionrecord.PolicePersonSubmissionId;

public interface PolicePersonSubmissionRepository extends JpaRepository<PolicePersonSubmission, PolicePersonSubmissionId> {
}
