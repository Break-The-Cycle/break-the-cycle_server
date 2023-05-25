package brave.btc.service.bo;

import brave.btc.domain.bo.user.ManagePerson;
import brave.btc.dto.common.auth.register.RegisterDto;
import brave.btc.exception.auth.UserPrincipalNotFoundException;
import brave.btc.repository.bo.ManagePersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class BackOfficeManagePersonServiceImpl implements BackOfficeManagePersonService {

    private final ManagePersonRepository managePersonRepository;

    @Override
    public List<RegisterDto.ManagePersonResponse> findManagePersonRegisterList() {
        List<ManagePerson> notAcceptedManagePersons = managePersonRepository.findNotAcceptedManagePersons();
        return notAcceptedManagePersons.stream()
                .map(ManagePerson::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void permissionManagerPersonRegister(Integer managePersonId) {
        ManagePerson managePerson = managePersonRepository.findById(managePersonId)
                .orElseThrow(() -> new UserPrincipalNotFoundException("해당하는 유저가 존재하지 않습니다."));
        managePerson.activateAccount();
        log.info("[register] 허용된 관리 개인 이름: {}", managePerson.getName());
    }
}
