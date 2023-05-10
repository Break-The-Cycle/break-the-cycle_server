package brave.btc.repository.temporary.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import brave.btc.domain.temporary.jwt.RefreshToken;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

}
