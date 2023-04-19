package brave.btc.repository.record;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.record.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {
}
