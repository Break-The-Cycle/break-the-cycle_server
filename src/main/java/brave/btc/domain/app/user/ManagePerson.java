package brave.btc.domain.app.user;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
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
@Table(name = "MANAGE_PERSON")
public class ManagePerson extends User{

	@Comment("관리개인ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MANAGE_PERSON_ID", columnDefinition = "INT NOT NULL")
	private Integer id;

	@Comment("관리개인접속식별자")
	@Column(name = "MANAGE_PERSON_LGNIDN", columnDefinition = "VARCHAR(45) NOT NULL UNIQUE", nullable = false, unique = true)
	private String loginId;

	@Comment("관리개인비밀번호")
	@Column(name = "MANAGE_PERSON_PWD", columnDefinition = "VARCHAR(200) NOT NULL", nullable = false)
	private String password;

	@Comment("관리개인이름")
	@Column(name = "MANAGE_PERSON_NAME", columnDefinition = "VARCHAR(45) NOT NULL", nullable = false)
	private String name;

	@Comment("관리구분")
	@Column(name = "MANAGE_DVSN", columnDefinition = "VARCHAR(3) NOT NULL", nullable = false)
	private String division;
}
