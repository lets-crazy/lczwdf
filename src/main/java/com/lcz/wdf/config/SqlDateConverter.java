package com.lcz.wdf.config;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * sqlDate转换器。将日期转换成Excel的数字形式
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/2 14:49
 */
@Component
public class SqlDateConverter implements Converter<Date> {

    @Override
    public Class supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Date convertToJavaData(
            CellData cellData,
            ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        return null;
    }

    @Override
    public CellData convertToExcelData(
            Date value,
            ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new CellData(
                    BigDecimal.valueOf(
                            DateUtil.getExcelDate(
                                    value, globalConfiguration.getUse1904windowing())));
        } else {
            return new CellData(
                    BigDecimal.valueOf(
                            DateUtil.getExcelDate(
                                    value,
                                    contentProperty
                                            .getDateTimeFormatProperty()
                                            .getUse1904windowing())));
        }
    }
}
