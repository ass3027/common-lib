package kr.lsj.common.lib.auth;

public interface JwtTokenUser {
    String getUserId();
    String getUserNm();
    String getAuthCd();
}
