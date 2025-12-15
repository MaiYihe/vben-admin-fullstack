package com.vbenadmin.backend.user.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {
    private String id;
    private String username;
    private String realName;
    private String avatar;
    private String description;
    private String homePath;
}
