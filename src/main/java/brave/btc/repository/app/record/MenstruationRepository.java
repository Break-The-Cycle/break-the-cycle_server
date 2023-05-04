package brave.btc.repository.app.record;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.app.record.Menstruation;

public interface MenstruationRepository extends JpaRepository<Menstruation,Integer>, MenstruationRepositoryCustom {


}
