package com.vbenadmin.backend.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.commonweb.models.vo.PageResponseVO;
import com.vbenadmin.backend.user.converter.GroupInfoVOConverter;
import com.vbenadmin.backend.user.converter.context.GroupRelationContext;
import com.vbenadmin.backend.user.entity.Group;
import com.vbenadmin.backend.user.mapper.GroupMapper;
import com.vbenadmin.backend.user.models.dto.GroupRoleDTO;
import com.vbenadmin.backend.user.models.dto.GroupUserCountDTO;
import com.vbenadmin.backend.user.models.request.GroupQueryRequest;
import com.vbenadmin.backend.user.models.vo.GroupInfoVO;
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

    private final GroupMapper groupMapper;
    private final GroupInfoVOConverter groupInfoVOConverter;

    @Override
    public PageResponseVO<GroupInfoVO> getGroupListByRequest(GroupQueryRequest request) {
        Page<Group> page = new Page<>(request.getPage(), request.getPageSize());

        LambdaQueryWrapper<Group> wrapper = Wrappers.lambdaQuery(Group.class)
                .like(request.getName() != null, Group::getName, request.getName())
                .like(request.getCode() != null, Group::getCode, request.getCode())
                .eq(request.getStatus() != null, Group::getStatus, request.getStatus())
                .like(request.getRemark() != null, Group::getRemark, request.getRemark())
                .ge(request.getStartTime() != null, Group::getCreateTime, request.getStartTime())
                .le(request.getEndTime() != null, Group::getCreateTime, request.getEndTime());

        if (request.getRoles() != null) {
            wrapper.in(Group::getId, groupMapper.selectGroupIdsByRoles(request.getRoles()));
        }

        IPage<Group> groupPage = groupMapper.selectPage(page, wrapper);
        List<Group> groups = groupPage.getRecords();

        if (groups.isEmpty()) {
            return new PageResponseVO<>(null, 0);
        }

        List<String> groupIds = groups.stream()
            .map(Group::getId)
            .toList();

        // mapstruct(List<Group> -> List<GroupInfoVO>)
        GroupRelationContext ctx = getGroupRelationContext(groupIds);
        List<GroupInfoVO> groupInfoVOs = groupInfoVOConverter.toVOList(groups,ctx);

        return new PageResponseVO<GroupInfoVO>(groupInfoVOs, groupPage.getTotal());
    }

    private GroupRelationContext getGroupRelationContext(List<String> groupIds){
        // get role
        List<GroupRoleDTO> groupRoleRows = groupMapper.selectGroupRolesByGroupIds(groupIds);
        Map<String,List<String>> roleMap = groupRoleRows.stream()
            .collect(Collectors.groupingBy(GroupRoleDTO::getGroupId,
                        Collectors.mapping(GroupRoleDTO::getRole, Collectors.toList())));

        // get user count
        List<GroupUserCountDTO> groupUsersRows = groupMapper.countUsersByGroupIds(groupIds);
        Map<String,Integer> userCountMap = new HashMap<>();
        for(int i=0;i<groupIds.size();i++){
            userCountMap.put(groupUsersRows.get(i).getGroupId(), groupUsersRows.get(i).getUserCount());
        }
        
        return new GroupRelationContext(roleMap, userCountMap);
    }
}
