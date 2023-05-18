package brave.btc.service.bo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brave.btc.domain.bo.user.OfficialInstitution;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.bo.OfficialInstitutionDto;
import brave.btc.repository.bo.OfficialInstitutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OfficialInstitutionServiceImpl implements OfficialInstitutionService {

	private final OfficialInstitutionRepository officialInstitutionRepository;

	@Override
	public CommonResponseDto<?> createOfficialInstitution(OfficialInstitutionDto.Create requestDto) {

		OfficialInstitution newOfficialInstitution = requestDto.toOfficialInstitutionEntity();
		officialInstitutionRepository.save(newOfficialInstitution);
		return CommonResponseDto.builder()
			.message("공식 기관 생성이 완료되었습니다.")
			.build();
	}
}
