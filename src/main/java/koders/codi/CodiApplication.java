package koders.codi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaAuditing		//엔티티 생성, 변경 시점 기록

public class CodiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodiApplication.class, args);
	}

}
