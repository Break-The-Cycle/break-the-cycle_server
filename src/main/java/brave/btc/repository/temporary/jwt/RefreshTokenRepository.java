package brave.btc.repository.temporary.jwt;

import brave.btc.domain.temporary.jwt.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByPhoneNumber(String phoneNumber);

}
