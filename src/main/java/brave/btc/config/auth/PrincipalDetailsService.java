package brave.btc.config.auth;

import brave.btc.domain.app.user.UsePerson;
import brave.btc.repository.app.UsePersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final UsePersonRepository usePersonRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("[Authentication] 로그인 진행");
        UsePerson findUsePerson = usePersonRepository.findByLoginId(loginId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("존재하지 않는 아이디입니다."));

        return new PrincipalDetails(findUsePerson);
    }
}
