package a.lsj.lib.cmm.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * [공통 코드] API 통신에 대한 '에러 코드'를 Enum 형태로 관리를 한다.
 *
 * @author lee
 */
@SuppressWarnings("DanglingJavadoc")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorCode {
	/*************** HTTP Status Code : Error CodeList ***********************
	 400 : Bad Request
	 401 : Unauthorized
	 403 : Forbidden
	 404 : Not Found
	 500 : Internal Server Error
	 ***********************************************************************/

	/**
	 * **************** Custom Error Code *************************************
	 */
	// 4xx
	S400_GENERAL(400, "400000", "Request syntax not recognized."),
	S400_INVALID_VALUE(400, "400001", "Argument value is not valid."),
	S400_MISSING_MANDATORY_ATTRIBUTE(400, "400002", "A required argument value is missing."),

	S401_GENERAL(401, "401000", "Id or password do not match."),
	S401_NOT_VERIFIED(401, "401001", "Not verified."),
	S401_TOKEN_IS_NULL(401, "401002", "Token does not exist."),
	S401_TOKEN_EXPIRATION(401, "401003", "Token expiration."),
	S401_INVALID_TOKEN(401, "401004", "Invalid token."),
	S401_PASSWORD_MISMATCH(401, "401005", "password don't match."),
	S401_INVALID_USER(401, "401006", "user status is 0 (useYn = N)."),

	S403_GENERAL(403, "403000", "Make a message."),
	S403_PERMISSION_DENIED(403, "403001", "You don't have permission."),

	S404_GENERAL(404, "404000", "A Page or file not found."),
	S404_FILE_NOT_FOUND(404, "404001", "File not found."),
	S404_OBJECT_NOT_FOUND(404, "404002", "A Object not found."),

	S409_GENERAL(409, "409000", "The client's request conflicted with the server's state."),
	S409_ALREADY_IN_CONTROL(409, "409001", "It's already under control."),

	// 5xx
	S500_GENERAL(500, "500000", "Internal Server Error."),
	S500_DB_ERROR(500, "500101", "Internal Server Error."),
	S500_FAILED_SELECT(500, "500102", "Failed select."),
	S500_FAILED_INSERT(500, "500103", "Failed insert."),
	S500_FAILED_UPDATE(500, "500104", "Failed update."),
	S500_FAILED_DELETE(500, "500105", "Failed delete."),
	S500_EXIST_OBJECT(500, "500106", "It already exists."),
	S500_NOT_EXIST_OBJECT(500, "500107", "It does not exist."),

	S500_JSON_PARSE_ERROR(500, "500201", "An JsonParseException occurred."),
	S500_JSON_PROCESSING_ERROR(500, "500202", "An JsonProcessingException occurred."),

	S501_GENERAL(501, "501000", "Not Implemented."),
	S501_COMING_SOON(501, "501001", "Not Implemented Coming Soon.")

	; // End

	/**
	 * ********************** Error Code Constructor **************************
	 */
	// 에러 코드의 '코드 상태'을 반환한다.
	private int status;

	// 에러 코드의 '코드간 구분 값'을 반환한다.
	private String errorCode;

	// 에러 코드의 '코드 메시지'을 반환한다.
	private String errorMsg;

	// 생성자 구성
	ErrorCode(final int status, final String errorCode, final String errorMsg) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
}
