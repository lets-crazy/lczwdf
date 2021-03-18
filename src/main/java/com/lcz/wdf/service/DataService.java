package com.lcz.wdf.service;

import com.lcz.wdf.entity.exception.BizException;

import javax.servlet.http.HttpServletResponse;

public interface DataService {
    void exportExcel(String tableName, HttpServletResponse response) throws BizException;
}
