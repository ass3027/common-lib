import a.lsj.lib.auth.Auth;
import a.lsj.lib.auth.TokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class AllTest {

    @Test
    void tokenParseTest() {
        String key = "";
        TokenUtils tokenUtils = new TokenUtils();
        ReflectionTestUtils.setField(tokenUtils,"jwtSecretKey","d1359ca5e428d1ee3d351ab1fcded78b71a7e9b722394a286b20276f666074fe1308dc8d459202be0214e35681d873ae0b29ee9fa05ac6058a0454469abc0385");
        Auth auth = tokenUtils.getAuthFromToken("eyJyZWdEYXRlIjoxNzIyNTg1NjM4NjU4LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTm0iOiLstZzqs6DqtIDrpqzsnpAiLCJhdXRoQ2QiOiJST0xFX0FETUlOIiwidXNlcklkIjoiYWRtaW4iLCJzdWIiOiJhZG1pbiIsImV4cCI6MTcyMjY3MjAzOH0.e2IsQcd2qEKRjJJa6JHviwvuuXINkhB1X1BOZUCNn0s");

        System.out.println(auth.getId());
    }
}
