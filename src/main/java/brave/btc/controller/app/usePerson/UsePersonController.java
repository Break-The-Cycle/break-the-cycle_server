package brave.btc.controller.app.usePerson;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import brave.btc.dto.CommonResponseDto;
import brave.btc.service.app.user.UsePersonAndMenstruationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "03-1. Use-person", description = "앱 사용자 관련 API")
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/use-persons/{usePersonId}")
public class UsePersonController {

	private final UsePersonAndMenstruationService usePersonAndMenstruationService;


	@Operation(summary = "생리 주기 설정", description = "사용자의 생리 주기를 설정한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "생리 주기 설정 성공"),
			@ApiResponse(responseCode = "400", description = "회원 정보 불일치")
		})
	@PutMapping("/menstruation-period")
	public ResponseEntity<?> modifyMenstruationPeriod(
		@Parameter(description = "use person pk",required = true) @PathVariable("usePersonId") Integer usePersonId,
		@Positive @Parameter(description = "유저의 생리 주기",required = true) @RequestParam("newPeriod") Integer newPeriod) {

		log.debug("[modifyMenstruationPeriod] usePersonId: {} newPeriod: {}",usePersonId, newPeriod);
		CommonResponseDto<?> responseDto = usePersonAndMenstruationService.modifyUsePersonMenstruationPeriod(usePersonId, newPeriod);
		return ResponseEntity.ok()
			.body(responseDto);
	}

}
