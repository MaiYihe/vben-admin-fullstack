package com.vbenadmin.codegenerator.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Getter
@Setter
@ToString
@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色备注
     */
    private String remark;

    /**
     * 状态：1 启用，0 禁用
     */
    private Byte status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
