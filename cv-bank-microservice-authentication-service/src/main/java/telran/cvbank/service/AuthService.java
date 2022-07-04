package telran.cvbank.service;

import telran.cvbank.dto.InfoEmployeeDto;

public interface AuthService {
	
	InfoEmployeeDto getEmployee(String id, String password);
}
