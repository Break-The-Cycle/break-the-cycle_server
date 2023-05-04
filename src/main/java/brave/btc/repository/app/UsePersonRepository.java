package brave.btc.repository.app;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.app.user.UsePerson;

public interface UsePersonRepository extends JpaRepository<UsePerson, Integer> {

    List<UsePerson> findByName(String username);

    Optional<UsePerson> findByLoginId(String loginId);
}
