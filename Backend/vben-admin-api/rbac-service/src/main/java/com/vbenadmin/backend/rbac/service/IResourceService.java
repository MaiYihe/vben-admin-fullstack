package com.vbenadmin.backend.rbac.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vbenadmin.backend.rbac.entity.Resource;

/**
 * <p>
 * 系统资源（菜单/页面/按钮/内嵌/外链）表 服务类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
public interface IResourceService extends IService<Resource> {
    List<String> getIdsByAuthCodes(List<String> authCodes);
}
