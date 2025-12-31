package com.vbenadmin.backend.user.entity;

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
 * 用户组表
 * </p>
 *
 * @author maihehe
 * @since 2025-12-17
 */
@Getter
@Setter
@ToString
@TableName("sys_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组ID（UUID）
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 组显示名
     */
    private String name;

    /**
     * 系统编码（唯一，不可随意修改）
     */
    private String code;

    /**
     * 状态：1=启用，0=禁用
     */
    private Boolean status;

    /**
     * 描述备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
