package telran.cvbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CVBankEmployeeServiceAppl {

	public static void main(String[] args) {
		SpringApplication.run(CVBankEmployeeServiceAppl.class, args);

	}

}
