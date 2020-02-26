package ad.example.performance.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.example.performance.data.OneMb;

@RestController
@RequestMapping("/simulate")
public class SimulatorController {
	
	private static final Logger logger = LoggerFactory.getLogger(SimulatorController.class);

	private static final int MAX_TIME = 5000; // CHANGE-IT
	private static final int MIN_TIME = 100; // CHANGE-IT
	private static int NO_OF_MEM_INC_REQUEST = 0;
	private static Map<Integer, OneMb> APP_BYTES = new HashMap<Integer, OneMb>();
	

	@GetMapping(value = { "/delay", "/delay/{timeinms}" })
	public String getDelayedResponse(@PathVariable(name = "timeinms", required = false) Integer timeInMs) {
		if (timeInMs == null)
			timeInMs = 0;
		return delayResponse(timeInMs);
	}

	@GetMapping(value = { "/cpu", "/cpu/{noofiteration}" })
	public String getProcessingResponse(@PathVariable(name = "noofiteration", required = false) Integer noofiteration) {
		if (noofiteration == null)
			noofiteration = 10;
		if (noofiteration >= 75)
			noofiteration = 75;
		return "For " + noofiteration + " iterations post processing value is: " + getFibonnaci(noofiteration);
	}

	public static int getFibonnaci(long noofiteration) {
		if (noofiteration == 1 || noofiteration == 2) {
			return 1;
		}
		return getFibonnaci(noofiteration - 1) + getFibonnaci(noofiteration - 2);
	}

	@GetMapping(value = "/randomdelay")
	public String getDelayedResponse() {
		int timeInMs = new Random().nextInt((MAX_TIME - MIN_TIME) + 1) + MIN_TIME;
		return delayResponse(timeInMs);
	}

	private String delayResponse(long delayTimeInMs) {
		try {
			Thread.sleep(delayTimeInMs);
		} catch (InterruptedException e) {
			logger.error("Simulated error:", e);
		}
		return "Delayed by " + delayTimeInMs + " ms";
	}

	private String incrementMemory(int noOfBlocksMB) {
		synchronized (this) {
			for (int i = 0; i < noOfBlocksMB; i++) {
				NO_OF_MEM_INC_REQUEST++;

				if (!APP_BYTES.containsKey(NO_OF_MEM_INC_REQUEST)) {
					APP_BYTES.put(NO_OF_MEM_INC_REQUEST, new OneMb());
				}
			}
		}
		return "Memory used in " + NO_OF_MEM_INC_REQUEST + " MB";
	}

	@GetMapping(value = { "/memory", "/memory/{noofmb}" })
	public String incrementMemoryHigh(@PathVariable(name = "noofmb", required = false) Integer noOfBlocksMB) {
		if (noOfBlocksMB == null)
			noOfBlocksMB = 1;
		return incrementMemory(noOfBlocksMB);
	}

	@GetMapping(value = "/error")
	public String error() {
		try {
			generateError();
		} catch (Exception e) {
			logger.error("Simulated error:", e);
		}
		return "OK with error";
	}
	
	@GetMapping(value = "/fail")
	public String fail() throws Exception {
		generateError();
		return "OK with error";
	}

	private void generateError() throws Exception {
		try {
			int i = 10 / 0;
		} catch (Exception e) {
			throw new Exception("Custom exception", e);
		}
	}

}

