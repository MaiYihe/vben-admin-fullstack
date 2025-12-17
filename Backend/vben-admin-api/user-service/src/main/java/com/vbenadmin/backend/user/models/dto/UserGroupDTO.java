package com.vbenadmin.backend.user.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserGroupDTO {
    private String userId;
    private String groupName;
}
