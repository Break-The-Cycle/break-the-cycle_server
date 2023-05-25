package brave.btc.repository.bo;

import java.util.List;
import java.util.Optional;

import brave.btc.dto.common.auth.register.RegisterDto;
import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.bo.user.ManagePerson;
import org.springframework.data.jpa.repository.Query;

public interface ManagePersonRepository extends JpaRepository<ManagePerson, Integer> {


    Optional<ManagePerson> findByLoginId(String loginId);

    @Query("SELECT mp " +
            "FROM ManagePerson mp " +
            "WHERE mp.isAccepted = FALSE " +
            "AND mp.division != brave.btc.constant.enums.ManageDivision.BACKOFFICE_MANAGE_PERSON")
    List<ManagePerson> findNotAcceptedManagePersons();
}
