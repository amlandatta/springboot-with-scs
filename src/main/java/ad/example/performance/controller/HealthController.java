package ad.example.performance.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	private static int LAST_DELAY_TIME = 0;
	
	@GetMapping(value = { "/"  })
	public String getDefault() {
		return "hello from ad";
	}

	@GetMapping(value = { "/health", "/health/{delayinms}" })
	public String getHealthWithDelay(@PathVariable(name = "delayinms", required = false) Integer timeInMs) {

		if (timeInMs == null && LAST_DELAY_TIME == 0) {
			return "OK";
		} else if (timeInMs == null && LAST_DELAY_TIME > 0) {
			timeInMs = LAST_DELAY_TIME;
		}

		LAST_DELAY_TIME = timeInMs;
		return delayResponse(timeInMs);
	}

	private String delayResponse(long delayTimeInMs) {
		try {
			Thread.sleep(delayTimeInMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Delayed by " + delayTimeInMs + " ms";
	}
}
