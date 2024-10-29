package kr.lsj.common.lib.cmm.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.lsj.common.lib.cmm.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * Service name : VAS_v2
 * Package name : kr.co.wkit.hdsvas.lib.cmm.response
 * Class   name : ErrorResponseBody
 * Description  : Exception 발생 시 GlobalExceptionHandler 에서 응답 메시지의 body 로 사용할 클래스이다.
 *
 * ==============================================================================
 *
 * </pre>
 * @date 2023-07-03
 * @author JINY
 * @version 1.0.0
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponseBody {
	private int status;
	private String errorCode;
	private String errorMsg;
	private List<FieldError> errors;
	private String reason;


	public ErrorResponseBody(ErrorCode errorCode) {
		this.status = errorCode.getStatus();
		this.errorCode = errorCode.getErrorCode();
		this.errorMsg = errorCode.getErrorMsg();
	}

	@SuppressWarnings("unused")
	public ErrorResponseBody(ErrorCode errorCode, List<FieldError> errors) {
		this(errorCode);
		this.errors = errors;
	}

	public ErrorResponseBody(ErrorCode errorCode, String reason) {
		this(errorCode);
		this.reason = reason;
	}


	/**
	 * 에러를 e.getBindingResult() 형태로 전달 받는 경우 해당 내용을 상세 내용으로 변경하는 기능을 수행한다.
	 */
	@Getter
	public static class FieldError {
		private final String field;
		private final String value;
		private final String reason;


		@Builder
		FieldError(String field, String value, String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}

		@SuppressWarnings("unused")
		public static List<FieldError> of(final String field, final String value, final String reason) {
			List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, reason));
			return fieldErrors;
		}

		@SuppressWarnings("unused")
		private static List<FieldError> of(final BindingResult bindingResult) {
			final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
			return fieldErrors.stream()
					.map(error -> new FieldError(
							error.getField(),
							error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
							error.getDefaultMessage()))
					.collect(Collectors.toList());
		}

	}
}


