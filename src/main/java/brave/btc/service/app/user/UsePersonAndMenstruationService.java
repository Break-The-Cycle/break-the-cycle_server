package brave.btc.service.app.user;

import java.time.LocalDate;
import java.util.List;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.menstruation.MenstruationDto;

public interface UsePersonAndMenstruationService {

	List<MenstruationDto.Response> findMenstruationList(int usePersonId, LocalDate fromDate, LocalDate toDate);

	CommonResponseDto<?> createOnBoardMenstruationInfo(int usePersonId, MenstruationDto.OnBoardCreate mnsttOnBoardCreateDto);

	CommonResponseDto<?> createMenstruationInfo(int usePersonId, MenstruationDto.Create mnsttCreateDto);

	CommonResponseDto<?> modifyUsePersonMenstruationPeriod(int usePersonId, int mnsttPeriod);
}
