package telran.cvbank.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.cvbank.dao.EmployeeMongoRepository;
import telran.cvbank.dto.InfoEmployeeDto;
import telran.cvbank.dto.RegisterEmployeeDto;
import telran.cvbank.exceptions.EmployeeAlreadyExistException;
import telran.cvbank.exceptions.EmployeeNotFoundException;
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
		employee.getRoles().add("USER");
		employeeRepo.save(employee);
		return modelMapper.map(employee, InfoEmployeeDto.class);
	}

	@Override
	public InfoEmployeeDto getEmployee(String id) {
		Employee employee = employeeRepo.findById(id).orElseThrow(EmployeeNotFoundException::new);
		return modelMapper.map(employee, InfoEmployeeDto.class);
	}

}
