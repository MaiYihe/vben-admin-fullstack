package com.vbenadmin.codegenerator.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Getter
@Setter
@ToString
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码hash
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 个人简介
     */
    private String desc;

    /**
     * 默认首页路径
     */
    private String homePath;

    /**
     * 状态 1启用 0禁用
     */
    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
