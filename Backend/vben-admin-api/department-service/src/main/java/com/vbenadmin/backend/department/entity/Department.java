package com.vbenadmin.backend.department.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 
 * </p>
 *
 * @author maihehe
 * @since 2025-12-26
 */
@Getter
@Setter
@ToString
@TableName("sys_department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String pid;

    private String name;

    private Boolean status;

    private LocalDateTime createTime;

    /**
     * 描述备注
     */
    private String remark;
}
