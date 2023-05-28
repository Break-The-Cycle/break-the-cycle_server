package brave.btc.repository.app.record;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.common.submissionrecord.UsePersonSubmissionRecord;

public interface UsePersonSubmissionRecordRepository extends JpaRepository<UsePersonSubmissionRecord, Integer> {
}
