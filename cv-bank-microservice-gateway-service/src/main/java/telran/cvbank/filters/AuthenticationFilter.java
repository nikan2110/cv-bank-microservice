package telran.cvbank.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;
import telran.cvbank.configuration.RouterValidator;
import telran.cvbank.exceptions.AuthorizationHeaderIsInvalidException;
import telran.cvbank.exceptions.AuthorizationHeaderIsMissingException;
import telran.cvbank.jwt.JwtUtil;

@Component
@Order(1)
public class AuthenticationFilter implements GatewayFilter {

	JwtUtil jwtUtil;
	RouterValidator routerValidator;

	@Autowired
	public AuthenticationFilter(JwtUtil jwtUtil, RouterValidator routerValidator) {
		this.jwtUtil = jwtUtil;
		this.routerValidator = routerValidator;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		System.out.println("Filter auth started");
		ServerHttpRequest request = exchange.getRequest();
		if (routerValidator.isSecured.test(request)) {
			if (isAuthMissing(request)) {
				throw new AuthorizationHeaderIsMissingException("Authorization header is missing in request");
			}
			final String token = getAuthHeader(request);
			System.out.println("AuthFilter: {} " + token);

			if (jwtUtil.isInvavlid(token)) {
				throw new AuthorizationHeaderIsInvalidException("Authorization header is missing in request");
			}
			populateRequestWithHeaders(exchange, token);
		}
		return chain.filter(exchange);
	}

	private String getAuthHeader(ServerHttpRequest request) {
		return request.getHeaders().getOrEmpty("Authorization").get(0);
	}

	private boolean isAuthMissing(ServerHttpRequest request) {
		return !request.getHeaders().containsKey("Authorization");
	}

	private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
		Claims claims = jwtUtil.getAllClaimsFromToken(token);
		exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id")))
				.header("role", String.valueOf(claims.get("role"))).build();

	}

}
