package com.lcz.wdf.dao.handler;

import com.trs.ai.ty.constant.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 枚举类转换
 *
 * @author lin.xuancheng
 * @version 1.0
 * @date 2020/11/20 14:09
 **/
@Slf4j
@MappedTypes({
        BaseEnum.class,
        TaskStatusEnum.class,
        OperationStatusEnum.class,
        EnumAITask.class,
        EvaluateStatusEnum.class
})
public class EnumTypeHandler<E extends BaseEnum> extends BaseTypeHandler<BaseEnum> {

    private final Class<E> type;

    public EnumTypeHandler (Class<E> type) {
        if (log.isDebugEnabled()) {
            log.debug("TyEnumTypeHandler : type == > " + type);
        }
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        if (code == 0 && rs.wasNull()) {
            return null;
        }
        return codeOf(code);
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        if (code == 0 && rs.wasNull()) {
            return null;
        }
        return codeOf(code);
    }

    @Override
    public BaseEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        if (code == 0 && cs.wasNull()) {
            return null;
        }
        return codeOf(code);
    }

    private BaseEnum codeOf(int resultInt) {
        E[] constants = type.getEnumConstants();
        for (BaseEnum baseEnum : constants) {
            if (baseEnum.getCode() == resultInt) {
                return baseEnum;
            }
        }
        return null;
    }
}
