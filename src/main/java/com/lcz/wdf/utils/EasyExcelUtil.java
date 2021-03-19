package com.lcz.wdf.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * easyExcel工具类
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/2 16:34
 */
public class EasyExcelUtil {

    /**
     * 请在此处输入方法描述信息
     *
     * @param response HttpServletResponse
     * @param headFiled 表头
     * @param data 表数据
     * @author li.chengzhen
     * @since 2021/3/2 16:43
     */
    public static void export(
            HttpServletResponse response, List<List<String>> headFiled, List<List<Object>> data)
            throws IOException {
        EasyExcel.write(response.getOutputStream())
                .head(headFiled)
                // 样式
                .registerWriteHandler(getHorizontalCellStyleStrategy())
                .autoCloseStream(Boolean.FALSE)
                .sheet(0)
                .doWrite(data);
    }
    /**
     * 设置请求头、文件名
     *
     * @param fileName excel文件名
     */
    public static void setResponse(HttpServletResponse response, String fileName) {
        // 编码设置成UTF-8，excel文件格式为.xlsx
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // 这里URLEncoder.encode可以防止中文乱码 和easyexcel本身没有关系
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
    }

    /**
     * excel首列序号列样式
     *
     * @param workbook Workbook
     * @return org.apache.poi.ss.usermodel.CellStyle
     * @author li.chengzhen
     * @since 2021/3/2 16:00
     */
    public static CellStyle firstCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        // 居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        // 设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        // 文字
        Font font = workbook.createFont();
        font.setBold(Boolean.TRUE);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 用于设置excel导出时的样式 easyexcel 导出样式
     *
     * @return com.alibaba.excel.write.style.HorizontalCellStyleStrategy
     * @author li.chengzhen
     * @since 2021/3/2 15:59
     */
    public static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 11);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了
        // 背景设置
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());
        // 文字
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 11);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 边框
        contentWriteCellStyle.setBorderBottom(BorderStyle.NONE);
        contentWriteCellStyle.setBorderLeft(BorderStyle.NONE);
        contentWriteCellStyle.setBorderRight(BorderStyle.NONE);
        contentWriteCellStyle.setBorderTop(BorderStyle.NONE);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }
}
