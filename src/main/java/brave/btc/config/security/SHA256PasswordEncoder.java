package brave.btc.config.security;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SHA256PasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
			BigInteger numRepresentation = new BigInteger(1, digest);
			StringBuilder hashedString = new StringBuilder(numRepresentation.toString(16));
			while (hashedString.length() < 32) {
				hashedString.insert(0, "0");
			}
			return hashedString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {

		return this.encode(rawPassword).equals(encodedPassword);
	}

}
