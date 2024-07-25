package a.lsj.lib.cmm.enums.converter;

import a.lsj.lib.cmm.enums.ICodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <pre>
 * Service name : VAS_v2
 * Package name : kr.co.wkit.hdsvas.app.cmm.enums
 * Class   name : AbstractTypeHandler
 * Description  : Mybatis 사용 시 enum 과 db 값을 자동으로 변환해 주는 추상 클래스이다.
 *
 * 사용법 : mybatis-config.xml 에 다음과 같이 설정한다.
 * 예)
 * <typeHandlers>
 * 		<typeHandler handler="com.example.enum.xxxTypeHandler"/>
 * </typeHandlers>
 *
 *
 * ==============================================================================
 *
 * </pre>
 * @date 2023-08-01
 * @author JINY
 * @version 1.0.0
 */
@Slf4j
public abstract class AbstractTypeHandler<E extends Enum<E> & ICodeEnum<T>,T> implements TypeHandler<E> {
	private final Class <E> type;

	public AbstractTypeHandler(Class <E> type) {
		this.type = type;
	}

	@Override
	public void setParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		log.debug("[Mybatis TypeHandler:: enum to db] enum : " + parameter);
		ps.setObject(i, parameter == null ? null : parameter.getCode());
	}

	@Override
	public E getResult(ResultSet rs, String columnName) throws SQLException {
		log.debug("[Mybatis TypeHandler:: db to enum] columnName : " + columnName);

		@SuppressWarnings("unchecked")
		T code = (T)rs.getObject(columnName);
		return getCodeEnum(code);
	}

	@Override
	public E getResult(ResultSet rs, int columnIndex) throws SQLException {
		log.debug("[Mybatis TypeHandler:: db to enum] columnIndex : " + columnIndex);

		@SuppressWarnings("unchecked")
		T code = (T)rs.getObject(columnIndex);

		return getCodeEnum(code);
	}

	@Override
	public E getResult(CallableStatement cs, int columnIndex) throws SQLException {
		log.debug("[Mybatis TypeHandler:: db to enum](CallableStatement) columnIndex : " + columnIndex);

		@SuppressWarnings("unchecked")
		T code = (T)cs.getObject(columnIndex);
		return getCodeEnum(code);
	}

	protected E getCodeEnum(T code) {
		try {
			E[] enumConstants = type.getEnumConstants();
			for (E constant: enumConstants) {
				if (constant.getCode().equals(code)) {
					return constant;
				}
			}
			return null;
		} catch (Exception e) {
			throw new TypeException("Can't make enum object '" + type + "'", e);
		}
	}
}
