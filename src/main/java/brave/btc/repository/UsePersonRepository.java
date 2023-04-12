package brave.btc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.user.UsePerson;

public interface UsePersonRepository extends JpaRepository<UsePerson, Long> {

    List<UsePerson> findByName(String username);

    Optional<UsePerson> findByLoginId(String loginId);
}
