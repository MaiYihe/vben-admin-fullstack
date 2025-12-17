package com.vbenadmin.codegenerator.service.impl;

import com.vbenadmin.codegenerator.entity.Group;
import com.vbenadmin.codegenerator.mapper.GroupMapper;
import com.vbenadmin.codegenerator.service.IGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-17
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

}
