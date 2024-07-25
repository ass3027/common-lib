package a.lsj.lib.cmm.enums.converter;

import a.lsj.lib.cmm.enums.ICodeEnum;
import a.lsj.lib.cmm.exception.EnumConvertException;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.Objects;

/**
 * <pre>
 * Service name : VAS_v2
 * Package name : kr.co.wkit.hdsvas.lib.cmm.enums.converter
 * Class   name : AbstractEnumConverter
 * Description  : JPA 사용 시 enum 과 db 값을 자동으로 변환해 주는 추상 클래스이다
 *
 * 사용법 : 멤버변수에 다음과 같이 어노테이션을 추가한다.
 * 예) @Convert(converter = xxxAttributeConverter.class)
 *
 * ==============================================================================
 *
 * </pre>
 * @date 2023-08-01
 * @author JINY
 * @version 1.0.0
 */
@Slf4j
public abstract class AbstractAttributeConverter<E extends Enum<E> & ICodeEnum<?>, T> implements AttributeConverter<E, T> {

	private final Class<E> enumClass;

	public AbstractAttributeConverter(Class<E> enumClass) { this.enumClass = enumClass; }


	@SuppressWarnings("unchecked")
	@Override
	public T convertToDatabaseColumn(E attribute) {
		log.debug("[JPA Convert:: enum to db] attribute class : " + attribute);
		if (Objects.isNull(attribute)) {
//			throw new EnumConvertException("Enum Converting error - YardType is null");
			return null;
		}
		return (T) attribute.getCode();
	}


	@Override
	public E convertToEntityAttribute(T dbData) {
		if (dbData == null || dbData.toString().isEmpty()) {
//			throw new EnumConvertException("Enum Converting error - dbData is blank.");
			return null;
		}
		log.debug("[JPA Convert:: db to enum] enum class : " + this.enumClass);
		return EnumSet.allOf(enumClass).stream()
				.filter(e -> e.getCode().equals(dbData))
				.findAny()
				.orElseThrow( () -> new EnumConvertException( String.format("Not found enum=[%s] code=[%s]", enumClass.getName(), dbData) ) );
	}
}
