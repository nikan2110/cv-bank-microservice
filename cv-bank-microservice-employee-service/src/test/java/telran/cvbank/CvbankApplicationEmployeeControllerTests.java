package telran.cvbank;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.cvbank.controller.EmployeeController;
import telran.cvbank.dto.InfoEmployeeDto;
import telran.cvbank.dto.RegisterEmployeeDto;
import telran.cvbank.dto.UpdateEmployeeDto;
import telran.cvbank.models.Employee;
import telran.cvbank.service.EmployeeAccountService;
import telran.cvbank.service.ServerPortService;

@SpringBootTest
@AutoConfigureMockMvc
@EnableEurekaClient
@EmbeddedKafka(partitions = 1, topics = { "testTopic" }, bootstrapServersProperty = "spring.kafka.bootstrap-servers")
class CvbankApplicationEmployeeControllerTests {

	@InjectMocks
	private EmployeeController employeeController;

	@Mock
	private EmployeeAccountService employeeAccountService;

	@Mock
	private ServerPortService serverPortService;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	public void testSignup() throws Exception {
		  RegisterEmployeeDto registerEmployeeDto = new RegisterEmployeeDto();
		    registerEmployeeDto.setEmail("test@mail.com");
		    registerEmployeeDto.setFirstName("John");
		    registerEmployeeDto.setLastName("Doe");
		    registerEmployeeDto.setPassword("password123");

		    // Perform the POST request and assert the response
		    mockMvc.perform(post("/cvbank/employee/signup")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(objectMapper.writeValueAsString(registerEmployeeDto)))
		            .andExpect(status().isOk());
		    
	}

	@Test
	public void testUpdateEmployee() throws Exception {
		UpdateEmployeeDto updateEmployeeDto = new UpdateEmployeeDto();
	    updateEmployeeDto.setFirstName("New Name");
	    updateEmployeeDto.setLastName("New Last Name");
	    
	    mockMvc.perform(put("/cvbank/employee/{id}", "test-id")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(updateEmployeeDto)))
	            .andExpect(status().isOk());
	}

	@Test
	public void testDeleteEmployee() throws Exception {
		String id = "test@mail.com";

		mockMvc.perform(delete("/cvbank/employee/" + id)).andExpect(status().isOk());

		verify(employeeAccountService, times(1)).deleteEmployee(id);
	}

	@Test
	public void testFindEmployee() throws Exception {
		String id = "test@mail.com";
		InfoEmployeeDto infoEmployeeDto = new InfoEmployeeDto();

		when(employeeAccountService.getEmployee(id)).thenReturn(infoEmployeeDto);

		mockMvc.perform(get("/cvbank/employee/" + id)).andExpect(status().isOk());

		verify(employeeAccountService, times(1)).getEmployee(id);
	}

	@Test
	public void testUpdateLogin() throws Exception {
		String id = "test@mail.com";
		String newLogin = "newLogin";
		InfoEmployeeDto infoEmployeeDto = new InfoEmployeeDto();

		when(employeeAccountService.changeEmployeeLogin(id, newLogin)).thenReturn(infoEmployeeDto);

		mockMvc.perform(put("/cvbank/employee/login").header("id", id).header("X-Login", newLogin))
				.andExpect(status().isOk());

		verify(employeeAccountService, times(1)).changeEmployeeLogin(id, newLogin);
	}

	@Test
	public void testUpdatePassword() throws Exception {
		String id = "test@mail.com";
		String newPassword = "newPassword";

		mockMvc.perform(put("/cvbank/employee/pass").header("id", id).header("X-Password", newPassword))
				.andExpect(status().isOk());

		verify(employeeAccountService, times(1)).changeEmployeePassword(id, newPassword);
	}

	@Test
	public void testGetEmployeeById() throws Exception {
		String id = "test@mail.com";
		Employee employee = new Employee();

		when(employeeAccountService.getEmployeeById(id)).thenReturn(employee);

		mockMvc.perform(get("/cvbank/employee/feign/" + id)).andExpect(status().isOk());

		verify(employeeAccountService, times(1)).getEmployeeById(id);
	}

	@Test
	public void testDeleteCVId() throws Exception {
		String id = "test@mail.com";
		String cvId = "testCvId";

		mockMvc.perform(delete("/cvbank/employee/restTemplate/" + id + "/" + cvId)).andExpect(status().isOk());

		verify(employeeAccountService, times(1)).removeCVId(id, cvId);
	}
}
