package brave.btc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aws")
public class AWSController {

	@GetMapping("/healthcheck")
	public ResponseEntity<?> elbHealthCheck() {
		return ResponseEntity.ok()
			.body("health check ok");
	}
}
