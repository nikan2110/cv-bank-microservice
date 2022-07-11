package telran.cvbank.feign;


import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;
import telran.cvbank.models.Employee;

@LoadBalancerClient(name = "cv-bank-microservice-employee-service")
@ReactiveFeignClient(name="cv-bank-microservice-employee-service")
public interface EmployeeServiceProxy {
	
	@GetMapping("/cvbank/employee/feign/{id}")
	public Mono<Employee> getEmployeeById(@PathVariable String id);

}
