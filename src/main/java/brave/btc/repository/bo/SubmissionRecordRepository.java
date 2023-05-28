package brave.btc.repository.bo;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.bo.submissionrecord.SubmissionRecord;

public interface SubmissionRecordRepository extends JpaRepository<SubmissionRecord, Integer> {
}
