package brave.btc.domain.user;

import org.hibernate.annotations.Comment;

import brave.btc.domain.address.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "USE_PERSON")
public class UsePerson extends User{

	@Comment("사용개인ID")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USE_PERSON_ID", columnDefinition = "INT NOT NULL AUTO_INCREMENT")
	private Integer id;

	@Comment("사용개인접속식별자")
	@Column(name = "USE_PERSON_LGNIDN", columnDefinition = "VARCHAR(45) NOT NULL", unique = true)
	private String loginId;

	@Comment("사용개인이름")
	@Column(name = "USE_PERSON_NAME", columnDefinition = "VARCHAR(45) NOT NULL")
	private String name;

	@Comment("사용개인전화번호")
	@Column(name = "USE_PERSON_PNBR", columnDefinition = "VARCHAR(18) NOT NULL")
	private String phoneNumber;

	@Comment("사용개인비밀번호")
	@Column(name = "USE_PERSON_PASSWORD", columnDefinition = "VARCHAR(200) NOT NULL")
	private String password;

	@Comment("주소")
	@JoinColumn(name = "ADDRESS_ID", columnDefinition = "INT NOT NULL")
	@OneToOne(fetch= FetchType.LAZY)
	private Address address;
}
