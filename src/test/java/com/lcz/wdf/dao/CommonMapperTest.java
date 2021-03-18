package com.lcz.wdf.dao;

import com.alibaba.fastjson.JSONObject;
import com.lcz.wdf.TestPersistenceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/18 16:53
 **/
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestPersistenceConfig.class)
class CommonMapperTest {

    @Resource
    private CommonMapper commonMapper;

    @Test
    void selectColumnAndTypeByTableName(){
        List<JSONObject> jsonObjects = commonMapper.selectColumnAndTypeByTableName("users");
        System.out.println(jsonObjects.toString());
    }
}
