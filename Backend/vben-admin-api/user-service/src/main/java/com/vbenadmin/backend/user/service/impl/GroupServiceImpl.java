package com.vbenadmin.backend.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.user.entity.Group;
import com.vbenadmin.backend.user.mapper.GroupMapper;
import com.vbenadmin.backend.user.models.dto.UserGroupDTO;
import com.vbenadmin.backend.user.service.IGroupService;

import java.util.List;

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

    @Override
    public List<UserGroupDTO> getGroupsByUserIds(List<String> userIds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGroupsByUserIds'");
    }

}
