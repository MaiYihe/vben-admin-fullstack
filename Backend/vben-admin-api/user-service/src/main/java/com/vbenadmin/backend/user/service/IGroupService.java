package com.vbenadmin.backend.user.service;


import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vbenadmin.backend.user.entity.Group;
import com.vbenadmin.backend.user.models.dto.UserGroupDTO;

/**
 * <p>
 * 用户组表 服务类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-17
 */
public interface IGroupService extends IService<Group> {
    List<UserGroupDTO> getGroupsByUserIds(List<String> userIds);
}
