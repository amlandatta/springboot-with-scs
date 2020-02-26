package ad.example.performance.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient("backend-app")
@FeignClient("${backend-app-name}")
public interface BackEndAppClient {
	
	@GetMapping(value = "/simulate/delay/{timeinms}")
	String getDelayedResponse(@PathVariable(name = "timeinms") Integer timeinms);
}

//@FeignClient(name="backend-app-name",url="https://backend-app.apps.pcfone.io")
//public interface BackEndAppClient {
//	
//	@GetMapping(value = "/simulate/delay/{timeinms}")
//	String getDelayedResponse(@PathVariable(name = "timeinms") Integer timeinms);
//
//}
