package a.lsj.lib.cmm.exception;

import a.lsj.lib.cmm.enums.ErrorCode;
import lombok.Getter;

/**
 * <pre>
 * Service name : VAS_v2
 * Package name : kr.co.wkit.hdsvas.lib.cmm.exception
 * Class   name : GlobalException
 * Description  :
 *
 * ==============================================================================
 *
 * </pre>
 * @date 2023-07-03
 * @author JINY
 * @version 1.0.0
 */
@Getter
public class GlobalException extends RuntimeException {

	private final ErrorCode errorCode;

	public GlobalException(ErrorCode errorCode) {
		super(errorCode.getErrorMsg());
		this.errorCode = errorCode;
	}

	public GlobalException(ErrorCode errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	public GlobalException(ErrorCode errorCode, Throwable cause) {
		super(errorCode.getErrorMsg(), cause);
		this.errorCode = errorCode;
	}

	public GlobalException(ErrorCode errorCode, String errorMsg, Throwable cause) {
		super(errorMsg, cause);
		this.errorCode = errorCode;
	}
}
