package com.vbenadmin.backend.user.converter.context;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRelationContext {
    private final Map<String, List<String>> roleMap;
    private final Map<String, List<String>> groupMap;

    public List<String> getRoles(String userId) {
        return roleMap.getOrDefault(userId, List.of());
    }
    public List<String> getGroups(String userId) {
        return groupMap.getOrDefault(userId, List.of());
    }
}
