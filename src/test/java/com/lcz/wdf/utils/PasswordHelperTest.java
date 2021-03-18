package com.lcz.wdf.utils;

import com.lcz.wdf.entity.Users;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * TODO
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/18 11:06
 **/
@ExtendWith(SpringExtension.class)
public class PasswordHelperTest {


    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private String algorithmName = "MD5";
    private final int hashIterations = 5;

    public void encryptPassword(Users user) {
        // User对象包含最基本的字段Username和Password
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        // 将用户的注册密码经过散列算法替换成一个不可逆的新密码保存进数据，散列过程使用了盐
        String newPassword = new SimpleHash(algorithmName, user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), hashIterations).toHex();
        user.setPassword(newPassword);
    }

    @Test
    @DisplayName("生成密码")
    void newPassword(){
        String s = new SimpleHash(algorithmName, "1234",
                ByteSource.Util.bytes("373f647f075100720438ebab3d5dccb8"), hashIterations).toHex();
        System.err.println(s);
    }
}
