package telran.cvbank.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfigAdapter {

	@Bean
	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.httpBasic();
		http.cors().and().csrf().disable();
		http.authorizeExchange().pathMatchers("/cvbank/auth/signup").permitAll();
		http.authorizeExchange().pathMatchers("/cvbank/auth/signin").permitAll();
		http.authorizeExchange().pathMatchers("/cvbank/employee/{id}").permitAll();
		http.authorizeExchange().anyExchange().hasRole("ADMIN");
		return http.build();
	}
}