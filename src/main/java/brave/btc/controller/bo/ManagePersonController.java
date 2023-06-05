package brave.btc.controller.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.record.ViolentRecordDto;
import brave.btc.dto.common.auth.UsePersonDto;
import brave.btc.exception.ErrorResponseDto;
import brave.btc.service.common.record.ViolentRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "22. BO_Manage person", description = "MANAGE_PERSON (경찰, 상담사) 사용 API")
@Slf4j
@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/manage-persons")
public class ManagePersonController {

	private final ViolentRecordService violentRecordService;


	@Operation(summary = "가정 폭력 기록 불러오기", description = "발행된 토큰을 이용하여 가정 폭력 기록을 조회한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "가정 폭력 기록 조회 성공",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					array = @ArraySchema(schema = @Schema(implementation = ViolentRecordDto.Response.class)))),
			@ApiResponse(responseCode = "400", description = "가정 폭력 기록 조회 실패",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class)))
		})
	@GetMapping("/{managePersonId}/violent-records")
	public ResponseEntity<?> getViolentRecordUsingSubmissionToken(
		@PathVariable Integer managePersonId,
		@RequestHeader(value = "Submission") String submissionToken,
		@RequestParam(required = true) Boolean usePerson,
		@RequestParam(required = true) Boolean record
	) {
		log.info("[getViolentRecordUsingSubmissionToken] submissionToken: {}", submissionToken);

		Map<String, Object> responseData = new HashMap<>();
		if (usePerson) {
			UsePersonDto.Response usePersonDto = violentRecordService.findViolentRecordUsePerson(submissionToken);
			responseData.put("usePerson", usePersonDto);
		}
		if (record) {
			List<ViolentRecordDto.Response> violentRecordList = violentRecordService.findViolentRecordList(managePersonId,submissionToken);
			responseData.put("record", violentRecordList);
		}
		CommonResponseDto<Object> responseDto = CommonResponseDto.builder().data(responseData).build();

		return ResponseEntity.ok()
			.body(responseDto);
	}



}
