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

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfigAdapter {

	static Logger LOG = LoggerFactory.getLogger(SecurityConfigAdapter.class);

	AuthenticationManager authenticationManager;
	SecurityContextRepository contextRepository;

	@Autowired
	public SecurityConfigAdapter(AuthenticationManager authenticationManager,
			SecurityContextRepository contextRepository) {
		this.authenticationManager = authenticationManager;
		this.contextRepository = contextRepository;
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
		http.authorizeExchange().pathMatchers("/cvbank/employee/signup").permitAll();
		http.authorizeExchange().pathMatchers("/cvbank/auth/signin").permitAll();
		http.authorizeExchange().pathMatchers("/cvbank/employee/feign/**").denyAll();
		http.authorizeExchange().pathMatchers(HttpMethod.PUT, "/cvbank/employee/login").hasRole("EMPLOYEE");
		http.authorizeExchange().pathMatchers(HttpMethod.PUT, "/cvbank/employee/pass").hasRole("EMPLOYEE");
		http.authorizeExchange().pathMatchers(HttpMethod.GET, "/cvbank/employee/{id}").permitAll();
		http.authorizeExchange().pathMatchers(HttpMethod.PUT, "/cvbank/employee/{id}").access(this::currentUserMatchesPath);
		http.authorizeExchange().pathMatchers(HttpMethod.DELETE, "/cvbank/employee/{id}").access(this::currentUserMatchesPath);
		http.authorizeExchange().anyExchange().authenticated();
		return http.build();
	}

	private Mono<AuthorizationDecision> currentUserMatchesPath(Mono<Authentication> authentication,
			AuthorizationContext context) {
		LOG.trace("requests variables", context.getVariables());
		return authentication
				.map(a -> context.getVariables().get("id").equals(a.getName()))
				.map(granted -> new AuthorizationDecision(granted));
	}

}