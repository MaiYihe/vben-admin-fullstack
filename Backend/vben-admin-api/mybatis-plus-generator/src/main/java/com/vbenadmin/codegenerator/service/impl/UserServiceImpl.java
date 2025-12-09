package com.vbenadmin.codegenerator.service.impl;

import com.vbenadmin.codegenerator.entity.User;
import com.vbenadmin.codegenerator.mapper.UserMapper;
import com.vbenadmin.codegenerator.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
