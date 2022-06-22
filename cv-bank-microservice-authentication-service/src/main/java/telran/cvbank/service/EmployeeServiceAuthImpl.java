package telran.cvbank.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.cvbank.dao.EmployeeMongoRepository;
import telran.cvbank.dto.InfoEmployeeDto;
import telran.cvbank.dto.RegisterEmployeeDto;
import telran.cvbank.exceptions.EmployeeAlreadyExistException;
import telran.cvbank.exceptions.EmployeeNotFoundException;
import telran.cvbank.exceptions.WrongCredentialException;
import telran.cvbank.model.Employee;

@Service
public class EmployeeServiceAuthImpl implements EmployeeServiceAuth {

	EmployeeMongoRepository employeeRepo;
	ModelMapper modelMapper;
	PasswordEncoder passwordEncoder;

	@Autowired
	public EmployeeServiceAuthImpl(EmployeeMongoRepository employeeRepo, ModelMapper modelMapper,
			PasswordEncoder passwordEncoder) {
		this.employeeRepo = employeeRepo;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public InfoEmployeeDto registerEmployee(RegisterEmployeeDto newEmployee) {
		employeeRepo.deleteById(newEmployee.getEmail());
		if (employeeRepo.existsById(newEmployee.getEmail())) {
			throw new EmployeeAlreadyExistException();
		}
		Employee employee = modelMapper.map(newEmployee, Employee.class);
		String password = passwordEncoder.encode(newEmployee.getPassword());
		employee.setPassword(password);
		employee.getRoles().add("EMPLOYEE");
		employeeRepo.save(employee);
		return modelMapper.map(employee, InfoEmployeeDto.class);
	}

	@Override
	public InfoEmployeeDto getEmployee(String id, String password) {
		Employee employee = employeeRepo.findById(id).orElseThrow(EmployeeNotFoundException::new);
		if (!BCrypt.checkpw(password, employee.getPassword())) {
			throw new WrongCredentialException();
		}
		return modelMapper.map(employee, InfoEmployeeDto.class);
	}

}
