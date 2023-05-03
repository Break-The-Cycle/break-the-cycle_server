package brave.btc.repository.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.persistence.user.UsePerson;

public interface UsePersonRepository extends JpaRepository<UsePerson, Integer> {

    List<UsePerson> findByName(String username);

    Optional<UsePerson> findByLoginId(String loginId);
}
