package com.lcz.wdf.dao.handler;

import com.alibaba.fastjson.JSON;
import com.trs.ai.ty.entity.request.model.HyperParameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author lin.xuancheng
 * @version 1.0
 * @description JSON类型处理
 * @date 2020/11/19 17:20
 **/
@MappedTypes({
        List.class,
        Set.class,
        HyperParameter.class
})
@MappedJdbcTypes(JdbcType.LONGVARCHAR)
@Slf4j
@SuppressWarnings("unchecked")
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {

    private final Class<T> type;

    public JsonTypeHandler (Class<T> type) {
        if (log.isDebugEnabled()) {
            log.debug("JsonTypeHandler ( " + type + " ) ");
        }
        if (Objects.isNull(type)) {
            throw new IllegalStateException("type param cannot be null !");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter (PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }

    @Override
    public T getNullableResult (ResultSet rs, String columnName) throws SQLException {
        String resultStr = rs.getString(columnName);
        if (type.isAssignableFrom(List.class)) {
            return (T) buildListResult(columnName, resultStr);
        }
        return JSON.parseObject(resultStr, type);
    }

    @Override
    public T getNullableResult (ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public T getNullableResult (CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }


    private List<?> buildListResult (String name, String resultString) {
        Class<?> t = Object.class;
        final String parameters = "parameters";
        if (name.equals(parameters)) {
            t = HyperParameter.class;
        }
        return JSON.parseArray(resultString, t);
    }

}
