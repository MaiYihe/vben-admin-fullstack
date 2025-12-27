package com.vbenadmin.codegenerator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 用户-部门联表
 * </p>
 *
 * @author maihehe
 * @since 2025-12-27
 */
@Getter
@Setter
@ToString
@TableName("sys_user_department")
public class UserDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId("user_id")
    private String userId;

    /**
     * 部门ID
     */
    @TableId("department_id")
    private String departmentId;
}
