package telran.cvbank.jwt;

import java.security.Key;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	String jwtSecret;
	Key key;

	@PostConstruct
	public void init() {
		String encodeJwtSecret = DigestUtils.sha256Hex(jwtSecret);
		this.key = Keys.hmacShaKeyFor(encodeJwtSecret.getBytes());
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}


	public Boolean isTokenExpired(String token) {
		return getAllClaimsFromToken(token).getExpiration().before(new Date());
	}

	public Boolean isInvavlid(String token) {
		return isTokenExpired(token);
	}

}
