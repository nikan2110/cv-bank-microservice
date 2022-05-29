package telran.cvbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import telran.cvbank.service.EmployeeAccountService;

@RestController
@RequestMapping("/cvbank/employee")
@CrossOrigin(origins = "*", methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS,
		RequestMethod.POST, RequestMethod.PUT }, allowedHeaders = "*", exposedHeaders = "*")
public class EmployeeController {
	EmployeeAccountService employeeAccountService;

	@Autowired
	public EmployeeController(EmployeeAccountService employeeAccountService) {
		this.employeeAccountService = employeeAccountService;
	}

	@PostMapping("/signup")
	public InfoEmployeeDto registerEmployee(@RequestBody RegisterEmployeeDto newEmployee) {
		return employeeAccountService.registerEmployee(newEmployee);
	}

	@PostMapping("/signin")
	public InfoEmployeeDto loginEmployee(Authentication authentication) {
		return employeeAccountService.getEmployee(authentication.getName());
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
	public InfoEmployeeDto updateLogin(Authentication authentication, @RequestHeader("X-Login") String newLogin) {
		return employeeAccountService.changeEmployeeLogin(authentication.getName(), newLogin);
	}

	@PutMapping("/pass")
	public void updatePassword(Authentication authentication, @RequestHeader("X-Password") String newPassword) {
		employeeAccountService.changeEmployeePassword(authentication.getName(), newPassword);
	}
}