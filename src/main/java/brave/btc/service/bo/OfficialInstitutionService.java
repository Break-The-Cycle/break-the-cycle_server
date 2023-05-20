package brave.btc.service.bo;

import java.util.List;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.bo.OfficialInstitutionDto;

public interface OfficialInstitutionService {


	OfficialInstitutionDto.Response findOfficialInstitutionDetail(Integer officialInstitutionId);

	List<OfficialInstitutionDto.Response> findOfficialInstitutionList();

	CommonResponseDto<?> createOfficialInstitution(OfficialInstitutionDto.Create requestDto);
}
