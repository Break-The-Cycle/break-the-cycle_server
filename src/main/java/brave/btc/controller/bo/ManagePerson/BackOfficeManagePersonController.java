package brave.btc.controller.bo.ManagePerson;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.bo.OfficialInstitutionDto;
import brave.btc.dto.common.auth.register.RegisterDto;
import brave.btc.exception.ErrorResponseDto;
import brave.btc.service.bo.BackOfficeManagePersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "21. BO_Back Office Manage Person", description = "백오피스 Admin 관련 API")
@Slf4j
@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/manage-person")
public class BackOfficeManagePersonController {

    private final BackOfficeManagePersonService backOfficeManagePersonService;

    @Operation(summary = "회원 가입 요청 목록 조회", description = "요청한 회원 가입 목록을 가져온다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원 가입 요청 리스트 조회 성공",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array=@ArraySchema(schema = @Schema(implementation = RegisterDto.ManagePersonResponse.class)))),
                    @ApiResponse(responseCode = "400", description = "회원 가입 요청 리스트 조회 실패",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    @GetMapping
    public ResponseEntity<?> findManagePersonRegisterList() {
        List<RegisterDto.ManagePersonResponse> managePersonRegisterList = backOfficeManagePersonService.findManagePersonRegisterList();
        CommonResponseDto<Object> responseDto = CommonResponseDto.builder().data(managePersonRegisterList).build();
        return ResponseEntity.ok()
                .body(responseDto);
    }
}
