package brave.btc.service.bo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brave.btc.domain.bo.user.OfficialInstitution;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.bo.OfficialInstitutionDto;
import brave.btc.exception.domain.EntityNotFoundException;
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
	public OfficialInstitutionDto.Response findOfficialInstitutionDetail(Integer officialInstitutionId) {
		OfficialInstitution officialInstitution = officialInstitutionRepository.findById(officialInstitutionId)
			.orElseThrow(() -> new EntityNotFoundException(OfficialInstitution.class.getName(), officialInstitutionId));
		return officialInstitution.toResponseDto();
	}

	@Override
	public List<OfficialInstitutionDto.Response> findOfficialInstitutionList() {
		return officialInstitutionRepository.findAll()
			.stream()
			.map(OfficialInstitution::toResponseDto)
			.collect(Collectors.toList());
	}

	@Override
	public CommonResponseDto<?> createOfficialInstitution(OfficialInstitutionDto.Create requestDto) {

		OfficialInstitution newOfficialInstitution = requestDto.toEntity();
		officialInstitutionRepository.save(newOfficialInstitution);
		return CommonResponseDto.builder()
			.message("공식 기관 생성이 완료되었습니다.")
			.build();
	}
}
