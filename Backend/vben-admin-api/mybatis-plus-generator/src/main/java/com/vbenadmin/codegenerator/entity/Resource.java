package com.vbenadmin.codegenerator.entity;

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
 * 系统资源（菜单/页面/按钮/内嵌/外链）表
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Getter
@Setter
@ToString
@TableName("sys_resource")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级ID
     */
    private Long pid;

    /**
     * 资源类型: catalog/page/button/embedded/link
     */
    private String type;

    /**
     * 资源名称（唯一）
     */
    private String name;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 前端组件路径
     */
    private String component;

    /**
     * 重定向路径
     */
    private String redirect;

    /**
     * 权限标识
     */
    private String authCode;

    /**
     * meta 配置(JSON 格式)
     */
    private String meta;

    /**
     * 状态 1启用 0禁用
     */
    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
