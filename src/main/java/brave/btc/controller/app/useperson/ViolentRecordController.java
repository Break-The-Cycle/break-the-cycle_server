package brave.btc.controller.app.useperson;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.record.ViolentRecordDto;
import brave.btc.exception.ErrorResponseDto;
import brave.btc.service.common.record.ViolentRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "11. APP_Violent Record", description = "기록/사진/녹음/일기")
@Slf4j
@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/violent-records")
public class ViolentRecordController {

	private final ViolentRecordService violentRecordService;

	@Operation(summary = "폭력 일기 기록 범위만큼 불러오기 (내용은 포함 x)", description = "업로드한 폭력 일기 기록을 fromDate ~ toDate에 속해있는 날짜만 불러온다. 자세한 내용은 포함하지 않는다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "폭력 일기 날짜 리스트 가져오기 성공",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					array = @ArraySchema(schema = @Schema(implementation = LocalDate.class)))),
			@ApiResponse(responseCode = "400", description = "조회 실패",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "내부 오류 / 조회 실패",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class)))
		})
	@GetMapping("/{usePersonId}/dates")
	public ResponseEntity<?> violentRecordList(
		@Parameter(description = "use person pk",required = true) @PathVariable("usePersonId") Integer usePersonId,
		@Parameter(description = "조회 시작 날짜",required = true) @RequestParam(name = "fromDate") LocalDate fromDate,
		@Parameter(description = "조회 종료 날짜",required = true) @RequestParam(name = "toDate") LocalDate toDate) {

		log.info("[violentRecordList] usePersonId: {} fromDate: {} toDate: {}", usePersonId, fromDate, toDate);

		List<LocalDate> violentRecordDateList = violentRecordService.findViolentRecordDateList(usePersonId, fromDate, toDate);
		CommonResponseDto<Object> responseDto = CommonResponseDto.builder().data(violentRecordDateList).build();

		return ResponseEntity.ok()
			.body(responseDto);
	}


	@Operation(summary = "특정 날짜 폭력 일기 기록 불러오기 (내용 포함 o)"
		, description = "업로드한 폭력 일기 기록을 targetDate에 속해있는 날짜 것을 불러온다. 자세한 내용은 포함한다. *비밀번호를 암호화 하기 위해 POST 요청을 사용한다.*",
		responses = {
			@ApiResponse(responseCode = "200", description = "폭력 일기 기록 자세히 가져오기 성공",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					array = @ArraySchema(schema = @Schema(implementation = LocalDate.class)))),
			@ApiResponse(responseCode = "400", description = "조회 실패",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "내부 오류 / 조회 실패",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class)))
		})
	@PostMapping("/{usePersonId}")
	public ResponseEntity<?> violentRecordDetails(
		@Parameter(description = "use person pk",required = true) @PathVariable("usePersonId") Integer usePersonId,
		@Parameter(description = "조회 날짜",required = true) @RequestParam(name = "targetDate") LocalDate targetDate,
		@RequestBody(required = true) ViolentRecordDto.Credential credential) {

		log.info("[violentRecordDetails] usePersonId: {} targetDate: {}", usePersonId,targetDate);

		List<ViolentRecordDto.Response> violentRecordList = violentRecordService.findViolentRecordList(usePersonId, targetDate, credential);
		CommonResponseDto<Object> responseDto = CommonResponseDto.builder().data(violentRecordList).build();

		return ResponseEntity.ok()
			.body(responseDto);
	}

	@Operation(summary = "폭력 기록/일기 업로드", description = "가정 폭력 사실에 대한 기록을 업로드한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "업로드 성공",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = CommonResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "회원 정보 불일치",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "aws 업로드 실패",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class)))
		})
	@PostMapping(consumes = {
		MediaType.MULTIPART_FORM_DATA_VALUE
	})
	public ResponseEntity<?> ViolentRecordUpload(
		 @ModelAttribute ViolentRecordDto.Create requestDto) {

		log.info("[uploadRecord] requestDto: {}", requestDto);
		String message = violentRecordService.uploadViolentRecord(requestDto);
		CommonResponseDto<Object> responseDto = CommonResponseDto.builder().message(message).build();

		return ResponseEntity.ok()
			.body(responseDto);
	}


	@Operation(summary = "폭력 기록/일기 내보내기", description = "가정 폭력 사실에 대한 기록을 내보내기한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "내보내기 성공",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ViolentRecordDto.OutResponse.class))),
			@ApiResponse(responseCode = "400", description = "회원 정보 불일치",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "aws에서 내보내기 실패",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class)))
		})
	@PostMapping("/out")
	public ResponseEntity<?> ViolentRecordOut(
		@RequestBody ViolentRecordDto.OutRequest requestDto) {

		log.info("[ViolentRecordOut] requestDto: {}", requestDto);
		ViolentRecordDto.OutResponse outResponse = violentRecordService.outViolentRecord(requestDto);
		CommonResponseDto<Object> responseDto = CommonResponseDto.builder().data(outResponse).build();

		return ResponseEntity.ok()
			.body(responseDto);
	}




}
