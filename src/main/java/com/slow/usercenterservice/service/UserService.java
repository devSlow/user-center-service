package com.slow.usercenterservice.service;

import com.slow.usercenterservice.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户服务
* @author slow
*  @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-05-18 15:59:50
*/
public interface UserService extends IService<User> {
//    用户注册，返回用户id
    long userRegister(String userAccount,String password,String confirmPassword);

}
