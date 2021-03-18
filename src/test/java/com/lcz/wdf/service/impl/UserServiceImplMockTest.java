package com.lcz.wdf.service.impl;

import com.lcz.wdf.dao.UsersMapper;
import com.lcz.wdf.entity.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 用户业务层mock测试
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/12 11:35
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplMockTest {

    @InjectMocks private UserServiceImpl userService;

    @Mock private UsersMapper usersMapper;

    @Test
    void test_getIndex() {
        Mockito.when(usersMapper.selectByPrimaryKey(1))
                .thenReturn(new Users(1, "123", "123456", "张三", "qwer", 0,null));
        String except = "Users(id=1, account=123, password=123456, name=张三, salt=qwer, isLocked=0)";
        String actual = userService.getIndex();
        Assertions.assertEquals(except, actual);
    }
}
