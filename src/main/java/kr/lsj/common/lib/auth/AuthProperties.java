package kr.lsj.common.lib.auth;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "common-lib.auth")
public class AuthProperties {
    private String jwtSecretKey;
    private int expiration;
}
