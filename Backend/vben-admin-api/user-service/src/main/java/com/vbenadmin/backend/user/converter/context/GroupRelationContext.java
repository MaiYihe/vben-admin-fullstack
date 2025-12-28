package com.vbenadmin.backend.user.converter.context;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GroupRelationContext {
    private final Map<String,List<String>> roleMap;
    private final Map<String,Integer> usersCountMap;

    public List<String> getRoles(String groupId){
        return roleMap.getOrDefault(groupId, List.of());
    }
    public Integer getUsersCount(String groupId){
        return usersCountMap.getOrDefault(groupId, 0);
    }
}
