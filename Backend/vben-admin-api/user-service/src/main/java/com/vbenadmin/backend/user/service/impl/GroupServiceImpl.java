package com.vbenadmin.backend.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commonweb.models.vo.PageResponseVO;
import com.vbenadmin.backend.user.converter.GroupConverter;
import com.vbenadmin.backend.user.converter.GroupInfoVOConverter;
import com.vbenadmin.backend.user.converter.context.GroupRelationContext;
import com.vbenadmin.backend.user.entity.Group;
import com.vbenadmin.backend.user.mapper.GroupMapper;
import com.vbenadmin.backend.user.models.dto.GroupRoleDTO;
import com.vbenadmin.backend.user.models.dto.GroupUserCountDTO;
import com.vbenadmin.backend.user.models.request.GroupCreateRequest;
import com.vbenadmin.backend.user.models.request.GroupQueryRequest;
import com.vbenadmin.backend.user.models.request.GroupUpdateRequest;
import com.vbenadmin.backend.user.models.vo.GroupInfoVO;
import com.vbenadmin.backend.user.service.IGroupRoleService;
import com.vbenadmin.backend.user.service.IGroupService;
import com.vbenadmin.backend.user.service.IUserGroupService;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户组表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

    private final GroupMapper groupMapper;
    private final GroupInfoVOConverter groupInfoVOConverter;
    private final GroupConverter groupConverter;
    private final IUserGroupService userGroupService;
    private final IGroupRoleService groupRoleService;

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
        List<GroupInfoVO> groupInfoVOs = groupInfoVOConverter.toVOList(groups, ctx);

        return new PageResponseVO<GroupInfoVO>(groupInfoVOs, groupPage.getTotal());
    }

    private GroupRelationContext getGroupRelationContext(List<String> groupIds) {
        // get role
        List<GroupRoleDTO> groupRoleRows = groupMapper.selectGroupRolesByGroupIds(groupIds);
        Map<String, List<String>> roleMap = groupRoleRows.stream()
                .collect(Collectors.groupingBy(GroupRoleDTO::getGroupId,
                        Collectors.mapping(GroupRoleDTO::getRole, Collectors.toList())));

        // get user count
        List<GroupUserCountDTO> groupUsersRows = groupMapper.countUsersByGroupIds(groupIds);
        Map<String, Integer> userCountMap = new HashMap<>();
        for (int i = 0; i < groupIds.size(); i++) {
            userCountMap.put(groupUsersRows.get(i).getGroupId(), groupUsersRows.get(i).getUserCount());
        }

        return new GroupRelationContext(roleMap, userCountMap);
    }

    @Override
    public GroupInfoVO getGroupDetailById(String id) {
        Group group = this.getById(id);
        List<String> roles = groupMapper.getRolesByGroupId(id);
        Integer userCount = groupMapper.countUsersByGroupId(id);

        GroupInfoVO groupInfoVO = groupInfoVOConverter.toVO(group);
        groupInfoVO.setRoles(roles);
        groupInfoVO.setUserCount(userCount);
        return groupInfoVO;
    }

    @Override
    @Transactional
    public void createGroup(GroupCreateRequest request) {
        if (existGroup(request.getCode()))
            throw new BizException(40901, "用户组已存在，不能出现重复的编码");

        List<String> roleIds = request.getRoleIds();
        List<String> userIds = request.getUserIds();

        Group group = groupConverter.toEntity(request);

        this.save(group);
        String groupId = group.getId();

        groupRoleService.bindGroupWithRoles(groupId, roleIds);
        userGroupService.bindGroupWithUsers(groupId, userIds);
    }

    @Override
    @Transactional
    public void updateGroup(String groupId, GroupUpdateRequest request) {
        if (this.getById(groupId) == null)
            throw new BizException(40401, "用户组不存在");

        if (existGroup(request.getCode()))
            throw new BizException(40000, "用户编码已存在");

        log.debug("尝试更新 groupId 为 {} 的用户", groupId);

        LambdaUpdateWrapper<Group> uw = Wrappers.lambdaUpdate(Group.class)
                .eq(Group::getId, groupId)
                .set(request.getName() != null, Group::getName, request.getName())
                .set(request.getCode() != null, Group::getCode, request.getCode())
                .set(request.getStatus() != null, Group::getStatus, request.getStatus())
                .set(request.getRemark() != null, Group::getRemark, request.getRemark());

        this.update(uw);

        if (request.getRoleIds() != null)
            groupRoleService.updateByRoleIds(groupId, request.getRoleIds());

        if (request.getUserIds() != null)
            userGroupService.updateByUserIds(groupId, request.getUserIds());

    }

    private boolean existGroup(String groupCode) {
        return this.lambdaQuery()
                .eq(Group::getCode, groupCode)
                .count() > 0;
    }

    @Override
    @Transactional
    public void deleteGroupById(@NotBlank String id) {
        if(this.getById(id) == null)
            throw new BizException(40401, "用户组不存在");

        // delete itself
        this.removeById(id);

        // delete related
        userGroupService.removeByGroupId(id);
        groupRoleService.removeByGroupId(id);
    }
}
