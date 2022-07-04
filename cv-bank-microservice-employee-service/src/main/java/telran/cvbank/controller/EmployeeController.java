package telran.cvbank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import telran.cvbank.dto.InfoEmployeeDto;
import telran.cvbank.dto.RegisterEmployeeDto;
import telran.cvbank.dto.UpdateEmployeeDto;
import telran.cvbank.model.Employee;
import telran.cvbank.service.EmployeeAccountService;

@RestController
@RequestMapping("/cvbank/employee")
@CrossOrigin(origins = "*", methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS,
		RequestMethod.POST, RequestMethod.PUT }, allowedHeaders = "*", exposedHeaders = "*")
public class EmployeeController {
	
	static Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
	
	EmployeeAccountService employeeAccountService;

	@Autowired
	public EmployeeController(EmployeeAccountService employeeAccountService) {
		this.employeeAccountService = employeeAccountService;
	}
	
	@PostMapping("/signup")
	public InfoEmployeeDto signup(@RequestBody RegisterEmployeeDto newEmployee) {
		return employeeAccountService.registerEmployee(newEmployee);
	}

	@PutMapping("/{id}")
	public InfoEmployeeDto updateEmployee(@RequestBody UpdateEmployeeDto employeeData, @PathVariable String id) {
		return employeeAccountService.updateEmployee(employeeData, id);
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable String id) {
		employeeAccountService.deleteEmployee(id);
	}

	@GetMapping("/{id}")
	public InfoEmployeeDto findEmployee(@PathVariable String id) {
		return employeeAccountService.getEmployee(id);
	}

	@PutMapping("/login")
	public InfoEmployeeDto updateLogin(@RequestHeader("id") String id, @RequestHeader("X-Login") String newLogin) {
		LOG.trace("received employee id {}", id);
		return employeeAccountService.changeEmployeeLogin(id, newLogin);
	}

	@PutMapping("/pass")
	public void updatePassword(@RequestHeader("id") String id, @RequestHeader("X-Password") String newPassword) {
		employeeAccountService.changeEmployeePassword(id, newPassword);
	}
	
	@GetMapping("/feign/{id}")
	public Employee getEmployeeById(@PathVariable String id) {
		LOG.trace("received employee id {}", id);
		return employeeAccountService.getEmployeeById(id);
	}
}