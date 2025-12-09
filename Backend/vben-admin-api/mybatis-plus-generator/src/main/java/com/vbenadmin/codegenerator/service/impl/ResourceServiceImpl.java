package com.vbenadmin.codegenerator.service.impl;

import com.vbenadmin.codegenerator.entity.Resource;
import com.vbenadmin.codegenerator.mapper.ResourceMapper;
import com.vbenadmin.codegenerator.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统资源（菜单/页面/按钮/内嵌/外链）表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

}
