package brave.btc.repository.persistence.record;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.persistence.record.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {
}
