package brave.btc.domain.bo.user;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.ManageDivision;
import brave.btc.domain.bo.Address;
import brave.btc.domain.common.user.User;
import brave.btc.util.converter.ManageDivisionToCodeConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="MANAGE_DVSN", discriminatorType = DiscriminatorType.STRING)
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

<<<<<<< HEAD

	@Comment("공식 기관 주소")
	@JoinColumn(name = "ADDRESS_ID", columnDefinition = "INT NOT NULL", nullable = false)
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
	@ToString.Exclude
	private Address address;
=======
	@Comment("계정만료여부")
	@Column(name = "IS_ACCOUNT_NON_EXPIRED", columnDefinition = "TINYINT", nullable = false)
	private Boolean isAccountNonExpired=Boolean.TRUE;

	@Comment("계정잠금여부")
	@Column(name = "IS_ACCOUNT_NON_LOCKED", columnDefinition = "TINYINT", nullable = false)
	private Boolean isAccountNonLocked=Boolean.TRUE;

	@Comment("자격증명만료여부")
	@Column(name = "IS_CREDENTIALS_NON_EXPIRED", columnDefinition = "TINYINT", nullable = false)
	private Boolean isCredentialsNonExpired=Boolean.TRUE;

	@Comment("계정활성화여부")
	@Column(name = "IS_ENABLED", columnDefinition = "TINYINT", nullable = false)
	private Boolean isEnabled=Boolean.FALSE;

>>>>>>> 37bc8a5 (modify : spring security userdetails 유저 상태 4가지 추가)
}
