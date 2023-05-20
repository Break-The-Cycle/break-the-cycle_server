package brave.btc.repository.bo;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.bo.user.OfficialInstitution;

public interface OfficialInstitutionRepository extends JpaRepository<OfficialInstitution,Integer> {
}
