package a.lsj.lib.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Token {
	private String accessToken;
	private String refreshToken;
	private String tokenType;
	private long expiresIn;
	private String scope;
}
