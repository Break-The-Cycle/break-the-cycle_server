package brave.btc.controller.bo.institution;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.bo.OfficialInstitutionDto;
import brave.btc.exception.ErrorResponseDto;
import brave.btc.service.bo.OfficialInstitutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "20. BO_Official Institution", description = "공식 기관")
@Slf4j
@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/official-institutions")
public class OfficialInstitutionController {

	private final OfficialInstitutionService officialInstitutionService;

	@Operation(summary = "공식 기관 추가", description = "공식 기관을 등록한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "공식 기관 등록 성공",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = CommonResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "유효성 검사 통과 실패",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class)))
		})
	@PostMapping
	public ResponseEntity<?> createOfficialInstitution(
		@RequestBody OfficialInstitutionDto.Create requestDto
	) {

		CommonResponseDto<?> responseDto = officialInstitutionService.createOfficialInstitution(requestDto);
		return ResponseEntity.ok()
			.body(responseDto);
	}

}
