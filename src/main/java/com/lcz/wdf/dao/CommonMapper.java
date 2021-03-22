package com.lcz.wdf.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通用mapper
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/18 16:18
 **/
@Mapper
public interface CommonMapper {
    List<Map<Object, Object>> getAllDataByTableName(@Param("tableName") String tableName);

    List<JSONObject> selectColumnAndTypeByTableName(@Param("tableName") String tableName);

    void insertCommon(@Param("resultList") List<List<Object>> resultList,@Param("titles") List<String> titles, @Param("tableName") String tableName);
}
