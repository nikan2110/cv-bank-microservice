package telran.cvbank.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;

import reactor.core.publisher.Mono;
import telran.cvbank.feign.EmployeeServiceProxy;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfigAdapter {

	static Logger LOG = LoggerFactory.getLogger(SecurityConfigAdapter.class);

	AuthenticationManager authenticationManager;
	SecurityContextRepository contextRepository;
	EmployeeServiceProxy employeeServiceProxy;

	@Autowired
	public SecurityConfigAdapter(AuthenticationManager authenticationManager,
			SecurityContextRepository contextRepository, EmployeeServiceProxy employeeServiceProxy) {
		this.authenticationManager = authenticationManager;
		this.contextRepository = contextRepository;
		this.employeeServiceProxy = employeeServiceProxy;
	}
	
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.httpBasic();
		http.cors().and().csrf().disable();
		http.authenticationManager(authenticationManager);
		http.securityContextRepository(contextRepository);
		http.authorizeExchange().pathMatchers("/actuator/health").permitAll();
		http.authorizeExchange().pathMatchers("/cvbank/employee/signup").permitAll();
		http.authorizeExchange().pathMatchers("/cvbank/auth/signin").permitAll();
		http.authorizeExchange().pathMatchers("/cvbank/employee/feign/**").denyAll();
		// =============================================== CV =================================================
		http.authorizeExchange().pathMatchers(HttpMethod.POST, "/cvbank/cv").hasRole("EMPLOYEE");
		http.authorizeExchange().pathMatchers(HttpMethod.POST, "/cvbank/cv/cvs").permitAll();
		http.authorizeExchange().pathMatchers(HttpMethod.POST, "/cvbank/cv/cvs/aggregate").permitAll();
		http.authorizeExchange().pathMatchers(HttpMethod.GET, "/cvbank/cv/{cvId}").permitAll();
		http.authorizeExchange().pathMatchers(HttpMethod.GET, "/cvbank/cv/cvs/published").hasRole("EMPLOYER");
		http.authorizeExchange().pathMatchers(HttpMethod.PUT, "/cvbank/cv/anonymizer/{cvId}").access(this::checkCVAuthority);
		http.authorizeExchange().pathMatchers(HttpMethod.PUT, "/cvbank/cv/{cvId}").access(this::checkCVAuthority);
		http.authorizeExchange().pathMatchers(HttpMethod.PUT, "/cvbank/cv/publish/{cvId}").access(this::checkCVAuthority);
		http.authorizeExchange().pathMatchers(HttpMethod.DELETE, "/cvbank/cv/{cvId}").access(this::checkCVAuthority);
		// ============================================= EMPLOYEE =============================================
		http.authorizeExchange().pathMatchers(HttpMethod.PUT, "/cvbank/employee/login").hasRole("EMPLOYEE");
		http.authorizeExchange().pathMatchers(HttpMethod.PUT, "/cvbank/employee/pass").hasRole("EMPLOYEE");
		http.authorizeExchange().pathMatchers(HttpMethod.GET, "/cvbank/employee/{id}").permitAll();
		http.authorizeExchange().pathMatchers(HttpMethod.PUT, "/cvbank/employee/{id}").access(this::currentUserMatchesPath);
		http.authorizeExchange().pathMatchers(HttpMethod.DELETE, "/cvbank/employee/{id}").access(this::currentUserMatchesPath);
		http.authorizeExchange().anyExchange().authenticated();
		return http.build();
	}

	private Mono<AuthorizationDecision> checkCVAuthority(Mono<Authentication> authentication,
			AuthorizationContext context) {
		return authentication
				.map(a -> employeeServiceProxy.getEmployeeById(a.getName()).share().block()
						.getCv_id().contains(context.getVariables().get("cvId")))
				.map(granted -> new AuthorizationDecision(granted));
	}

	private Mono<AuthorizationDecision> currentUserMatchesPath(Mono<Authentication> authentication,
			AuthorizationContext context) {
		return authentication
				.map(a -> context.getVariables().get("id").equals(a.getName()))
				.map(granted -> new AuthorizationDecision(granted));
	}
	
	

}