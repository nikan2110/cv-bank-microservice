package telran.cvbank.service;

import telran.cvbank.dto.InfoEmployeeDto;
import telran.cvbank.dto.RegisterEmployeeDto;

public interface EmployeeServiceAuth {
	
	InfoEmployeeDto registerEmployee(RegisterEmployeeDto newEmployee);
	
	InfoEmployeeDto getEmployee(String id, String password);
}
