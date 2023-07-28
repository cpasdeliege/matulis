package be.cpasdeliege.authentication.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String SECRET_KEY = "Mn6r2V3oH2qcw2SEwxkauZfzSHUrmGrKKT70Y9FuBauyJYaPSypYxKIglkRohXKoGuyvyYe6ihvXhVqJaXCXNDHfnDUgEgECfl0mmlB/Kn3325Z2mAbcuOM/3asuUrsS07J/qQ7nRUIkv/kH09xb4xlYTn2DKnUitjwZ5oqxQucryjVmQTjZt7KutqOVKMZ66bK0CeROpxPCGAcMGsTvQzGcGH5hJr0qmOaczwnFgnIHACE294xkwW/L/pmFv5bWBW1VHsPaT+OaDOTeLm6xRK5GyEGrJ4MpkpN0LiVUbFG3DoYmgfOc4riha37e71y308aqcFR1oL+zxqJPe6s1PqWnqI9RBELj0BzP4jUD3nE=";

	public String extractUsername (String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(
		Map<String, Object> extraClaims,
		UserDetails userDetails
	) {
		return Jwts
			.builder()
			.setClaims(extraClaims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
			.signWith(getSignInKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
			.parserBuilder()
			.setSigningKey(getSignInKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
