package brave.btc.repository.app.record;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.app.submission_record.SubmissionRecord;

public interface SubmissionRecordRepository extends JpaRepository<SubmissionRecord, Integer> {
}
