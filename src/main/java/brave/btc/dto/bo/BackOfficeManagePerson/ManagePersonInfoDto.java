package brave.btc.dto.bo.BackOfficeManagePerson;

import brave.btc.constant.enums.ManageDivision;
import brave.btc.domain.bo.Address;
import brave.btc.dto.bo.AddressDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "관리 개인 정보 조회", description = "관리 개인 정보를 조회한다.")
public class ManagePersonInfoDto {

    @Schema(description = "관리 개인 pk", example = "1")
    private Integer id;

    @Schema(description = "관리 개인 이름", example = "1")
    private String name;

    @Schema(description = "관리 개인 소속", example = "1")
    private ManageDivision division;

    @Schema(description = "관리 개인 전화번호", example = "1")
    private String phoneNumber;

    @Schema(description = "관리 개인 주소", example = "1")
    private Address address;

    @Schema(description = "관리 개인 프로필 소개글", example = "1")
    private String description;

}
