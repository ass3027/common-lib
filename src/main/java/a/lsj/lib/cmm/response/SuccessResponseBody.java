package a.lsj.lib.cmm.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import a.lsj.lib.cmm.enums.SuccessCode;
import lombok.*;

/**
 * <pre>
 * Service name : VAS_v2
 * Package name : kr.co.wkit.hdsvas.lib.cmm.response
 * Class   name : SuccessResponseBody
 * Description  : 요청 처리 성공시 반환하는 응답 Body
 *
 * ==============================================================================
 *
 * </pre>
 * @date 2023-07-03
 * @author JINY
 * @version 1.0.0
 */
@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessResponseBody<T> {

	// 응답 데이터
	private T datas;

	// 응답 코드
	private String responseCode;

	// 응답 메시지
	private String responseMsg;


	public SuccessResponseBody(T datas) {
		this.datas = datas;
	}

	public SuccessResponseBody(SuccessCode successCode) {
		this.responseCode = successCode.getCode();
		this.responseMsg = successCode.getMessage();
	}

}
