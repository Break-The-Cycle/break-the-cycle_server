package brave.btc.domain.common.user;

import brave.btc.constant.enums.UserType;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.domain.bo.user.ManagePerson;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@Getter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public class User {

	@Transient
	private Integer id;

	@Transient
	private UserType userType;

	@Transient
	private String password;

	@Transient
	private String loginId;

	@Transient
	private String phoneNumber;

	public static User of(UsePerson usePerson) {
		return User.builder()
			.id(usePerson.getId())
			.loginId(usePerson.getLoginId())
			.password(usePerson.getPassword())
			.phoneNumber(usePerson.getPhoneNumber())
			.userType(UserType.USE_PERSON)
			.build();
	}

	public static User of(ManagePerson managePerson) {
		return User.builder()
			.id(managePerson.getId())
			.loginId(managePerson.getLoginId())
			.password(managePerson.getPassword())
			.phoneNumber(managePerson.getPhoneNumber())
			.userType(UserType.MANAGE_PERSON)
			.build();
	}
}
