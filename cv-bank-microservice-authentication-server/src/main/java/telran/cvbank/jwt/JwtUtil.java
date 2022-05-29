package telran.cvbank.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
		this.key = Keys.hmacShaKeyFor(encodeJwtSecret.getBytes());
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public Date getExpirationDateFromToken(String token) {
		return getAllClaimsFromToken(token).getExpiration();
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(InfoEmployeeDto infoEmployeeDto, String type) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", infoEmployeeDto.getEmail());
		claims.put("role", infoEmployeeDto.getRoles());
		return doGenerateToken(claims, infoEmployeeDto.getEmail(), type);
	}

	private String doGenerateToken(Map<String, Object> claims, String username, String type) {
		long expirationTimeLong;
		if ("ACCESS".equals(type)) {
			expirationTimeLong = tokenValidity * 1000;
		} else {
			expirationTimeLong = tokenValidity * 1000 * 5;
		}
		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(createdDate)
				.setExpiration(expirationDate).signWith(key, SignatureAlgorithm.HS512).compact();

	}

	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

}
