package brave.btc.domain.bo.user;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.ManageDivision;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@DiscriminatorValue(ManageDivision.Values.POLICE_OFFICER)
@Table(name = "POLICE_PERSON")
public class PolicePerson extends ManagePerson{

	@ToString.Exclude
	@JoinColumn(name = "OFFICIAL_INSTT_ID", columnDefinition = "INT NOT NULL",nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private OfficialInstitution officialInstitution;

	@Comment("직급")
	@Column(name = "POSITION", columnDefinition = "VARCHAR(45) NOT NULL", nullable = false)
	private String position;

	@Comment("부서")
	@Column(name = "DEPARTMENT", columnDefinition = "VARCHAR(45) NOT NULL", nullable = false)
	private String department;


}
