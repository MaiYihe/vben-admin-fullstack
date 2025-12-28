package com.vbenadmin.backend.user.models.vo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupInfoVO {
    private String id;
    private String name;
    private String code;

    private List<String> roles;
    private Integer userCount;

    private Boolean status;
    private String remark;
    private LocalDateTime createTime;
}
