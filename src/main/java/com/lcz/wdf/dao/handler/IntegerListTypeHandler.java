package com.lcz.wdf.dao.handler;

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
import java.util.stream.Collectors;

/**
 * 整数数组类型处理
 */
@MappedTypes(Integer[].class)
@MappedJdbcTypes({JdbcType.VARCHAR, JdbcType.LONGVARCHAR})
public class IntegerListTypeHandler extends BaseTypeHandler<List<Integer>> {

	private static final String SPLITTER = ",";

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<Integer> parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, join(parameter));
	}

	@Override
	public List<Integer> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String reString = rs.getString(columnName);
		if (reString != null && reString.length() > 0) {
			return Arrays.stream(reString.split(SPLITTER))
					.map(Integer::valueOf).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	public List<Integer> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String reString = rs.getString(columnIndex);
		if (reString != null && reString.length() > 0) {
			return Arrays.stream(reString.split(SPLITTER))
					.map(Integer::valueOf).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	public List<Integer> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String reString = cs.getString(columnIndex);
		if (reString != null && reString.length() > 0) {
			return Arrays.stream(reString.split(SPLITTER))
					.map(Integer::valueOf).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	private String join(List<?> list) {
		return join(list, list.size());
	}

	private String join(List<?> list, int endIndex) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < endIndex; i++) {
			/**
			 * 在前面加，就不会出现最后多一个分隔符的现象同时判断不是第一个才加分隔符
			 */
			if (i > 0) {
				sb.append(SPLITTER);
			}

			if (list.get(i) != null) {
				sb.append(list.get(i));
			}
		}
		return sb.toString();

	}
}
