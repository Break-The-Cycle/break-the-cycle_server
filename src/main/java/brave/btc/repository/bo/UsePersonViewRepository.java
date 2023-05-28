package brave.btc.repository.bo;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.bo.user.UsePersonView;

public interface UsePersonViewRepository extends JpaRepository<UsePersonView, Integer> {
}
