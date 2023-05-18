package brave.btc.service.bo;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.bo.OfficialInstitutionDto;

public interface OfficialInstitutionService {

	CommonResponseDto<?> createOfficialInstitution(OfficialInstitutionDto.Create requestDto);
}
