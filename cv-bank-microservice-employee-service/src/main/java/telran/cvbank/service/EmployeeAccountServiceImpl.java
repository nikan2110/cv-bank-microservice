package telran.cvbank.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.cvbank.dao.EmployeeMongoRepository;
import telran.cvbank.dto.InfoEmployeeDto;
import telran.cvbank.dto.UpdateEmployeeDto;
import telran.cvbank.dto.exceptions.EmployeeAlreadyExistException;
import telran.cvbank.dto.exceptions.EmployeeNotFoundException;
import telran.cvbank.model.Employee;

@Service
public class EmployeeAccountServiceImpl implements EmployeeAccountService {
	
    EmployeeMongoRepository employeeRepo;
    ModelMapper modelMapper;
	PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeAccountServiceImpl(EmployeeMongoRepository employeeRepo, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.employeeRepo = employeeRepo;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public InfoEmployeeDto getEmployee(String id) {
        Employee employee = getEmployeeById(id);
        return modelMapper.map(employee, InfoEmployeeDto.class);
    }

    @Override
    public InfoEmployeeDto updateEmployee(UpdateEmployeeDto employeeData, String id) {
        Employee employee = getEmployeeById(id);
        employee.setFirstName(employeeData.getFirstName());
        employee.setLastName(employeeData.getLastName());
        employeeRepo.save(employee);
        return modelMapper.map(employee, InfoEmployeeDto.class);
    }

    @Override
    public void deleteEmployee(String id) {
        Employee employee = getEmployeeById(id);
        employeeRepo.delete(employee);
    }

    @Override
    public InfoEmployeeDto changeEmployeeLogin(String id, String newLogin) {
        if (employeeRepo.existsById(newLogin)) {
            throw new EmployeeAlreadyExistException();
        }
        Employee employee = getEmployeeById(id);
        employee.setEmail(newLogin);
        employeeRepo.deleteById(id);
        employeeRepo.save(employee);
        return modelMapper.map(employee, InfoEmployeeDto.class);
    }

    @Override
    public void changeEmployeePassword(String id, String newPassword) {
        Employee employee = getEmployeeById(id);
        String password = passwordEncoder.encode(newPassword);
        employee.setPassword(password);
        employeeRepo.save(employee);
    }

    private Employee getEmployeeById(String id) {
        return employeeRepo.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }
}
