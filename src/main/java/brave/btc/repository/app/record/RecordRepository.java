package brave.btc.repository.app.record;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.app.record.Record;

public interface RecordRepository extends JpaRepository<Record, Integer>, RecordRepositoryCustom {

}
