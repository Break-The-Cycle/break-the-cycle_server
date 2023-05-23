package brave.btc.service.bo;

import java.util.List;

import brave.btc.dto.bo.OfficialInstitutionDto;

public interface OfficialInstitutionService {


	OfficialInstitutionDto.Response findOfficialInstitutionDetail(Integer officialInstitutionId);

	List<OfficialInstitutionDto.Response> findOfficialInstitutionList();

	String createOfficialInstitution(OfficialInstitutionDto.Create requestDto);
}
