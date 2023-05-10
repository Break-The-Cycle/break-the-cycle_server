package brave.btc.repository.app;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.app.user.UsePerson;

public interface UsePersonRepository extends JpaRepository<UsePerson, Integer> {


    Optional<UsePerson> findByLoginId(String loginId);

    Optional<UsePerson> findByPhoneNumber(String phoneNumber);
}
