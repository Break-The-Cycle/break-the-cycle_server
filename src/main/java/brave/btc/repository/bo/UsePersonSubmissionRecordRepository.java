package brave.btc.repository.bo;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.bo.submissionrecord.UsePersonSubmissionRecord;

public interface UsePersonSubmissionRecordRepository extends JpaRepository<UsePersonSubmissionRecord, Integer> {
}
