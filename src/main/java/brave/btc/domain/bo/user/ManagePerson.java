package brave.btc.domain.bo.user;

import brave.btc.dto.common.auth.register.RegisterDto;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.ManageDivision;
import brave.btc.domain.bo.Address;
import brave.btc.domain.common.user.User;
import brave.btc.util.converter.ManageDivisionToCodeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@ToString
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="MANAGE_DVSN", discriminatorType = DiscriminatorType.STRING)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "MANAGE_PERSON")
public class ManagePerson extends User {

	@Comment("관리개인ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MANAGE_PERSON_ID", columnDefinition = "INT NOT NULL")
	protected Integer id;

	@Comment("관리개인접속식별자")
	@Column(name = "MANAGE_PERSON_LGNIDN", columnDefinition = "VARCHAR(45) NOT NULL UNIQUE", nullable = false, unique = true)
	protected String loginId;

	@Comment("관리개인비밀번호")
	@Column(name = "MANAGE_PERSON_PWD", columnDefinition = "VARCHAR(200) NOT NULL", nullable = false)
	protected String password;

	@Comment("관리개인이름")
	@Column(name = "MANAGE_PERSON_NAME", columnDefinition = "VARCHAR(45) NOT NULL", nullable = false)
	protected String name;

	@Comment("관리개인전화번호")
	@Column(name = "MANAGE_PERSON_PNBR", columnDefinition = "VARCHAR(18) NOT NULL" , nullable = false)
	protected String phoneNumber;


	@Convert(converter = ManageDivisionToCodeConverter.class)
	@Comment("관리구분")
	@Column(name = "MANAGE_DVSN", columnDefinition = "VARCHAR(3) NOT NULL", nullable = false,  insertable = false, updatable = false)
	protected ManageDivision division;

	@Comment("회원가입 요청 시간")
	@CreatedDate
	@Column(name = "CREATED_AT", columnDefinition = "DATE", nullable = false)
	protected LocalDate createdAt;

	@Comment("관리 개인 주소")
	@JoinColumn(name = "ADDRESS_ID", columnDefinition = "INT NOT NULL", nullable = false)
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
	@ToString.Exclude
	private Address address;

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
	private Boolean isEnabled=Boolean.FALSE;


	public RegisterDto.ManagePersonResponse toResponseDto() {
		return RegisterDto.ManagePersonResponse.builder()
				.id(id)
				.manageDivision(division)
				.name(name)
				.phoneNumber(phoneNumber)
				.createdAt(createdAt)
				.build();
	}
}
