package brave.btc.repository.app.record;

import java.time.LocalDate;
import java.util.List;

import brave.btc.domain.app.record.Record;

public interface RecordRepositoryCustom {

	List<LocalDate> searchViolentRecordDateList(int usePersonId, LocalDate fromDate, LocalDate toDate);

	List<Record> searchViolentRecordList(int usePersonId, LocalDate targetDate);
}
