package brave.btc.repository.bo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.bo.user.ManagePerson;

public interface ManagePersonRepository extends JpaRepository<ManagePerson, Integer> {


    Optional<ManagePerson> findByLoginId(String loginId);
}
