package brave.btc.repository.app.record;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepositoryCustom {

	List<String> searchSimpleViolentRecordList(int usePersonId, LocalDate fromDate, LocalDate toDate);
}
