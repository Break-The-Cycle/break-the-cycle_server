package brave.btc.service.app.user;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.menstruation.MenstruationDto;

public interface UsePersonAndMenstruationService {

	CommonResponseDto<?> createMenstruationInfo(int usePersonId, MenstruationDto.Create mnsttCreateDto);

	CommonResponseDto<?> modifyUsePersonMenstruationPeriod(int usePersonId, int mnsttPeriod);
}
