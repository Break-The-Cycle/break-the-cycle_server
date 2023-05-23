package brave.btc.controller.app.usePerson;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.menstruation.MenstruationDto;
import brave.btc.exception.ErrorResponseDto;
import brave.btc.service.app.user.UsePersonAndMenstruationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "10-1. APP_Use-person", description = "앱 사용자 관련 API")
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/use-persons/{usePersonId}")
public class UsePersonController {

	private final UsePersonAndMenstruationService usePersonAndMenstruationService;

	@Operation(summary = "온보딩", description = "사용자의 생리 주기와 초기 생리 기록을 생성한다. (온보딩)",
		responses = {
			@ApiResponse(responseCode = "200", description = "온보딩 데이터 등록 성공",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = CommonResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "회원 정보 불일치",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class)))
		})
	@PostMapping("/on-board")
	public ResponseEntity<?> createUsePersonOnBoard(
		@Parameter(description = "사용자의 pk",required = true) @PathVariable("usePersonId") Integer usePersonId,
		@RequestBody MenstruationDto.OnBoardCreate onBoardCreateReqDto) {

		log.debug("[modifyMenstruationPeriod] usePersonId: {} onBoardCreateReqDto: {}",usePersonId, onBoardCreateReqDto);
		String message = usePersonAndMenstruationService.createOnBoardMenstruationInfo(usePersonId, onBoardCreateReqDto);
		CommonResponseDto<Object> responseDto = CommonResponseDto.builder().message(message).build();

		return ResponseEntity.ok()
			.body(responseDto);
	}

	@Operation(summary = "생리 주기 설정", description = "사용자의 생리 주기를 설정한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "생리 주기 설정 성공",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = CommonResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "회원 정보 불일치",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class)))
		})
	@PutMapping("/menstruation-period")
	public ResponseEntity<?> modifyMenstruationPeriod(
		@Parameter(description = "use person pk",required = true) @PathVariable("usePersonId") Integer usePersonId,
		@Positive @Parameter(description = "유저의 생리 주기",required = true, example = "28") @RequestParam(value="period",defaultValue = "28") Integer period) {

		log.debug("[modifyMenstruationPeriod] usePersonId: {} newPeriod: {}",usePersonId, period);
		String message = usePersonAndMenstruationService.modifyUsePersonMenstruationPeriod(usePersonId, period);
		CommonResponseDto<Object> responseDto = CommonResponseDto.builder().message(message).build();

		return ResponseEntity.ok()
			.body(responseDto);
	}

}
