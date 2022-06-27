package telran.cvbank.security;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;
import telran.cvbank.exceptions.AuthorizationHeaderIsInvalidException;
import telran.cvbank.exceptions.AuthorizationHeaderIsMissingException;
import telran.cvbank.jwt.JwtUtil;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

	static Logger LOG = LoggerFactory.getLogger(AuthenticationManager.class);

	JwtUtil jwtUtil;

	@Autowired
	public AuthenticationManager(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String authToken = authentication.getCredentials().toString();
		if(authToken.isEmpty()) {
			throw new AuthorizationHeaderIsMissingException("Authorization header is missing in request");
		}
		LOG.trace("received auth token {}", authToken);
		String userName = jwtUtil.getAllClaimsFromToken(authToken).get("id").toString();
		LOG.trace("received user id {}", userName);
		if (jwtUtil.isInvavlid(authToken)) {
			throw new AuthorizationHeaderIsInvalidException("Token is not valid");
		}
		Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
		List<String> roles = claims.get("role", List.class);
		List<GrantedAuthority> authorities = roles.stream()
				.map(r -> new SimpleGrantedAuthority(r))
				.collect(Collectors.toList());
		LOG.trace("authorities list {}", authorities);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				userName, null, authorities);
		LOG.info("authenticate method finished");
		return Mono.just(authenticationToken);
	}

}
