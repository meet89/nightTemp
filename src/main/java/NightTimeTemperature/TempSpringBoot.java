package NightTimeTemperature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TempSpringBoot {

	private static final Logger log = LoggerFactory.getLogger(TempSpringBoot.class);

	public static void main(String[] args) {
		SpringApplication.run(TempSpringBoot.class);
	}


	

}
