package com.vbenadmin.backend.user.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.user.entity.Group;
import com.vbenadmin.backend.user.mapper.GroupMapper;
import com.vbenadmin.backend.user.service.IGroupService;

import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 用户组表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-17
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {
}
