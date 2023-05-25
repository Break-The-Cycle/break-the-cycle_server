package brave.btc.dto.bo.BackOfficeManagePerson;

import brave.btc.constant.enums.ManageDivision;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "관리 개인 회원가입 응답", description = "관리 개인 회원가입을 요청한 사람들의 목록을 보낸다.")
public class ManagePersonRegisterListDto {

    @Schema(description = "회원가입 된 유저 pk", example = "1")
    private Integer id;

    @Schema(description = "회원가입 요청한 유저 이름", example = "강경찰")
    private String name;

    @Schema(description = "관리 구분", example = "POLICE_OFFICER")
    private ManageDivision division;

    @Schema(description = "회원가입 요청한 유저 전화번호", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "회원가입 요청한 유저 공식 기관 이름", example = "일산 동부 경찰서")
    private String officialInstitutionName;

    @Schema(description = "회원가입 요청한 날짜", example = "2023-05-23")
    private LocalDate createdAt;

}
