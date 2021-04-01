package NightTimeTemperature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "/night-time-temperature")
public class TwilightTempController {

	@Bean
	public TwilightTemperature getTempObj() {
		return new TwilightTemperature();
	};

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private TwilightRestService service;
	
	@Bean
	public TwilightRestService service()
	{
		return new TwilightRestService();
	}
	
	 @Bean
	   public RestTemplate restTemplate() {
	       return new RestTemplate();
	   }

	private static final Logger log = LoggerFactory.getLogger(TempSpringBoot.class);

	@GetMapping(path="", produces = "application/json")
	public TwilightTemperature getTemp(
			@RequestParam(name = "lat", required = false, defaultValue = "43.66258321585993") String lat,
			@RequestParam(name = "lng", required = false, defaultValue = "43.66258321585993") String lng) {
	
		java.lang.String url = "https://api.sunrise-sunset.org/json?lat=" + lat + "&lng=" + lng;
		ResultsOutput object = restTemplate.getForObject(url, ResultsOutput.class);
		long temp = service.compareTimes(object);
		log.info("DayLength:   " + object.getResults().getDay_length());
		TwilightTemperature obj=getTempObj();
		obj.setTemperature(temp+"");
		return obj;
		
		//return getTempObj();
	}
}
