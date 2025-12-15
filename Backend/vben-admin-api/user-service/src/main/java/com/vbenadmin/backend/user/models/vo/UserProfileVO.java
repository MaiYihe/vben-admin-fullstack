package com.vbenadmin.backend.user.models.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileVO {
    private String userId;
    private String username;
    private String realName;
    private String avatar;
    private String description;
    private String homePath;

    private List<String> roles;
    private String accessToken;
}
