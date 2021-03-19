package com.lcz.wdf.service;

import com.lcz.wdf.entity.exception.BizException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataService {
    void exportExcel(String tableName, HttpServletResponse response) throws BizException;

    void uploadExcel(MultipartFile file, String tableName) throws BizException, IOException;

    void addData(List<Map<String, Object>> data, String tableName, List<String> keyList);
}
