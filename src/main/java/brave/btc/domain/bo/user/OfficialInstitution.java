package brave.btc.domain.bo.user;

import java.sql.Time;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.OfficialInstitutionDivision;
import brave.btc.util.converter.OfficialInstitutionDivisionToCodeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OFFICIAL_INSTITUTION")
public class OfficialInstitution {

	@Comment("공식기관 ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OFFICIAL_INSTT_ID", columnDefinition = "INT NOT NULL")
	private Integer id;

	@Comment("공식기관 이름")
	@Column(name = "OFFICIAL_INSTT_NAME", columnDefinition = "VARCHAR(45) NOT NULL UNIQUE", nullable = false)
	private String name;

	@Comment("공식기관 전화 번호")
	@Column(name = "OFFICIAL_INSTT_PNBR", columnDefinition = "VARCHAR(18) NOT NULL UNIQUE", nullable = false)
	private String phoneNumber;

	@Convert(converter = OfficialInstitutionDivisionToCodeConverter.class)
	@Comment("공식기관 코드")
	@Column(name = "OFFICIAL_INSTT_CODE", columnDefinition = "VARCHAR(3) NOT NULL", nullable = false)
	private OfficialInstitutionDivision code;

	@Comment("기관 운영 시작 시간")
	@Column(name = "INSTT_OPRTN_START_TIME", nullable = true)
	private Time startTime;

	@Comment("기관 운영 종료 시간")
	@Column(name = "INSTT_OPRTN_END_TIME", nullable = true)
	private Time endTime;
}
