package brave.btc.service.bo;

import brave.btc.dto.bo.BackOfficeManagePerson.ManagePersonInfoDto;
import brave.btc.dto.bo.BackOfficeManagePerson.ManagePersonRegisterListDto;
import brave.btc.dto.common.auth.register.RegisterDto;

import java.util.List;

public interface BackOfficeManagePersonService {

    List<ManagePersonRegisterListDto> findManagePersonRegisterList();

    void permissionManagerPersonRegister(Integer managePersonId);

    ManagePersonInfoDto findManagePersonInfo(Integer managePersonId);
}
