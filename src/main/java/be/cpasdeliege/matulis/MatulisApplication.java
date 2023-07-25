package be.cpasdeliege.matulis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("be.cpasdeliege")
public class MatulisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatulisApplication.class, args);
	}

}
