package brave.btc.dto.app.record;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import brave.btc.constant.enums.RecordDivision;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@Schema(title = "폭력 피해 기록", description = "폭력 피해 정보")
public class ViolentRecordDto {

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "폭력 피해 기록 등록 dto",description = "Violent record create request dto")
	public static class Create {
		@Schema(title = "제목", example = "제목을 입력해주세요.", requiredMode = Schema.RequiredMode.REQUIRED)
		private String title;
		@Schema(title = "내용", example = "내용을 입력해주세요.", requiredMode = Schema.RequiredMode.REQUIRED)
		private String contents;

		@Schema(title = "사용자 로그인 id", example = "kang123", requiredMode = Schema.RequiredMode.REQUIRED)
		private String loginId;

		@Schema(title = "로그인, 암호화 password", example = "kang123!", requiredMode = Schema.RequiredMode.REQUIRED )
		private String password;

		@Schema(title = "첨부 사진 리스트")
		private List<MultipartFile> pictureList = new ArrayList<>();

		@Schema(title = "피해를 당한 날", requiredMode = Schema.RequiredMode.REQUIRED, implementation = LocalDate.class, example = "2023-05-19")
		private LocalDate reportDate;

		public DiaryDto.Create toDiaryDto() {
			return DiaryDto.Create.builder()
				.title(this.title)
				.contents(this.contents)
				.build();
		}
	}


	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "폭력 피해 기록 response dto",
		description = "폭력 기록에 대한 response dto 이다. diary, picture 하나 당 dto 하나 이다. ")
	public static class Response {

		@Schema(title = "record primary key")
		private Integer id;

		@Schema(title = "기록 구분자")
		private RecordDivision division;

		@Schema(title = "이미지")
		private byte[] image;
		
		@Schema(title = "일기 내용")
		private DiaryDto.Response diary;

		@Schema(title = "피해를 당한 날", example = "2023-05-19")
		private LocalDate reportDate;

	}
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "사용자 패스워드가 담긴 dto",
		description = "s3 객체를 다운로드 하기 위해서는 사용자 패스워드가 필요함. 이것을 담는 dto")
	public static class Credential {

		@Schema(title = "사용자 로그인 패스워드을 SHA256 해싱한 상태 / s3 암호화 패스워드 ", defaultValue = "AS234ASD@#234")
		private String password;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "데이터 내보내기 요청 dto",
		description = "데이터 내보내기를 통해 aws s3에서 객체가 퍼블릭 객체로 복제되어 이동됨.")
	public static class OutRequest {

		@Schema(title = "사용자 로그인 id", example = "kang123", requiredMode = Schema.RequiredMode.REQUIRED)
		private String loginId;

		@Schema(title = "로그인, 암호화 password", example = "kang123!", requiredMode = Schema.RequiredMode.REQUIRED )
		private String password;
		
		@Schema(title = "조회 시작 날짜 (생성일 기준 X  폭력 당한 날O)", example = "2023-03-01", requiredMode = Schema.RequiredMode.REQUIRED)
		private LocalDate fromDate;
		
		@Schema(title = "조회 종료 날짜 (생성일 기준 X  폭력 당한 날O)", example = "2023-07-01", requiredMode = Schema.RequiredMode.REQUIRED)
		private LocalDate toDate;

	}


	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "데이터 내보내기 응답 dto",
		description = "데이터 내보내기를 통해 aws s3에서 객체가 퍼블릭 객체로 복제되어 이동된 후 액세스 할 수 있는 제출토큰, 만료시간.")
	public static class OutResponse {

		@Schema(title = "제출 토큰", description = "데이터 내보내기 후 생성된 토큰", example = "3sd234sad@")
		private String submissionToken;

		@Schema(title = "만료 시간", description = "토큰 만료 시간")
		private LocalDateTime expireDateTime;

	}

	
}
