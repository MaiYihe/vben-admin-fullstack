package com.vbenadmin.backend.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 用户组-角色关系表
 * </p>
 *
 * @author maihehe
 * @since 2025-12-29
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@TableName("sys_group_role")
public class GroupRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户组ID
     */
    @TableId("group_id")
    private String groupId;

    /**
     * 角色ID
     */
    @TableId("role_id")
    private String roleId;
}
