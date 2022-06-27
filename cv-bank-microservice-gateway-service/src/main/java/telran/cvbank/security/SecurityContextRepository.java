package telran.cvbank.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Repository
public class SecurityContextRepository implements ServerSecurityContextRepository {
	
	static Logger LOG = LoggerFactory.getLogger(AuthenticationManager.class);

	AuthenticationManager authenticationManager;

	@Autowired
	public SecurityContextRepository(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		throw new IllegalStateException("Save method not supported!");
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		LOG.trace("auth header {}", authHeader);
		if (authHeader != null && authHeader.startsWith("Bearer")) {
			String authToken = authHeader.substring(7);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
			LOG.info("load method finished");
			return authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
		}
		return Mono.empty();

	}

}
