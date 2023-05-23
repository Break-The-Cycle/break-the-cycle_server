package brave.btc.service.app.user;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brave.btc.constant.enums.MenstruationDivision;
import brave.btc.domain.app.record.Menstruation;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.dto.app.menstruation.MenstruationDto;
import brave.btc.exception.auth.UserPrincipalNotFoundException;
import brave.btc.exception.menstruation.InvalidMenstruationException;
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

		UsePerson usePerson = usePersonRepository.findById(usePersonId)
			.orElseThrow(()->new UserPrincipalNotFoundException("해당하는 유저가 존재하지 않습니다."));

		List<Menstruation> menstruationList =
			menstruationRepository.searchMenstruationList(usePersonId, fromDate, toDate);

		Period menstruationPeriod = usePerson.getMenstruationPeriod();
		List<MenstruationDto.Response> responseDtoList = menstruationList.stream()
			.map(Menstruation::toDto)
			.collect(Collectors.toList());

		Menstruation latestMenstruation = menstruationRepository.searchLatestMenstruation(usePersonId); //가장 최신 생리 기록
		if (latestMenstruation == null) {
			throw new InvalidMenstruationException("유저의 생리 기록이 존재하지 않습니다.");
		}
		LocalDate latestStartDate = latestMenstruation.getStartDate();
		calculateExpectedMenstruationInfo(responseDtoList, menstruationPeriod, latestStartDate, fromDate, toDate);
		return responseDtoList;
	}

	/**
	 * 사용자의 생리주기, 가장 최근 생리일을 기준으로 예상 가임기, 배란일, 생리일을 계산한다.
	 * @param responseDtoList 반환할 response dto list
	 * @param menstruationPeriod 사용자 생리 주기
	 * @param latestStartDate 최근 생리일
	 */
	private void calculateExpectedMenstruationInfo(List<MenstruationDto.Response> responseDtoList,

		Period menstruationPeriod, LocalDate latestStartDate, LocalDate fromDate, LocalDate toDate) {
		LocalDate expectedMenstruationDate = latestStartDate;
		//가장 가까운 미래에 생리를 할 날짜를 결정
		while (expectedMenstruationDate.isBefore(fromDate)) {
			expectedMenstruationDate = expectedMenstruationDate.plus(menstruationPeriod);
		}
		int halfMenstruationPeriodDays = menstruationPeriod.getDays()/2;
		LocalDate expectedOvulationDate = expectedMenstruationDate.minusDays(halfMenstruationPeriodDays);
		LocalDate fromExpectedChildBearing = expectedOvulationDate.minusDays(3);
		LocalDate toExpectedChildBearing = expectedOvulationDate.plusDays(3);

		MenstruationDto.Response expectedChildBearingResponseDto = MenstruationDto.Response.builder()
			.startDate(fromExpectedChildBearing)
			.endDate(toExpectedChildBearing)
			.division(MenstruationDivision.EXPECTED_CHILDBEARING_PERIOD)
			.build();
		responseDtoList.add(0, expectedChildBearingResponseDto);

		MenstruationDto.Response expectedOvulationResponseDto = MenstruationDto.Response.builder()
					.startDate(expectedOvulationDate)
					.endDate(null)
					.division(MenstruationDivision.EXPECTED_OVULATION)
					.build();
		responseDtoList.add(0, expectedOvulationResponseDto);

		MenstruationDto.Response expectedMenstruationResponseDto = MenstruationDto.Response.builder()
					.startDate(expectedMenstruationDate)
					.endDate(expectedMenstruationDate.plusDays(7))
					.division(MenstruationDivision.EXPECTED_MENSTRUATION)
					.build();
		responseDtoList.add(0, expectedMenstruationResponseDto);
	}

	@Override
	public String createMenstruationInfo(int usePersonId, MenstruationDto.Create mensttCreateDto) {

		LocalDate startDate = mensttCreateDto.getStartDate();
		LocalDate endDate = mensttCreateDto.getEndDate();
		if (startDate.isAfter(endDate)) {
			throw new InvalidMenstruationException("생리 시작일은 종료일보다 클 수 없습니다.");
		}

		UsePerson usePerson = usePersonRepository.findById(usePersonId)
			.orElseThrow(()->new UserPrincipalNotFoundException("해당하는 유저가 존재하지 않습니다."));

		Menstruation menstruation = mensttCreateDto.toEntity(usePerson);
		recordRepository.save(menstruation);
		log.debug("[createMenstruationInfo] menstruation 등록 완료: {}", menstruation);
		return "생리 기록이 정상적으로 등록되었습니다.";
	}

	@Override
	public String modifyUsePersonMenstruationPeriod(int usePersonId, int mnsttPeriod) {

		UsePerson usePerson = usePersonRepository.findById(usePersonId)
			.orElseThrow(()->new UserPrincipalNotFoundException("해당하는 유저가 존재하지 않습니다."));
		usePerson.changeMenstruationPeriod(mnsttPeriod);
		return "유저의 생리 주기가 정상적으로 변경되었습니다.";
	}

	@Override
	public String createOnBoardMenstruationInfo(int usePersonId, MenstruationDto.OnBoardCreate mnsttOnBoardCreateDto) {

		Integer newPeriod = mnsttOnBoardCreateDto.getPeriod();

		//TODO: 유저 상태 단계에 따라서 온보딩 메서드를 다시 실행할지 말지 결정

		modifyUsePersonMenstruationPeriod(usePersonId, newPeriod);
		createMenstruationInfo(usePersonId, mnsttOnBoardCreateDto.toCreateDto());
		return "유저 온보딩 정보가 정상적으로 등록되었습니다.";
	}
}
