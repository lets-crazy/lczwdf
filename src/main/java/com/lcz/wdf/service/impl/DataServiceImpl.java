package com.lcz.wdf.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.lcz.wdf.dao.CommonMapper;
import com.lcz.wdf.entity.exception.BizException;
import com.lcz.wdf.listener.NoModelDataListener;
import com.lcz.wdf.service.DataService;
import com.lcz.wdf.utils.EasyExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 临时数据业务层实现
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/18 16:12
 **/
@Service
@Slf4j
public class DataServiceImpl implements DataService {

    @Resource
    private CommonMapper commonMapper;

    @Override
    public void exportExcel(String tableName, HttpServletResponse response) throws BizException {
        try {
            exportFile( tableName, response);
        } catch (Exception e) {
            log.error("--- entity excel export failed ---", e);
            throw new BizException("实体excel导出失败！");
        }
    }

    private void exportFile(String tableName, HttpServletResponse response) throws IOException {
        //获取表字段
        List<List<String>> headFiled = getHeadFiled(tableName);
        List<Map<Object, Object>> originData = commonMapper.getAllDataByTableName(tableName);
        List<List<Object>> data = getData(originData, tableName);
        EasyExcelUtil.setResponse(response, tableName);
        EasyExcelUtil.export(response, headFiled, data);
    }

    private List<List<Object>> getData(List<Map<Object, Object>> originData, String tableName) {
        List<JSONObject> allFiled = commonMapper.selectColumnAndTypeByTableName(tableName);
        List<List<Object>> result = new ArrayList<>(originData.size());
        List<String> list1 = new ArrayList<>();
        for (JSONObject filed : allFiled) {
            String columnName = filed.get("COLUMN_NAME").toString();
            list1.add(columnName);
        }
        for (Map<Object, Object> temp : originData) {
            List<Object> list = new ArrayList<>();
            for (String s : list1) {
                Object o = temp.get(s);
                list.add(o);
            }
            result.add(list);
        }
        return result;
    }

    private List<List<String>> getHeadFiled(String tableName) {
        List<List<String>> headFiled = new ArrayList<>();
        List<JSONObject> allFiled = commonMapper.selectColumnAndTypeByTableName(tableName);
        for (JSONObject filed : allFiled) {
            String columnName = filed.get("COLUMN_NAME").toString();
            List<String> list1 = new ArrayList<>();
            list1.add(columnName);
            headFiled.add(list1);
        }
        return headFiled;
    }

    @Override
    public void uploadExcel(MultipartFile multipartFile, String tableName) throws BizException, IOException {
        final String fileType = ".xlsx";
        String name = multipartFile.getOriginalFilename();
        assert name != null;
        if (name.endsWith(fileType)) {
            log.info("开始解析Excel：");
            readExcelAndStore(multipartFile.getInputStream(), tableName);
            log.info("实体数据导入成功！");
        } else {
            throw new BizException("导入失败，文件名格式不正确！");
        }
    }

    private void readExcelAndStore(InputStream inputStream, String tableName) {

        //初始化监听器
        NoModelDataListener noModelDataListener = new NoModelDataListener(this, tableName);
        List<Object> objects = EasyExcel.read(inputStream, noModelDataListener).doReadAllSync();


    }

    @Override
    public void addData(List<Map<String, Object>> data, String tableName, List<String> keyList) {
            commonMapper.insertCommon();
    }
}
