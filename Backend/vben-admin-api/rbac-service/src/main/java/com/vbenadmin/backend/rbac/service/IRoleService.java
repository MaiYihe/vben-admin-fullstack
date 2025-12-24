package com.vbenadmin.backend.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vbenadmin.backend.commonweb.models.vo.PageResponseVO;
import com.vbenadmin.backend.rbac.entity.Role;
import com.vbenadmin.backend.rbac.models.request.RoleQueryRequest;
import com.vbenadmin.backend.rbac.models.vo.RoleInfoVO;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
public interface IRoleService extends IService<Role> {
    PageResponseVO<RoleInfoVO> getRoleListByRequest(RoleQueryRequest request);
}
