package com.vbenadmin.backend.user.models.vo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoVO {
    private String id;
    private String avatar;
    private String username;
    private String realName;
    private Byte status;
    private LocalDateTime createTime;
    private String remark;

    private List<String> roles;
    private List<String> groups;
}
