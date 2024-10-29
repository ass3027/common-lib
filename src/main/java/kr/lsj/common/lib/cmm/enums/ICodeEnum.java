package kr.lsj.common.lib.cmm.enums;

import java.util.Arrays;

/**
 * <pre>
 * Service name : VAS_v2
 * Package name : kr.co.wkit.hdsvas.lib.cmm.enums
 * Class   name : CodeType
 * Description  :
 *
 * ==============================================================================
 *
 * </pre>
 * @date 2023-08-01
 * @author JINY
 * @version 1.0.0
 */
@SuppressWarnings("unused")
public interface ICodeEnum<T> {
	T getCode();

	@SuppressWarnings("unchecked")
	default T[] constants() {
		return (T[])Arrays.stream(getClass().getEnumConstants()).map(ICodeEnum::getCode).toArray();
	}

	default boolean isEmpty() throws UnsupportedOperationException {
		if (getCode() instanceof String) {
			return ((String) getCode()).isEmpty();
		} else {
			throw new UnsupportedOperationException("Code type is not String.");
		}
	}


}
