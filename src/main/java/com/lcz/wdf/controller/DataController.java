package com.lcz.wdf.controller;

import com.lcz.wdf.entity.exception.BizException;
import com.lcz.wdf.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * TODO
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/18 16:11
 **/
@Validated
@RestController
@RequestMapping("/data")
@Slf4j
public class DataController {

    @Resource
    private DataService dataService;

    @GetMapping("/export")
    public void exportExcel(@NotBlank(message = "表名称缺失！") String tableName, HttpServletResponse response) throws BizException {
        dataService.exportExcel(tableName,response);
    }

    @PostMapping("/upload")
    public void uploadExcel(
            @NotNull(message = "文件缺失！") MultipartFile file,
            @NotBlank(message = "表名称缺失！") String tableName)
            throws BizException, IOException {
        dataService.uploadExcel(file, tableName);
    }

}