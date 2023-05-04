package brave.btc.repository.app.record;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.app.record.Menstruation;

public interface MenstruationRepository extends JpaRepository<Menstruation,Integer> {

	List<Menstruation> findAllByUsePersonIdAndStartDateBetweenOrEndDateBetweenOrderByStartDateDesc(Integer usePersonId, LocalDate fromDate1, LocalDate toDate1, LocalDate fromDate2, LocalDate toDate2);
}
