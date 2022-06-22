package telran.cvbank.filters;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.PathContainer.Element;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
import telran.cvbank.exceptions.WrongCredentialException;

@Component
@Order(10)
public class OwnerFilter implements GatewayFilter{
	
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		System.out.println("Filter owner started");
		ServerHttpRequest request = exchange.getRequest();
		String pathId = getLogin(request.getPath());
		String headerId = request.getHeaders().getFirst("id");
		if (!pathId.equals(headerId)) {
			throw new WrongCredentialException();
		}
		return chain.filter(exchange);
	}

	private String getLogin(RequestPath requestPath) {
		List<Element> path = requestPath.elements();
		String login = path.get(path.size()-1).value();
		return login;
	}

}
