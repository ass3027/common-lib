package kr.lsj.common.lib.cmm.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * [공통 코드] API 통신에 대한 '에러 코드'를 Enum 형태로 관리를 한다.
 * Success CodeList : 성공 코드를 관리한다.
 * Success Code Constructor: 성공 코드를 사용하기 위한 생성자를 구성한다.
 *
 * @author lee
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessCode {

    /**
     * ******************************* Success CodeList ***************************************
     */
    GENERAL(200, "200000", "요청이 성공적으로 처리되었습니다."),

    // 조회 성공
    SELECT(200, "200001", "SELECT SUCCESS"),

	// 삽입 성공
	INSERT(201, "201001", "INSERT SUCCESS"),

	// 수정 성공
	UPDATE(204, "204001", "UPDATE SUCCESS"),

	// 삭제 성공
    DELETE(205, "205001", "DELETE SUCCESS"),


    // 전송 성공 코드 (HTTP Response: 200 OK)
    SEND(200, "200002", "SEND SUCCESS"),

	// 로그인 성공
	LOGIN(200, "200003", "LOGIN SUCCESS"),

	// 토큰 리스페시 성공
	TOKEN_REFRESH(200, "200004", "TOKEN REFRESH SUCCESS"),



    ; // End

    /**
     * ******************************* Success Code Constructor ***************************************
     */
    // 성공 코드의 '코드 상태'를 반환한다.
    private int status;

    // 성공 코드의 '코드 값'을 반환한다.
    private String code;

    // 성공 코드의 '코드 메시지'를 반환한다.s
    private String message;

    // 생성자 구성
    SuccessCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
