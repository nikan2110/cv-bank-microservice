package telran.cvbank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfigAdapter {

	AuthenticationManager authenticationManager;
	SecurityContextRepository contextRepository;

	@Autowired
	public SecurityConfigAdapter(AuthenticationManager authenticationManager,
			SecurityContextRepository contextRepository) {
		this.authenticationManager = authenticationManager;
		this.contextRepository = contextRepository;
	}

	@Bean
	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.httpBasic();
		http.cors().and().csrf().disable();
		http.authenticationManager(authenticationManager);
		http.securityContextRepository(contextRepository);
		http.authorizeExchange().pathMatchers("/cvbank/auth/signup").permitAll();
		http.authorizeExchange().pathMatchers("/cvbank/auth/signin").permitAll();
		http.authorizeExchange().pathMatchers("/cvbank/employee/{id}").access((mono, context) -> mono
				.map(auth -> auth.getName().equals(context.getVariables().get("id")))
				.map(AuthorizationDecision::new));
		return http.build();
	}
}