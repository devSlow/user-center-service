package com.slow.usercenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slow.usercenterservice.model.domain.User;
import com.slow.usercenterservice.service.UserService;
import com.slow.usercenterservice.mapper.UserMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * @author slow
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-05-18 15:59:50
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String password, String confirmPassword) {
//        1.判定非空 使用Apache common lang工具类
        if (StringUtils.isAnyBlank(userAccount, password, confirmPassword)) {
            return -1;
        }
//      账号长度不小于4位
        if (userAccount.length() < 4) {
            return -1;
        }
//        密码及确认不小于6位
        if (password.length() < 6 || confirmPassword.length() < 6) {
            return -1;
        }
//        账号不能包含特殊字符（正则表达式）

        String regex = "^[a-zA-Z0-9]+$";
        Matcher matcher = Pattern.compile(regex).matcher(userAccount);
        if (!matcher.matches()) {
            return -1;
        }
//        密码与确认密码相同
        if (password.equals(confirmPassword)) {
            return -1;
        }

        //        账户不能重复
//        查询的构造条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        设置查询条件
        queryWrapper.eq("userAccount", userAccount);
//        得到查询的数据
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

//        对密码进行加密存储到数据库（使用DigestUtils 进行加密）
//      假定盐 slow 加盐目的是为了二次混淆MD5
        final String SALT = "slow";
        String handlePassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(handlePassword);
//       插入数据
        boolean save = this.save(user);
        if (!save) {
            return -1;
        }
        return user.getId();
    }
}







