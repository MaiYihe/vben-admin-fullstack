package com.vbenadmin.backend.rbac.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vbenadmin.backend.rbac.entity.Resource;



/**
 * <p>
 * 系统资源（菜单/页面/按钮/内嵌/外链）表 Mapper 接口
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    List<String> selectAuthCodesByRoleIds(@Param("roleIds") List<String> roleIds);
}

