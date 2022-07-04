package telran.cvbank.service;

import telran.cvbank.dto.InfoEmployeeDto;
import telran.cvbank.dto.RegisterEmployeeDto;

public interface AuthService {
	
	InfoEmployeeDto getEmployee(String id, String password);
}
