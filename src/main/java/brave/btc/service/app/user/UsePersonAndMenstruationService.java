package brave.btc.service.app.user;

import java.time.LocalDate;
import java.util.List;

import brave.btc.dto.app.menstruation.MenstruationDto;

public interface UsePersonAndMenstruationService {

	List<MenstruationDto.Response> findMenstruationList(int usePersonId, LocalDate fromDate, LocalDate toDate);

	String createOnBoardMenstruationInfo(int usePersonId, MenstruationDto.OnBoardCreate mnsttOnBoardCreateDto);

	String createMenstruationInfo(int usePersonId, MenstruationDto.Create mnsttCreateDto);

	String modifyUsePersonMenstruationPeriod(int usePersonId, int mnsttPeriod);
}
