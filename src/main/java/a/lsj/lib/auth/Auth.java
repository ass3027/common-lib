package a.lsj.lib.auth;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auth {
	private String id;
	private String role;
}
