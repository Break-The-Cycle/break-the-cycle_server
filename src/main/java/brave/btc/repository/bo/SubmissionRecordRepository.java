package brave.btc.repository.bo;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.bo.submissionrecord.SubmissionRecord;

public interface SubmissionRecordRepository extends JpaRepository<SubmissionRecord, Integer> {

	@NotNull
	@EntityGraph(attributePaths = {"usePersonSubmissionRecordList"})
	Optional<SubmissionRecord> findById(@NotNull Integer id);
}
