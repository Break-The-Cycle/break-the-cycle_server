package brave.btc.config.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import brave.btc.domain.app.user.UsePerson;
import brave.btc.domain.bo.user.ManagePerson;
import brave.btc.repository.app.UsePersonRepository;
import brave.btc.repository.bo.ManagePersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final UsePersonRepository usePersonRepository;
    private final ManagePersonRepository managePersonRepository;

    //TODO : ADMIN 추가하기
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("[Authentication] 로그인 진행");
        Optional<UsePerson> findUsePersonOptional = usePersonRepository.findByLoginId(loginId);
        Optional<ManagePerson> findManagePersonOptional = managePersonRepository.findByLoginId(loginId);

        if (findUsePersonOptional.isEmpty() && findManagePersonOptional.isEmpty()) {
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        } else if (findUsePersonOptional.isPresent()) {
            UsePerson findUsePerson = findUsePersonOptional.get();
            return new PrincipalDetails(findUsePerson);
        } else {
            ManagePerson findManagePerson = findManagePersonOptional.get();
            return new PrincipalDetails(findManagePerson);
        }
    }
}
