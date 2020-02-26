package ad.example.performance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a2a")
public class AppToAppController {

	@Autowired
	BackEndAppClient backendAppClient;
	
	@Autowired
	Environment env;

	@GetMapping(value = "/delay/{timeinms}")
	public String getDelayedResponse(@PathVariable(name = "timeinms", required = false) Integer timeInMs) {
		return backendAppClient.getDelayedResponse(timeInMs);
	}
	
	@GetMapping(value = "/env")
	public String getEnv() {
		return env.getProperty("backend-app-name");
	}

}