package telran.cvbank.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.cvbank.dto.InfoEmployeeDto;
import telran.cvbank.exceptions.WrongCredentialException;
import telran.cvbank.feign.EmployeeServiceProxy;
import telran.cvbank.model.Employee;

@Service
public class AuthServiceImpl implements AuthService {

	static Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);

	ModelMapper modelMapper;
	PasswordEncoder passwordEncoder;
	EmployeeServiceProxy employeeServiceProxy; 

	@Autowired
	public AuthServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, EmployeeServiceProxy employeeServiceProxy) {
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
		this.employeeServiceProxy = employeeServiceProxy;
	}

	@Override
	public InfoEmployeeDto getEmployee(String id, String password) {
		LOG.trace("received employee id {} for get", id);
		Employee employee = employeeServiceProxy.getEmployeeById(id);
		System.out.println(employee);
		LOG.trace("received employee from employee-service {}", employee.getEmail());
		if (!BCrypt.checkpw(password, employee.getPassword())) {
			LOG.error("wrong password");
			throw new WrongCredentialException("Wrong password");
		}
		LOG.trace("employee with id {} was found", employee.getEmail());
		return modelMapper.map(employee, InfoEmployeeDto.class);
	}

}
