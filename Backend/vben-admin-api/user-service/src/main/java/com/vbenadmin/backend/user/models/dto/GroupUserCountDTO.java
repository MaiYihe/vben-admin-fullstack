package com.vbenadmin.backend.user.models.dto;

import lombok.Data;

@Data
public class GroupUserCountDTO {
    private String groupId;
    private Integer userCount;    
}
