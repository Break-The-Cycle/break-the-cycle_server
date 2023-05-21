package brave.btc.domain.common.user;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.UserType;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.domain.bo.user.ManagePerson;
import jakarta.persistence.Column;
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

	@Transient
	private Boolean isAccountNonExpired;

	@Transient
	private Boolean isAccountNonLocked;

	@Transient
	private Boolean isCredentialsNonExpired;

	@Transient
	private Boolean isEnabled;

	public static User of(UsePerson usePerson) {
		return User.builder()
			.id(usePerson.getId())
			.loginId(usePerson.getLoginId())
			.password(usePerson.getPassword())
			.phoneNumber(usePerson.getPhoneNumber())
			.userType(UserType.USE_PERSON)
			.isAccountNonExpired(usePerson.getIsAccountNonExpired())
			.isAccountNonLocked(usePerson.getIsAccountNonLocked())
			.isCredentialsNonExpired(usePerson.getIsCredentialsNonExpired())
			.isEnabled(usePerson.getIsEnabled())
			.build();
	}

	public static User of(ManagePerson managePerson) {
		return User.builder()
			.id(managePerson.getId())
			.loginId(managePerson.getLoginId())
			.password(managePerson.getPassword())
			.phoneNumber(managePerson.getPhoneNumber())
			.userType(UserType.MANAGE_PERSON)
			.isAccountNonExpired(managePerson.getIsAccountNonExpired())
			.isAccountNonLocked(managePerson.getIsAccountNonLocked())
			.isCredentialsNonExpired(managePerson.getIsCredentialsNonExpired())
			.isEnabled(managePerson.getIsEnabled())
			.build();
	}
}
