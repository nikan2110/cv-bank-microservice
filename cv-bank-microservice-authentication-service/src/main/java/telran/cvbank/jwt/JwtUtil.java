package telran.cvbank.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import telran.cvbank.dto.InfoEmployeeDto;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	String jwtSecret;
	@Value("${jwt.token.validity}")
	long tokenValidity;
	Key key;

	@PostConstruct
	public void init() {
		String encodeJwtSecret = DigestUtils.sha256Hex(jwtSecret);
		key = Keys.hmacShaKeyFor(encodeJwtSecret.getBytes());
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

	public String generateToken(InfoEmployeeDto infoEmployeeDto, String type) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", infoEmployeeDto.getEmail());
		claims.put("role", infoEmployeeDto.getRoles());
		return doGenerateToken(claims, infoEmployeeDto.getEmail(), type);
	}

	private String doGenerateToken(Map<String, Object> claims, String username, String type) {
		final Date createdDate = new Date();
		final Date expirationDate = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(tokenValidity));
		return Jwts.builder()
				.setSubject("Employee details")
				.setClaims(claims)
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.setIssuer("authentication-service")
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
	}

}
