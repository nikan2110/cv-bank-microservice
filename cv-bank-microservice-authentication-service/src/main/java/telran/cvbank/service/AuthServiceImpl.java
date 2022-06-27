package telran.cvbank.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AuthServiceImpl implements AuthService {
	
	static Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);

	EmployeeMongoRepository employeeRepo;
	ModelMapper modelMapper;
	PasswordEncoder passwordEncoder;

	@Autowired
	public AuthServiceImpl(EmployeeMongoRepository employeeRepo, ModelMapper modelMapper,
			PasswordEncoder passwordEncoder) {
		this.employeeRepo = employeeRepo;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public InfoEmployeeDto registerEmployee(RegisterEmployeeDto newEmployee) {
		LOG.trace("received employee id {} for register", newEmployee.getEmail());
		if (employeeRepo.existsById(newEmployee.getEmail())) {
			throw new EmployeeAlreadyExistException("Employee with id " + newEmployee.getEmail() + " already exist");
		}
		Employee employee = modelMapper.map(newEmployee, Employee.class);
		String password = passwordEncoder.encode(newEmployee.getPassword());
		employee.setPassword(password);
		employee.getRoles().add("ROLE_EMPLOYEE");
		employeeRepo.save(employee);
		LOG.trace("employee with id {} was register", employee.getEmail());
		return modelMapper.map(employee, InfoEmployeeDto.class);
	}

	@Override
	public InfoEmployeeDto getEmployee(String id, String password) {
		LOG.trace("received employee id {} for get", id);
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " not found"));
		if (!BCrypt.checkpw(password, employee.getPassword())) {
			LOG.error("wrong password");
			throw new WrongCredentialException("Wrong password");
		}
		LOG.trace("employee with id {} was found", employee.getEmail());
		return modelMapper.map(employee, InfoEmployeeDto.class);
	}

}
