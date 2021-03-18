package com.lcz.wdf.dao;

import com.lcz.wdf.TestPersistenceConfig;
import com.lcz.wdf.entity.Users;
import com.lcz.wdf.utils.TestSqlUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 用户mapper测试
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/5 16:43
 **/
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestPersistenceConfig.class)
class UsersMapperTest {

    @Resource private UsersMapper usersMapper;

    @BeforeEach
    void init() throws SQLException, ClassNotFoundException {
        TestSqlUtil.clearTableDatas("users");
    }

    @Test
    void test_selectOne(){
        usersMapper.insert(new Users(null, "admin", "123456", "管理员", "qwer", 0));
        Users users = usersMapper.selectByPrimaryKey(1);
        assertEquals(new Users(1, "admin", "123456", "管理员", "qwer", 0), users);
    }
}
