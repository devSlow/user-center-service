package com.slow.usercenterservice.service;


import com.slow.usercenterservice.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
/**
 * 用户服务测试
 * @author slow
 */

class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    void testDigestUtils() {
//      假定盐 slow
        String source = "123456";
        String handle = DigestUtils.md5DigestAsHex(("slow" + source).getBytes());
        System.out.println(handle);
    }

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("slow");
        user.setUserAccount("212310620");
        user.setAvatarUrl("https://img1.baidu.com/it/u=451641322,4260356887&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1716138000&t=eeb4af299b891aef8cc42561f630dfce");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("1234");
        user.setEmail("1@qq.com");
        boolean res = userService.save(user);
        Long id = user.getId();
        System.out.println("id为：" + id);
//        断言主要用于测试，判定实际值是否等于你期望的值
        assertTrue(res);

    }

    @Test
    void userRegister() {
//        断言只推荐在测试类中使用
//        验证非空
        String userAccount = "";
        String password = "12345678";
        String confirmPassword = "12345678";
        long result = userService.userRegister(userAccount, password, confirmPassword);
        Assertions.assertEquals(-1, result);
//        验证账号不小于4位
        userAccount = "";
        result = userService.userRegister(userAccount, password, confirmPassword);
        Assertions.assertEquals(-1, result);
//        验证密码不小于6位
        userAccount = "test111";
        password="1234";
        confirmPassword="1234";
        result = userService.userRegister(userAccount, password, confirmPassword);
        Assertions.assertEquals(-1, result);
//        验证账号不能重复
        userAccount = "slow";
        password="12345678";
        confirmPassword="12345678";
        result = userService.userRegister(userAccount, password, confirmPassword);
        Assertions.assertEquals(-1, result);
//        验证不能包含特殊字符
        userAccount = "sl ow";
        password="12345678";
        confirmPassword="12345678";
        result = userService.userRegister(userAccount, password, confirmPassword);
        Assertions.assertEquals(-1, result);
//        验证密码和校验密码相同
        userAccount = "alibaba";
        password="12345678";
        confirmPassword="1234567";
        result = userService.userRegister(userAccount, password, confirmPassword);
        Assertions.assertEquals(-1, result);

    }
}