package brave.btc.config.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CustomPasswordEncoder implements PasswordEncoder {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final SHA256PasswordEncoder sha256PasswordEncoder;

	public CustomPasswordEncoder() {
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
		this.sha256PasswordEncoder = new SHA256PasswordEncoder();
	}

	@Override
	public String encode(CharSequence rawPassword) {

		String sha256EncodedPassword = sha256PasswordEncoder.encode(rawPassword);
		return bCryptPasswordEncoder.encode(sha256EncodedPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String sha256EncodedPassword = sha256PasswordEncoder.encode(rawPassword);
		log.debug("[matches] sha256EncodedPassword: {}", sha256EncodedPassword);
		return bCryptPasswordEncoder.matches(sha256EncodedPassword,encodedPassword);
	}

	public boolean matchesSHA256(String sha256encodedPassword, String encodedPassword) {
		return bCryptPasswordEncoder.matches(sha256encodedPassword, encodedPassword);
	}

}
