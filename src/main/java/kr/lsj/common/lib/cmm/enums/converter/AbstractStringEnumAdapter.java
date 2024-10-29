package kr.lsj.common.lib.cmm.enums.converter;


import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.core.convert.converter.Converter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public abstract class AbstractStringEnumAdapter<T extends Enum<T>>  implements Converter<String, T>, TypeHandler<T> {

    private final Class<T> clazz;

    @Override
    public T convert(String source) {
        return getEnum(source.toUpperCase());
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,parameter == null ? null : getName(parameter));
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        return getEnum(rs.getString(columnName));
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        return getEnum(rs.getString(columnIndex));
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getEnum(cs.getString(columnIndex));
    }

    protected T getEnum(String s){
        return T.valueOf(clazz,s);
    }

    protected String getName(T parameter) { return parameter.name(); }
}
