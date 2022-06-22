package telran.cvbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.cvbank.dto.InfoEmployeeDto;
import telran.cvbank.dto.JwtRequestDto;
import telran.cvbank.dto.JwtResponseDto;
import telran.cvbank.dto.RegisterEmployeeDto;
import telran.cvbank.jwt.JwtUtil;
import telran.cvbank.service.AuthService;

@RestController
@RequestMapping("/cvbank/auth")
public class AuthServiceController {

	JwtUtil jwtUtil;
	AuthService employeeServiceAuth;

	@Autowired
	public AuthServiceController(JwtUtil jwtUtil, AuthService employeeServiceAuth) {
		this.jwtUtil = jwtUtil;
		this.employeeServiceAuth = employeeServiceAuth;
	}

	@PostMapping("/signup")
	public InfoEmployeeDto signup(@RequestBody RegisterEmployeeDto newEmployee) {
		return employeeServiceAuth.registerEmployee(newEmployee);
	}

	@PostMapping("/signin")
	public JwtResponseDto generateJwtToken(@RequestBody JwtRequestDto jwtRequest) {
		InfoEmployeeDto infoEmployeeDto = employeeServiceAuth.getEmployee(jwtRequest.getUsername(), jwtRequest.getPassword());
		String accesstoken = jwtUtil.generateToken(infoEmployeeDto, "ACCESS");
		String refreshToken = jwtUtil.generateToken(infoEmployeeDto, "REFRESH");
		return new JwtResponseDto(accesstoken, refreshToken);
	}

}
