package brave.btc.domain.app.user;

import java.time.Period;

import org.hibernate.annotations.Comment;

import brave.btc.domain.common.user.User;
import brave.btc.util.converter.PeriodToIntegerConverter;
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
import lombok.experimental.SuperBuilder;

@ToString
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USE_PERSON")
public class UsePerson extends User {

	@Comment("사용개인ID")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USE_PERSON_ID", columnDefinition = "INT NOT NULL")
	private Integer id;

	@Comment("사용개인접속식별자")
	@Column(name = "USE_PERSON_LGNIDN", columnDefinition = "VARCHAR(45) NOT NULL UNIQUE", nullable = false, unique = true)
	private String loginId;

	@Comment("사용개인비밀번호")
	@Column(name = "USE_PERSON_PASSWORD", columnDefinition = "VARCHAR(200) NOT NULL", nullable = false)
	private String password;

	@Comment("사용개인이름")
	@Column(name = "USE_PERSON_NAME", columnDefinition = "VARCHAR(45) NOT NULL" , nullable = false)
	private String name;

	@Comment("사용개인전화번호")
	@Column(name = "USE_PERSON_PNBR", columnDefinition = "VARCHAR(18) NOT NULL" , nullable = false)
	private String phoneNumber;

	@Builder.Default
	@Convert(converter= PeriodToIntegerConverter.class)
	@Comment("사용개인생리주기")
	@Column(name = "MENSTRUATION_PERIOD", columnDefinition = "INT NOT NULL DEFAULT 28" , nullable = false)
	private Period menstruationPeriod=Period.ofDays(28);

	@Comment("긴급신고내용")
	@Column(name = "EMRGN_REPORT_CONTENT", columnDefinition = "VARCHAR(200)")
	private String emergencyReportContent;

	@Builder.Default
	@Comment("계정만료여부")
	@Column(name = "IS_ACCOUNT_NON_EXPIRED", columnDefinition = "TINYINT", nullable = false)
	private Boolean isAccountNonExpired=Boolean.TRUE;

	@Builder.Default
	@Comment("계정잠금여부")
	@Column(name = "IS_ACCOUNT_NON_LOCKED", columnDefinition = "TINYINT", nullable = false)
	private Boolean isAccountNonLocked=Boolean.TRUE;

	@Builder.Default
	@Comment("자격증명만료여부")
	@Column(name = "IS_CREDENTIALS_NON_EXPIRED", columnDefinition = "TINYINT", nullable = false)
	private Boolean isCredentialsNonExpired=Boolean.TRUE;

	@Builder.Default
	@Comment("계정활성화여부")
	@Column(name = "IS_ENABLED", columnDefinition = "TINYINT", nullable = false)
	private Boolean isEnabled=Boolean.TRUE;


	public void changeMenstruationPeriod(int newPeriod) {
		this.menstruationPeriod = Period.ofDays(newPeriod);
	}
	public void changeMenstruationPeriod(Period newPeriod) {
		this.menstruationPeriod = newPeriod;
	}


}
