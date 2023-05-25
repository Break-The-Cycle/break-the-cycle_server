package brave.btc.domain.bo.user;

import brave.btc.dto.bo.BackOfficeManagePerson.ManagePersonInfoDto;
import brave.btc.dto.bo.BackOfficeManagePerson.ManagePersonRegisterListDto;
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
@DiscriminatorValue(ManageDivision.Values.COUNSELOR)
@Table(name = "COUNSELING_PERSON")
public class CounselingPerson extends ManagePerson{

	@ToString.Exclude
	@JoinColumn(name = "OFFICIAL_INSTT_ID", columnDefinition = "INT NOT NULL",nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private OfficialInstitution officialInstitution;

	@Comment("직급")
	@Column(name = "POSITION", columnDefinition = "VARCHAR(45) NOT NULL", nullable = false)
	private String position;

	@Comment("프로필 소개글")
	@Column(name = "DESCRIPTION", columnDefinition = "VARCHAR(45)")
	private String description;

	@Override
	public ManagePersonRegisterListDto toResponseDto() {
		return ManagePersonRegisterListDto.builder()
				.id(id)
				.division(division)
				.officialInstitutionName(officialInstitution.getName())
				.name(name)
				.phoneNumber(phoneNumber)
				.createdAt(createdAt)
				.build();
	}

	@Override
	public ManagePersonInfoDto toInfoResponseDto() {
		return ManagePersonInfoDto.builder()
				.id(id)
				.division(division)
				.name(name)
				.phoneNumber(phoneNumber)
				.address(address)
				.description(description)
				.build();
	}
}
