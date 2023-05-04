package brave.btc.controller.app.usePerson;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.menstruation.MenstruationDto;
import brave.btc.service.app.user.UsePersonAndMenstruationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "03-2. Use-person & menstruation", description = "앱 사용자 생리 관련 API")
@Slf4j
@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/use-persons/{usePersonId}/menstruation")
public class MenstruationController {

	private final UsePersonAndMenstruationService usePersonAndMenstruationService;

	@Operation(summary = "생리 기록", description = "생리한 기록을 등록",
		responses = {
			@ApiResponse(responseCode = "200", description = "생리 기록 성공"),
			@ApiResponse(responseCode = "400", description = "생리 날짜 이상 / 회원 정보 불일치 ")
		})
	@PostMapping
	public ResponseEntity<?> createUsePersonRecordMenstruation(
		@Parameter(description = "use person pk",required = true) @PathVariable("usePersonId") Integer usePersonId,
		@RequestBody MenstruationDto.Create mnsttCreateDto) {

		log.debug("[createUsePersonRecordMenstruation] usePersonId: {} mnsttCreateDto: {}",usePersonId, mnsttCreateDto);
		CommonResponseDto<?> responseDto = usePersonAndMenstruationService.createMenstruationInfo(usePersonId, mnsttCreateDto);

		return ResponseEntity.ok()
			.body(responseDto);
	}


}
