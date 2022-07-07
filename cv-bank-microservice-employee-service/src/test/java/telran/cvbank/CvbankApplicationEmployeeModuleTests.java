package telran.cvbank;


import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.crypto.bcrypt.BCrypt;

import telran.cvbank.dto.RegisterEmployeeDto;
import telran.cvbank.dto.UpdateEmployeeDto;
import telran.cvbank.exceptions.EmployeeAlreadyExistException;
import telran.cvbank.exceptions.EmployeeNotFoundException;
import telran.cvbank.model.Employee;
import telran.cvbank.service.EmployeeAccountServiceImpl;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@EnableEurekaClient
class CvbankApplicationEmployeeModuleTests {

	EmployeeAccountServiceImpl employeeService;
	ModelMapper modelMapper;

	@Autowired
	public CvbankApplicationEmployeeModuleTests(EmployeeAccountServiceImpl employeeService, ModelMapper modelMapper) {
		this.employeeService = employeeService;
		this.modelMapper = modelMapper;
	}

	Employee employee = Employee.builder()
			.email("green@goblin.com")
			.password("123456")
			.firstName("Norman")
			.lastName("Osborn").cv_id(new HashSet<>()).build();

	UpdateEmployeeDto updateEmployeeDto = UpdateEmployeeDto.builder()
			.firstName("Harry")
			.lastName("Osborn")
			.build();

	@Test
	@Order(1)
	public void addEmployee() throws Exception {
        RegisterEmployeeDto newEmployeeDto = modelMapper.map(employee, RegisterEmployeeDto.class);
        assertEquals(employee.getEmail(), employeeService.registerEmployee(newEmployeeDto).getEmail());
        assertThrows(EmployeeAlreadyExistException.class, () -> employeeService.registerEmployee(newEmployeeDto));
        assertNotEquals("peter@pan.com", employeeService.getEmployee("green@goblin.com"));

	}

    @Test
    @Order(2)
    public void getEmployee() throws Exception {
    	assertNotEquals("peter@pan.com", employeeService.getEmployee("green@goblin.com"));
    	assertEquals("green@goblin.com", employeeService.getEmployee("green@goblin.com").getEmail());
    }

    @Test
    @Order(10)
    public void updateEmployee() throws Exception {
    	employeeService.updateEmployee(updateEmployeeDto, "green@goblin.com");
        assertEquals("Harry", employeeService.getEmployee("green@goblin.com").getFirstName());
    }


    @Test
    @Order(11)
    public void changeLogin() throws Exception {
        assertNotNull(employeeService.getEmployee("green@goblin.com"));
        employeeService.changeEmployeeLogin("green@goblin.com", "hob@goblin.com");
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee("green@goblin.com"));
        assertEquals("hob@goblin.com", employeeService.getEmployee("hob@goblin.com").getEmail());
    }

    @Test
    @Order(12)
    public void changePassword() throws Exception {
    	assertTrue(BCrypt.checkpw("123456", employeeService.getEmployeeById("hob@goblin.com").getPassword()));
    	employeeService.changeEmployeePassword("hob@goblin.com", "0000");
    	assertFalse(BCrypt.checkpw("123456", employeeService.getEmployeeById("hob@goblin.com").getPassword()));
    	assertTrue(BCrypt.checkpw("0000", employeeService.getEmployeeById("hob@goblin.com").getPassword()));
    }

    @Test
    @Order(100)
    public void deleteEmployee() throws Exception {
    	assertEquals("hob@goblin.com", employeeService.getEmployee("hob@goblin.com").getEmail());
    	assertNotNull(employeeService.getEmployee("hob@goblin.com"));
    	employeeService.deleteEmployee("hob@goblin.com");
    	assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee("hob@goblin.com"));
    }

}
