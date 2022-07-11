package telran.cvbank.service;

import telran.cvbank.dto.InfoEmployeeDto;
import telran.cvbank.dto.RegisterEmployeeDto;
import telran.cvbank.dto.UpdateEmployeeDto;
import telran.cvbank.models.Employee;

public interface EmployeeAccountService {
	
	InfoEmployeeDto registerEmployee(RegisterEmployeeDto newEmployee);
	
	InfoEmployeeDto getEmployee(String id);
	
	InfoEmployeeDto updateEmployee(UpdateEmployeeDto employeeData, String id);
	
	InfoEmployeeDto changeEmployeeLogin(String id, String newLogin);
	
	void changeEmployeePassword(String id, String newPassword);
	
	void deleteEmployee(String id);
	
	Employee getEmployeeById(String id);
	
	void removeCVId(String id, String cvId);

}