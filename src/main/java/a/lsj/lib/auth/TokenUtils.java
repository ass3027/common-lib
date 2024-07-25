package a.lsj.lib.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
@Slf4j
@Component
public class TokenUtils {

	@Value("${auth.jwt.secret-key}")
	private String jwtSecretKey;

	private SecretKey secretKey;

	private SecretKey getSecretKey(){
		log.debug("jwtSecretKey : {}", jwtSecretKey);
		log.debug("secretKey : {}", secretKey);
		if(secretKey == null){
			secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
		}
		return secretKey;
	}

	/**
	 * Generate jwt token string.
	 *
	 * @param jwtTokenUser the user vo
	 * @param expireTime Expiry time of the creation token(second). 0 is default value(1 day). max is a month(30 day).
	 * @return the string
	 */
	public String generateJwtToken(JwtTokenUser jwtTokenUser, int expireTime) {
		// 사용자 시퀀스를 기준으로 JWT 토큰을 발급하여 반환해줍니다.
		JwtBuilder builder = Jwts.builder()
				.header()
					.add(createHeader())// Header 구성
				.and()
				.claims(createClaims(jwtTokenUser))                        // Payload - Claims 구성
				.subject(String.valueOf(jwtTokenUser.getUserId()))         // Payload - Subject 구성
				.signWith(getSecretKey(),Jwts.SIG.HS256)
				.expiration(createExpiredDate(expireTime));          // Expired Date 구성
		return builder.compact();
	}


	/**
	 * 사용자 정보를 기반으로 토큰을 생성하여 반환 해주는 메서드(만료시간 1일)
	 *
	 * @param jwtTokenUser the user vo
	 * @return String : 토큰
	 */
	public String generateJwtToken(JwtTokenUser jwtTokenUser) {
		return generateJwtToken(jwtTokenUser, 0);
	}


	private JwtParser getJwtParser(){
		return Jwts.parser()
				.verifyWith(getSecretKey())
				.build();
	}
	/**
	 * 토큰을 기반으로 사용자 정보를 반환 해주는 메서드
	 *
	 * @param token String : 토큰
	 * @return String : 사용자 정보
	 */
	public String parseTokenToUserInfo(String token) {
		return getClaimsFormToken(token)
				.getSubject();
	}

	/**
	 * 유효한 토큰인지 확인 해주는 메서드
	 *
	 * @param token String  : 토큰
	 * @return boolean      : 유효한지 여부 반환
	 */
	public boolean hasClaim(String token) {
		try {
			Claims claims = getClaimsFormToken(token);

			log.debug("expireTime :" + claims.getExpiration());
			log.debug("userId :" + claims.get("userId"));
			log.debug("userNm :" + claims.get("userNm"));

			return true;
		} catch (ExpiredJwtException exception) {
			log.warn("Token Expired");
			return false;
		} catch (JwtException exception) {
			log.warn("Token Tampered");
			return false;
		} catch (NullPointerException exception) {
			log.warn("Token is null");
			return false;
		}
	}

	/**
	 * Header 내에 토큰을 추출합니다.
	 *
	 * @param header 헤더
	 * @return String
	 */
	public String getTokenFromHeader(String header) {
		return header.split(" ")[1];
	}

	/**
	 * 토큰의 만료기간을 지정하는 함수
	 *
	 * @param expireTime Expiry time of the creation token(second). 0 is default value(1 day). max is a month(30 day)
	 * @return Calendar
	 */
	private Date createExpiredDate(int expireTime) {
		if (expireTime <= 0) {
			expireTime = 86400; //(1 day)60 * 60 * 24;
		} else if (expireTime > 2592000) {
			expireTime = 2592000; //(30 day)30 * 86400;
		}

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
	 * @param jwtTokenUser 사용자 정보
	 * @return Map<String, Object>
	 */
	private Map<String, Object> createClaims(JwtTokenUser jwtTokenUser) {
		// 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
		Map<String, Object> claims = new HashMap<>();

		log.debug("userId :" + jwtTokenUser.getUserId());
		log.debug("userNm :" + jwtTokenUser.getUserNm());
		log.debug("authCd :" + jwtTokenUser.getAuthCd());

		claims.put("userId", jwtTokenUser.getUserId());
		claims.put("userNm", jwtTokenUser.getUserNm());
		claims.put("authCd", jwtTokenUser.getAuthCd());
		return claims;
	}


	/**
	 * 토큰 정보를 기반으로 Claims 정보를 반환받는 메서드
	 *
	 * @param token : 토큰
	 * @return Claims : Claims
	 */
	private Claims getClaimsFormToken(String token) {
		return getJwtParser()
				.parseEncryptedClaims(token)
				.getPayload();
	}

	/**
	 * 토큰을 기반으로 사용자 정보를 반환받는 메서드
	 *
	 * @param token : 토큰
	 * @return String : 사용자 아이디
	 */
	public String getUserIdFromToken(String token) {
		Claims claims = getClaimsFormToken(token);
		return claims.get("userId").toString();
	}

	public String getAuthCdFromToken(String token) {
		Claims claims = getClaimsFormToken(token);
		return claims.get("authCd").toString();
	}

	public long getExpireTimeFromToken(String token) {
		Claims claims = getClaimsFormToken(token);
		final Date expiration = claims.getExpiration();
		if (expiration == null) return 1000;
		return expiration.getTime();
	}

}
