package brave.btc.domain.user;

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
@Table(name = "USE_PERSON")
public class UsePerson extends User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USE_PERSON_ID", columnDefinition = "INT NOT NULL")
	private Integer id;

	@Column(name = "USE_PERSON_NAME", columnDefinition = "VARCHAR(45) NOT NULL")
	private String name;

	@Column(name = "USE_PERSON_PNBR")
	private String phoneNumber;

	@Column(name = "USE_PERSON_LGNIDN", columnDefinition = "VARCHAR(45) NOT NULL")
	private String loginId;

	@Column(name = "USE_PERSON_PASSWORD")
	private String password;
}
