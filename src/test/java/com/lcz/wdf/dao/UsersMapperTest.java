package com.lcz.wdf.dao;

import com.lcz.wdf.TestPersistenceConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 用户mapper测试
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/5 16:43
 **/
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
//@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestPersistenceConfig.class)
public class UsersMapperTest {
}
