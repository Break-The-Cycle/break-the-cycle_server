package brave.btc.repository.app.record;

import java.time.LocalDate;
import java.util.List;

import brave.btc.domain.app.record.Menstruation;

public interface MenstruationRepositoryCustom {
	List<Menstruation> searchMenstruationList(Integer usePersonId, LocalDate fromDate, LocalDate toDate);

	Menstruation searchLatestMenstruation(Integer usePersonId);
}
