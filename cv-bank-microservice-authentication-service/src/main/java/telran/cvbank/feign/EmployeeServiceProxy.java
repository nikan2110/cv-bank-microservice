package telran.cvbank.feign;


import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import telran.cvbank.models.Employee;

@FeignClient(name="cv-bank-microservice-employee-service")
@LoadBalancerClient(name = "cv-bank-microservice-employee-service")
public interface EmployeeServiceProxy {
	
	@GetMapping("/cvbank/employee/feign/{id}")
	public Employee getEmployeeById(@PathVariable String id);

}
