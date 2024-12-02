package kr.lsj.common.lib.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.lsj.common.lib.exception.TokenEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.*;

/**
 * JWT 관련된 토큰 Util
 *
 * @author lee
 * @fileName TokenUtils
 * @since 2022.12.23
 */
@SuppressWarnings("unused")
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
@Component
public class TokenUtils {

	private final AuthProperties authProperties;

	private SecretKey secretKey;
	private JwtParser jwtParser;

	@PostConstruct
	public void init(){
		secretKey = Keys.hmacShaKeyFor(authProperties.getJwtSecretKey().getBytes());
		jwtParser = Jwts.parser()
				.verifyWith(secretKey)
				.build();
	}


	/**
	 * Generate jwt token string.
	 *
	 * @param authentication the user vo
	 * @param expireTime Expiry time of the creation token(second). 0 is default value(1 day). max is a month(30 day).
	 * @return the string
	 */
	public String generateJwtToken(Authentication authentication, int expireTime) {
		// 사용자 시퀀스를 기준으로 JWT 토큰을 발급하여 반환해줍니다.
		return Jwts.builder()
				.header()
					.add(createHeader())
					.and()
				.claims(createClaims(authentication))
				.expiration(createExpiredDate(expireTime))
				.subject(authentication.getPrincipal().toString())
				.signWith(secretKey,Jwts.SIG.HS256)
				.compact();
	}


	/**
	 * 사용자 정보를 기반으로 토큰을 생성하여 반환 해주는 메서드(만료시간 1일)
	 *
	 * @param authentication the user vo
	 * @return String : 토큰
	 */
	public String generateJwtToken(Authentication authentication) {
		return generateJwtToken(authentication, authProperties.getExpiration());
	}

	/**
	 * 토큰의 만료기간을 지정하는 함수
	 *
	 * @param expireTime Expiry time of the creation token(second).
	 * @return Date
	 */
	private Date createExpiredDate(int expireTime) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.SECOND, expireTime);
		return c.getTime();
	}

	/**
	 * JWT 의 "헤더" 값을 생성해주는 메서드
	 *
	 * @return HashMap<String, Object>
	 */
	private Map<String, Object> createHeader() {
		Map<String, Object> header = new HashMap<>();

		header.put("typ", "JWT");
		header.put("alg", "HS256");
		header.put("regDate", System.currentTimeMillis());
		return header;
	}

	/**
	 * 사용자 정보를 기반으로 클래임을 생성해주는 메서드
	 *
	 * @param authentication 사용자 정보
	 * @return Map<String, Object>
	 */
	private Map<String, Object> createClaims(Authentication authentication) {
		// 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
		Map<String, Object> claims = new HashMap<>();

		claims.put("principal",		 authentication.getPrincipal());
		claims.put("details",		 authentication.getDetails());
		claims.put("authorities",	 authentication.getAuthorities());
		claims.put("authentication", authentication);
		return claims;
	}

	public long getExpireTimeFromToken(String token) throws JwtException, IllegalArgumentException {
		Claims claims = getClaimsFromToken(token);
		final Date expiration = claims.getExpiration();

		return expiration.getTime();
	}

	public Authentication getAuthFromHeader(String header) throws JwtException, IllegalArgumentException {
		return getAuthFromToken(getTokenFromHeader(header));
	}

	public Authentication getAuthFromToken(String token) throws JwtException, IllegalArgumentException {
		return getClaimsFromToken(token)
				.get("authentication", Authentication.class);
	}

	/**
	 * 토큰 정보를 기반으로 Claims 정보를 반환받는 메서드
	 *
	 * @param token : 토큰
	 * @return Claims : Claims
	 */
	private Claims getClaimsFromToken(String token) throws JwtException, IllegalArgumentException {
		return jwtParser.parseSignedClaims(token).getPayload();
	}
	/**
	 * 토큰을 기반으로 사용자 정보를 반환 해주는 메서드
	 *
	 * @param token String : 토큰
	 * @return String : 사용자 정보
	 */
	public String parseTokenToUserInfo(String token) throws JwtException, IllegalArgumentException {
		return getClaimsFromToken(token).getSubject();
	}

	/**
	 * Header 내에 토큰을 추출합니다.
	 *
	 * @param header 헤더
	 * @return String
	 */
	public String getTokenFromHeader(String header) throws TokenEmptyException {
		if(!StringUtils.hasLength(header))
			throw new TokenEmptyException("header is empty");

		String result = header.split(" ")[1];

		if(!StringUtils.hasText(result))
			throw new TokenEmptyException("token is empty");

		return result;
	}

}
