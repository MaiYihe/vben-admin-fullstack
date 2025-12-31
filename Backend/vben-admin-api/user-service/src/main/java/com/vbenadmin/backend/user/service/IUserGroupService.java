package com.vbenadmin.backend.user.service;

import java.util.List;

public interface IUserGroupService {
    void removeByUserId(String userId);

    void bindGroupWithUsers(String groupId, List<String> userIds);

    void updateByUserIds(String groupId, List<String> userIds);

    void removeByGroupId(String id);
}
