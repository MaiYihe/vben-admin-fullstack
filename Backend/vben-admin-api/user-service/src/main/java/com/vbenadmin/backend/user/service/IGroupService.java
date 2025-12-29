package com.vbenadmin.backend.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.vbenadmin.backend.commonweb.models.vo.PageResponseVO;
import com.vbenadmin.backend.user.entity.Group;
import com.vbenadmin.backend.user.models.request.GroupCreateRequest;
import com.vbenadmin.backend.user.models.request.GroupQueryRequest;
import com.vbenadmin.backend.user.models.vo.GroupInfoVO;

/**
 * <p>
 * 用户组表 服务类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-17
 */
public interface IGroupService extends IService<Group> {
    PageResponseVO<GroupInfoVO> getGroupListByRequest(GroupQueryRequest request);

    GroupInfoVO getGroupDetailById(String id);

    void createGroup(GroupCreateRequest request);
}
