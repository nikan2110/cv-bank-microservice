package telran.cvbank.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import telran.cvbank.filters.AuthenticationFilter;
import telran.cvbank.filters.OwnerFilter;

@Configuration
public class GateWayConfig {
	
	AuthenticationFilter filter;
	OwnerFilter ownerFilter;

	@Autowired
	public GateWayConfig(AuthenticationFilter filter, OwnerFilter ownerFilter) {
		this.filter = filter;
		this.ownerFilter = ownerFilter;
	}
	
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("cv-bank-microservice-authentication-server", route -> route.path("/cvbank/auth/**")
				.filters(f -> f.filter(filter))
				.uri("lb://cv-bank-microservice-authentication-server"))
				.route("cv-bank-microservice-employee-service", route -> route.path("/cvbank/employee/{id}")
				.filters(f -> f.filter(filter).filter(ownerFilter))
				.uri("lb://cv-bank-microservice-employee-service"))
				.build();
	}
	

}
