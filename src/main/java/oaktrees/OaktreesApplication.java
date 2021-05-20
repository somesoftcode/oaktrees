package oaktrees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class OaktreesApplication {
	
	public static void main(String[] args) {
		//ConfigurableApplicationContext c =
				SpringApplication.run(OaktreesApplication.class, args);
	}

}
