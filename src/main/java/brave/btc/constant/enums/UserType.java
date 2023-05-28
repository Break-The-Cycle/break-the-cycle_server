package brave.btc.constant.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserType {

	USE_PERSON("ROLE_USE_PERSON"), MANAGE_PERSON("ROLE_MANAGE_PERSON"), BO_MANAGE_PERSON("ROLE_BO_MANAGE_PERSON");

	private final String role;

	public String getRole() {
		return this.role;
	}

}
