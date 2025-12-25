package com.vbenadmin.backend.rbac.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commonweb.models.vo.PageResponseVO;
import com.vbenadmin.backend.rbac.controller.context.RoleRelationContext;
import com.vbenadmin.backend.rbac.converter.RoleConverter;
import com.vbenadmin.backend.rbac.converter.RoleInfoVOConverter;
import com.vbenadmin.backend.rbac.entity.Role;
import com.vbenadmin.backend.rbac.mapper.RoleMapper;
import com.vbenadmin.backend.rbac.models.dto.RolePermissionDTO;
import com.vbenadmin.backend.rbac.models.request.RoleCreateRequest;
import com.vbenadmin.backend.rbac.models.request.RoleQueryRequest;
import com.vbenadmin.backend.rbac.models.vo.RoleInfoVO;
import com.vbenadmin.backend.rbac.service.IRoleResourceService;
import com.vbenadmin.backend.rbac.service.IRoleService;

import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final RoleMapper roleMapper;
    private final RoleInfoVOConverter roleInfoVOConverter;
    private final RoleConverter roleConverter;
    private final IRoleResourceService roleResourceService;

    @Override
    public PageResponseVO<RoleInfoVO> getRoleListByRequest(RoleQueryRequest request) {

        Page<Role> page = new Page<>(request.getPage(), request.getPageSize());
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<Role>()
                .like(request.getName() != null, Role::getName, request.getName())
                .eq(request.getId() != null, Role::getId, request.getId())
                .like(request.getRemark() != null, Role::getRemark, request.getRemark())
                .eq(request.getId() != null, Role::getId, request.getId())
                .ge(request.getStartTime() != null, Role::getCreateTime, request.getStartTime())
                .le(request.getEndTime() != null, Role::getCreateTime, request.getEndTime())
                .orderByDesc(Role::getCreateTime);

        IPage<Role> rolePage = roleMapper.selectPage(page, wrapper);
        List<Role> roles = rolePage.getRecords();

        if (roles.isEmpty()) {
            return new PageResponseVO<>(null, 0);
        }

        RoleRelationContext ctx = getRoleRelationCtx(roles);

        // MapStruct
        List<RoleInfoVO> roleInfoVOs = roleInfoVOConverter.toVOList(roles, ctx);
        return new PageResponseVO<RoleInfoVO>(roleInfoVOs, rolePage.getTotal());
    }

    @Override
    public List<RoleInfoVO> getAllRoleList() {
        List<Role> roles = this.list();
        RoleRelationContext ctx = getRoleRelationCtx(roles);
        List<RoleInfoVO> roleInfoVOs = roleInfoVOConverter.toVOList(roles, ctx);

        return roleInfoVOs;
    }

    private RoleRelationContext getRoleRelationCtx(List<Role> roles) {
        // get roleRelation conext
        List<String> roleIds = roles.stream()
                .map(Role::getId)
                .toList();
        List<RolePermissionDTO> permissionRows = roleMapper.selectRolePermissionsByRoleIds(roleIds);

        Map<String, List<String>> permissionMap = permissionRows.stream()
                .collect(Collectors.groupingBy(
                        RolePermissionDTO::getRoleId,
                        Collectors.mapping(
                                RolePermissionDTO::getPermission,
                                Collectors.toList())));
        RoleRelationContext ctx = new RoleRelationContext(permissionMap);
        return ctx;
    }

    @Override
    @Transactional
    public void createRole(RoleCreateRequest roleCreateRequest) {
        if (roleCreateRequest == null)
            throw new BizException(40000, "创建请求为空");

        if (roleCreateRequest.getName() == null)
            throw new BizException(40000, "创建请求中没有角色名");

        if (existRole(roleCreateRequest.getName()))
            throw new BizException(40901, "角色已存在");
        
        // create role
        Role role = roleConverter.toEntity(roleCreateRequest);
        boolean saved = this.save(role);

        if (!saved)
            throw new BizException(50001, "创建失败，未知错误");

        // add role-resource relations
        if(roleCreateRequest.getPermissions() == null)
            return;

        roleResourceService.bindByAuthCodes(role.getId(),roleCreateRequest.getPermissions());
    }

    private boolean existRole(String roleName) {
        return this.lambdaQuery()
                .eq(Role::getName, roleName)
                .count() > 0;
    }

}
