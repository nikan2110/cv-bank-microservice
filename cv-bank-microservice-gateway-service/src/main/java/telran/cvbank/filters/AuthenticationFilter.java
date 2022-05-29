package telran.cvbank.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;
import telran.cvbank.configuration.RouterValidator;
import telran.cvbank.jwt.JwtUtil;

@RefreshScope
@Component
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
		ServerHttpRequest request = exchange.getRequest();
		if (routerValidator.isSecured.test(request)) {
			if (this.isAuthMissing(request)) {
				return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
			}
			final String token = this.getAuthHeader(request);

			if (jwtUtil.isInvavlid(token)) {
				return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
			}
			this.populateRequestWithHeaders(exchange, token);
		}
		return chain.filter(exchange);
	}

	private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
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
