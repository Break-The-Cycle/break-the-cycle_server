package brave.btc.service.app.user;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brave.btc.domain.app.record.Menstruation;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.menstruation.MenstruationDto;
import brave.btc.exception.auth.UserPrincipalNotFoundException;
import brave.btc.exception.menstruation.InvalidMenstruationInfoException;
import brave.btc.repository.app.UsePersonRepository;
import brave.btc.repository.app.record.MenstruationRepository;
import brave.btc.repository.app.record.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UsePersonAndMenstruationServiceImpl implements UsePersonAndMenstruationService{

	private final UsePersonRepository usePersonRepository;
	private final MenstruationRepository menstruationRepository;
	private final RecordRepository recordRepository;


	@Override
	public List<MenstruationDto.Response> findMenstruationList(int usePersonId, LocalDate fromDate, LocalDate toDate) {

		//TODO : QueryDSL 로 마이그레이션
		List<Menstruation> menstruationList =
			menstruationRepository.findAllByUsePersonIdAndStartDateBetweenOrEndDateBetweenOrderByStartDateDesc(
				usePersonId, fromDate, toDate, fromDate, toDate);
		return menstruationList.stream()
			.map(Menstruation::toDto)
			.collect(Collectors.toList());
	}

	@Override
	public CommonResponseDto<?> createMenstruationInfo(int usePersonId, MenstruationDto.Create mensttCreateDto) {

		LocalDate startDate = mensttCreateDto.getStartDate();
		LocalDate endDate = mensttCreateDto.getEndDate();
		if (startDate.isAfter(endDate)) {
			throw new InvalidMenstruationInfoException("생리 시작일은 종료일보다 클 수 없습니다.");
		}

		UsePerson usePerson = usePersonRepository.findById(usePersonId)
			.orElseThrow(()->new UserPrincipalNotFoundException("해당하는 유저가 존재하지 않습니다."));

		Menstruation menstruation = mensttCreateDto.toEntity(usePerson);
		recordRepository.save(menstruation);
		log.debug("[createMenstruationInfo] menstruation 등록 완료: {}", menstruation);
		return CommonResponseDto.builder()
			.message("생리 기록이 정상적으로 등록되었습니다.")
			.build();
	}

	@Override
	public CommonResponseDto<?> modifyUsePersonMenstruationPeriod(int usePersonId, int mnsttPeriod) {

		UsePerson usePerson = usePersonRepository.findById(usePersonId)
			.orElseThrow(()->new UserPrincipalNotFoundException("해당하는 유저가 존재하지 않습니다."));
		usePerson.changeMenstruationPeriod(mnsttPeriod);
		return CommonResponseDto.builder()
			.message("유저의 생리 주기가 정상적으로 변경되었습니다.")
			.build();
	}
}
