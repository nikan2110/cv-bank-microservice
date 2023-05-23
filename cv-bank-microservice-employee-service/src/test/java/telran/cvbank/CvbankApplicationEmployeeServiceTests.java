package telran.cvbank;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.cvbank.dao.EmployeeMongoRepository;
import telran.cvbank.dto.RegisterEmployeeDto;
import telran.cvbank.dto.UpdateEmployeeDto;
import telran.cvbank.models.Employee;
import telran.cvbank.service.EmployeeAccountServiceImpl;


class CvbankApplicationEmployeeServiceTests {

	@Mock
	private EmployeeMongoRepository employeeRepo;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private EmployeeAccountServiceImpl employeeService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void registerEmployeeTest() {
		RegisterEmployeeDto registerEmployeeDto = new RegisterEmployeeDto();
		registerEmployeeDto.setEmail("test@mail.com");
		registerEmployeeDto.setPassword("testPassword");
		Employee employee = new Employee();

		when(employeeRepo.existsById(anyString())).thenReturn(false);
		when(modelMapper.map(registerEmployeeDto, Employee.class)).thenReturn(employee);
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(employeeRepo.save(any())).thenReturn(employee);

		employeeService.registerEmployee(registerEmployeeDto);

		verify(employeeRepo, times(1)).save(any());
	}

	@Test
	public void getEmployeeTest() {
		String employeeId = "test@mail.com";
		Employee employee = new Employee();

		when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));

		employeeService.getEmployee(employeeId);

		verify(employeeRepo, times(1)).findById(employeeId);
	}

	@Test
	public void updateEmployeeTest() {
		UpdateEmployeeDto updateEmployeeDto = new UpdateEmployeeDto();
		updateEmployeeDto.setFirstName("New name");
		String employeeId = "test@mail.com";
		Employee employee = new Employee();

		when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));
		when(employeeRepo.save(any())).thenReturn(employee);

		employeeService.updateEmployee(updateEmployeeDto, employeeId);

		verify(employeeRepo, times(1)).save(any());
	}

	@Test
	public void deleteEmployeeTest() {
		String employeeId = "test@mail.com";
		Employee employee = new Employee();

		when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));

		employeeService.deleteEmployee(employeeId);

		verify(employeeRepo, times(1)).delete(any());
	}

	@Test
	public void changeEmployeeLoginTest() {
		String employeeId = "test@mail.com";
		String newLogin = "new@mail.com";
		Employee employee = new Employee();

		when(employeeRepo.existsById(anyString())).thenReturn(false);
		when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));
		when(employeeRepo.save(any())).thenReturn(employee);

		employeeService.changeEmployeeLogin(employeeId, newLogin);

		verify(employeeRepo, times(1)).deleteById(employeeId);
		verify(employeeRepo, times(1)).save(any());
	}

	@Test
	public void changeEmployeePasswordTest() {
		String employeeId = "test@mail.com";
		String newPassword = "newPassword";
		Employee employee = new Employee();

		when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(employeeRepo.save(any())).thenReturn(employee);

		employeeService.changeEmployeePassword(employeeId, newPassword);

		verify(employeeRepo, times(1)).save(any());
	}

}
