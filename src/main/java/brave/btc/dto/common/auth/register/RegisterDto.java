package brave.btc.dto.common.auth.register;

import brave.btc.constant.enums.OfficialInstitutionDivision;
import com.fasterxml.jackson.annotation.JsonIgnore;

import brave.btc.constant.enums.ManageDivision;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.domain.bo.user.BackOfficeManagePerson;
import brave.btc.domain.bo.user.CounselingPerson;
import brave.btc.domain.bo.user.OfficialInstitution;
import brave.btc.domain.bo.user.PolicePerson;
import brave.btc.dto.bo.AddressDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@Schema(title = "회원가입", description = "회원가입 DTO ")
public class RegisterDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(title = "사용 개인 회원가입 요청", description = "사용 개인 회원가입 폼을 작성하여 요청한다.")
    public static class UsePersonCreate {

        @Schema(title = "이름", example = "홍길동")
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        private String name;

        @Schema(description = "휴대폰 번호", example = "01012345678")
        @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$",
                message = "휴대전화 형식이 올바르지 않습니다.")
        private String phoneNumber;

        @Schema(title = "로그인 ID", example = "kang123")
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Pattern(regexp = "^[a-z]+[a-zA-Z0-9]{6,19}$",
                message = "아이디는 영문 소문자로 시작하고 숫자를 포함하여 7~20자로 구성되어야 합니다.")
        private String loginId;

        @Schema(title = "로그인 패스워드", example = "kang123!")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
                message = "비밀번호는 영문 대문자, 소문자, 특수문자를 포함하여 8~20자로 구성되어야 합니다.")
        private String password;

        @Schema(description = "로그인 패스워드2", example = "kang123!")
        @NotBlank(message = "확인 비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
                message = "확인 비밀번호는 영문 대문자, 소문자, 특수문자를 포함하여 8~20자로 구성되어야 합니다.")
        private String password2;

        public UsePerson toUsePersonEntity(String encodedPassword) {
            return UsePerson.builder()
                    .name(name)
                    .loginId(loginId)
                    .password(encodedPassword)
                    .phoneNumber(phoneNumber)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(title = "관리 개인 회원가입 요청", description = "관리 개인 회원가입 폼을 작성하여 요청한다.")
    public static class ManagePersonCreate {

        @Schema(title = "이름", example = "홍경찰")
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        private String name;

        @Schema(description = "휴대폰 번호", example = "01012345678")
        @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$",
                message = "휴대전화 형식이 올바르지 않습니다.")
        private String phoneNumber;

        @Schema(title = "로그인 ID", example = "policekang123")
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Pattern(regexp = "^[a-z]+[a-zA-Z0-9]{6,19}$",
                message = "아이디는 영문 소문자로 시작하고 숫자를 포함하여 7~20자로 구성되어야 합니다.")
        private String loginId;

        @Schema(title = "로그인 패스워드", example = "police123!")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
                message = "비밀번호는 영문 대문자, 소문자, 특수문자를 포함하여 8~20자로 구성되어야 합니다.")
        private String password;

        @Schema(description = "로그인 패스워드2", example = "police123!")
        @NotBlank(message = "확인 비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
                message = "확인 비밀번호는 영문 대문자, 소문자, 특수문자를 포함하여 8~20자로 구성되어야 합니다.")
        private String password2;

        @Schema(description = "관리 개인 구분", example = "POLICE_OFFICER")
        private ManageDivision manageDivision;

        @Schema(description = "주소 생성 DTO")
        private AddressDto address;

        @Schema(description = "공식 기관 ID", example = "1")
        private Integer officialInstitutionId;

        @Schema(description = "부서", example = "외사계")
        private String department;

        @Schema(description = "직급", example = "경감")
        private String position;

        @Schema(description = "프로필 소개글", example = "안녕하세요 OOO입니다. 일산 동부 경찰서에서 근무중입니다.")
        private String description;

        public PolicePerson toPolicePersonEntity(String encodedPassword, OfficialInstitution officialInstitution) {
            return PolicePerson.builder()
                    .name(name)
                    .loginId(loginId)
                    .password(encodedPassword)
                    .phoneNumber(phoneNumber)
                    .officialInstitution(officialInstitution)
                    .department(department)
                    .position(position)
                    .address(address.toEntity())
                    .description(description)
                    .build();
        }

        public CounselingPerson toCounselingPersonEntity(String encodedPassword, OfficialInstitution officialInstitution) {
            return CounselingPerson.builder()
                    .name(name)
                    .loginId(loginId)
                    .password(encodedPassword)
                    .phoneNumber(phoneNumber)
                    .officialInstitution(officialInstitution)
                    .position(position)
                    .address(address.toEntity())
                    .description(description)
                    .build();
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(title = "백오피스 관리 개인 회원가입 요청 (Admin)", description = "백오피스 관리 개인 회원가입 폼을 작성하여 요청한다.")
    public static class BackOfficeManagePersonCreate {

        @Schema(title = "이름", example = "김어드민")
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        private String name;

        @Schema(description = "휴대폰 번호", example = "01012345678")
        @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$",
                message = "휴대전화 형식이 올바르지 않습니다.")
        private String phoneNumber;

        @Schema(title = "로그인 ID", example = "btc-admin123")
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Pattern(regexp = "^[a-z]+[a-zA-Z0-9]{6,19}$",
                message = "아이디는 영문 소문자로 시작하고 숫자를 포함하여 7~20자로 구성되어야 합니다.")
        private String loginId;

        @Schema(title = "로그인 패스워드", example = "breakthecycle123!")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
                message = "비밀번호는 영문 대문자, 소문자, 특수문자를 포함하여 8~20자로 구성되어야 합니다.")
        private String password;

        @Schema(description = "로그인 패스워드2", example = "breakthecycle123!")
        // @NotBlank(message = "확인 비밀번호는 필수 입력 값입니다.")
        // @Pattern(regexp="^(?=.*[A-Za-z])(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
        // 	message ="확인 비밀번호는 영문 대문자, 소문자, 특수문자를 포함하여 8~20자로 구성되어야 합니다.")
        private String password2;

        @Schema(description = "관리 개인 구분", example = "BACKOFFICE_MANAGE_PERSON")
        private ManageDivision manageDivision;

        @Schema(description = "주소 생성 DTO")
        private AddressDto address;

        @Schema(description = "부서", example = "btc 개발팀")
        private String department;

        @Schema(description = "직급", example = "개발자")
        private String position;

        public BackOfficeManagePerson toBackOfficeManagePersonEntity(String encodedPassword) {
            return BackOfficeManagePerson.builder()
                    .name(name)
                    .loginId(loginId)
                    .password(encodedPassword)
                    .phoneNumber(phoneNumber)
                    .address(address.toEntity())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(title = "관리 개인 회원가입 응답", description = "관리 개인 회원가입을 요청한 사람들의 목록을 보낸다.")
    public static class ManagePersonResponse {

        @Schema(description = "회원가입 된 유저 pk", example = "1")
        private Integer id;

        @Schema(description = "회원가입 요청한 유저 이름", example = "강경찰")
        private String name;

        @Schema(description = "관리 구분", example = "POLICE_OFFICER")
        private ManageDivision manageDivision;

        @Schema(description = "회원가입 요청한 유저 전화번호", example = "01012345678")
        private String phoneNumber;

        @Schema(description = "회원가입 요청한 유저 공식 기관 이름", example = "일산 동부 경찰서")
        private String officialInstitutionName;

        @Schema(description = "회원가입 요청한 날짜", example = "2023-05-23")
        private LocalDate createdAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(title = "백오피스 관리 개인 회원가입 응답 (Admin)", description = "백오피스 관리 개인 회원가입에 대한 응답 메세지를 전달한다.")
    public static class Response {

        @JsonIgnore
        private String message;

        @Schema(description = "회원가입 된 유저 pk", example = "1")
        private Integer id;

    }
}
