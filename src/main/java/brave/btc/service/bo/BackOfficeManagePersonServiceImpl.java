package brave.btc.service.bo;

import brave.btc.domain.bo.user.ManagePerson;
import brave.btc.dto.common.auth.register.RegisterDto;
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
}
