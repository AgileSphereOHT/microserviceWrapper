package uk.co.agilesphere.microservicewrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import uk.co.agilesphere.wrapped.service.ServiceConfiguration;

@SpringBootApplication
@Import(ServiceConfiguration.class)
public class WrapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(WrapperApplication.class, args);
	}
}
