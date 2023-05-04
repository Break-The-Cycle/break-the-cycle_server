package brave.btc.controller.app.usePerson;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.record.RecordRequestDto;
import brave.btc.service.app.record.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "02. Record", description = "기록/사진/녹음/일기")
@Slf4j
@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/record")
public class RecordController {

	private final RecordService recordService;


	@Operation(summary = "기록/일기 업로드", description = "가정 폭력 사실에 대한 기록을 업로드한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "업로드 성공"),
			@ApiResponse(responseCode = "400", description = "회원 정보 불일치")
		})
	@PostMapping(consumes = {
		MediaType.MULTIPART_FORM_DATA_VALUE
	})
	public ResponseEntity<?> uploadRecord(
		 @ModelAttribute  RecordRequestDto requestDto) {

		log.info("[uploadRecord] requestDto: {}", requestDto);
		CommonResponseDto<Object> responseDto = recordService.uploadRecord(requestDto);
		return ResponseEntity.ok()
			.body(responseDto);
	}

}
