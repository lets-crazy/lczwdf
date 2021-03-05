package com.lcz.wdf.dao.handler;

import com.lcz.wdf.utils.StringUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 字符串数组类型处理
 */
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        String text = String.join(",", parameter);
        ps.setString(i, text);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String resultString = rs.getString(columnName);
        if (StringUtil.isNotEmpty(resultString)) {
            return Arrays.asList(resultString.split(","));
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String resultString = rs.getString(columnIndex);
        if (StringUtil.isNotEmpty(resultString)) {
            return Arrays.asList(resultString.split(","));
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String resultString = cs.getString(columnIndex);
        if (StringUtil.isNotEmpty(resultString)) {
            return Arrays.asList(resultString.split(","));
        }
        return Collections.emptyList();
    }
}
