package telran.cvbank.configuration;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

	public static final List<String> openApiEndPoints = List.of("/cvbank/auth/signup", "/cvbank/auth/signin");

	public Predicate<ServerHttpRequest> isSecured = request -> openApiEndPoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));

}
