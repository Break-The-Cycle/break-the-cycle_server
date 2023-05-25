package brave.btc.service.bo;

import brave.btc.dto.common.auth.register.RegisterDto;

import java.util.List;

public interface BackOfficeManagePersonService {

    List<RegisterDto.ManagePersonResponse> findManagePersonRegisterList();

    void permissionManagerPersonRegister(Integer managePersonId);
}
