package telran.cvbank.security;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import telran.cvbank.dao.EmployeeMongoRepository;
import telran.cvbank.model.Employee;

@Service
public class ReactiveUserDetailServiceIml implements ReactiveUserDetailsService {
	
	static Logger LOG = LoggerFactory.getLogger(ReactiveUserDetailServiceIml.class);

	EmployeeMongoRepository employeeMongoRepository;

	@Autowired
	public ReactiveUserDetailServiceIml(EmployeeMongoRepository employeeMongoRepository) {
		this.employeeMongoRepository = employeeMongoRepository;
	}

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		LOG.trace("received to security username {}", username);
		System.out.println(username);
		User user;
        if (employeeMongoRepository.existsById(username)) {
        	Employee employee = employeeMongoRepository.findById(username).orElse(null);
        	List<GrantedAuthority> authorities = employee.getRoles().stream()
        			.map(r -> new SimpleGrantedAuthority("ROLE_" + r))
        			.collect(Collectors.toList());
        	user = new User(username, employee.getPassword(),authorities);
        	return Mono.just(user);
        	
        }
        System.out.println("finish");
		return null;
	}

}
