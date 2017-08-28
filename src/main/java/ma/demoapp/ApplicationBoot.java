package ma.demoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="ma.demoapp.*")
public class ApplicationBoot {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}
	
}
