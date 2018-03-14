package com.yatang.sc.payment.mybatis.handler;

import com.yatang.sc.payment.enums.BaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yuwei on 2017/7/12.
 */
public class EnumTypeHandler extends BaseTypeHandler<BaseEnum> {
    private Class<BaseEnum> type;

    public EnumTypeHandler(Class<BaseEnum> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    private <R> BaseEnum convert(R status) {
        BaseEnum[] enumConstants = type.getEnumConstants();
        for (BaseEnum em : enumConstants) {
            if (em.getCode() instanceof String) {
                if (em.getCode().equals(status)) {
                    return em;
                }
            } else {
                if (em.getCode() == status) {
                    return em;
                }
            }

        }
        return null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement pPreparedStatement, int pI, BaseEnum pBaseEnum, JdbcType pJdbcType) throws SQLException {
        if (pJdbcType == null) {
            pJdbcType = JdbcType.VARCHAR;
        }
        pPreparedStatement.setObject(pI, pBaseEnum.getCode(), pJdbcType.TYPE_CODE);
    }

    @Override
    public BaseEnum getNullableResult(ResultSet pResultSet, String columnName) throws SQLException {
        Object columnsVal = pResultSet.getObject(columnName);
        if (columnsVal == null) {
            return null;
        }
        return convert(columnsVal);
    }

    @Override
    public BaseEnum getNullableResult(ResultSet pResultSet, int columnIndex) throws SQLException {
        Object columnsVal = pResultSet.getObject(columnIndex);
        if (columnsVal == null) {
            return null;
        }
        return convert(columnsVal);
    }

    @Override
    public BaseEnum getNullableResult(CallableStatement pCallableStatement, int columnIndex) throws SQLException {
        Object columnsVal = pCallableStatement.getObject(columnIndex);
        if (columnsVal == null) {
            return null;
        }
        return convert(columnsVal);
    }
}
