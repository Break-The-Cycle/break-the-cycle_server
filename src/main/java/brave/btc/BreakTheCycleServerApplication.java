package brave.btc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BreakTheCycleServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BreakTheCycleServerApplication.class, args);
	}

}
