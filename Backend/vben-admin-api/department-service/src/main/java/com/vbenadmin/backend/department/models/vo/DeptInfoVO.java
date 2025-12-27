package com.vbenadmin.backend.department.models.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DeptInfoVO {
    private String id;
    private String pid;

    private String name;
    private Boolean status;
    private LocalDateTime createTime;
    private String remark;
}
