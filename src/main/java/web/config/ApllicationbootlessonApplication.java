package web.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "web")
@EntityScan("web.model")
public class ApllicationbootlessonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApllicationbootlessonApplication.class, args);
	}

}
