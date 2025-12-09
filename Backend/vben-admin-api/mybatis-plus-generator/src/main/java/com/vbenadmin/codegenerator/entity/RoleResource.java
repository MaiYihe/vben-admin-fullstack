package com.vbenadmin.codegenerator.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 角色-资源节点关系表
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Getter
@Setter
@ToString
@TableName("sys_role_resource")
public class RoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 资源节点ID
     */
    private Long resourceId;
}
