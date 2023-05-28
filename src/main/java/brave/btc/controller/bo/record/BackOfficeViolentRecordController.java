package brave.btc.controller.bo.record;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.record.ViolentRecordDto;
import brave.btc.exception.ErrorResponseDto;
import brave.btc.service.bo.OfficialInstitutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "22. BO_Violent record", description = "가정 폭력 기록 확인 / MANAGE_PERSON 권한 있어야 사용 가능")
@Slf4j
@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/bo-violent-records")
public class BackOfficeViolentRecordController {

	private final OfficialInstitutionService officialInstitutionService;

	@Operation(summary = "가정 폭력 기록 불러오기", description = "발행된 토큰을 이용하여 가정 폭력 기록을 조회한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "가정 폭력 기록 조회 성공",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					array = @ArraySchema(schema = @Schema(implementation = ViolentRecordDto.Response.class)))),
			@ApiResponse(responseCode = "400", description = "가정 폭력 기록 조회 실패",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class)))
		})
	@GetMapping
	public ResponseEntity<?> getViolentRecordUsingSubmissionToken(
		@RequestHeader(value = "Submission") String submissionToken
	) {

		CommonResponseDto<Object> responseDto = CommonResponseDto.builder().data(null).build();

		return ResponseEntity.ok()
			.body(responseDto);
	}
}
