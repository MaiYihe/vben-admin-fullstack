package com.vbenadmin.backend.rbac.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vbenadmin.backend.rbac.converter.ResourceInfoVOConverter;
import com.vbenadmin.backend.rbac.entity.Resource;
import com.vbenadmin.backend.rbac.mapper.ResourceMapper;
import com.vbenadmin.backend.rbac.models.vo.ResourceInfoVO;
import com.vbenadmin.backend.rbac.service.IResourceService;

import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 系统资源（菜单/页面/按钮/内嵌/外链）表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    private final ResourceInfoVOConverter resourceInfoVOConverter;
    private final ObjectMapper objectMapper;// 用于 json -> java 类反序列化

    @Override
    public List<String> getIdsByAuthCodes(List<String> authCodes) {
        if (authCodes == null || authCodes.isEmpty())
            return List.of();

        return this.lambdaQuery()
                .in(Resource::getAuthCode, authCodes)
                .list()
                .stream()
                .map(Resource::getId)
                .toList();
    }

    @Override
    public List<ResourceInfoVO> getAllResourceList() {
        List<Resource> resources = this.lambdaQuery()
                .eq(Resource::getStatus, 1)
                .list();

        List<ResourceInfoVO> rezInfoVOs = resourceInfoVOConverter.toVOList(resources, objectMapper);

        return buildTree(rezInfoVOs);
    }

    private List<ResourceInfoVO> buildTree(List<ResourceInfoVO> list) {

        if (list == null || list.isEmpty()) {
            return List.of();
        }

        // id -> node
        Map<String, ResourceInfoVO> map = list.stream()
                .collect(Collectors.toMap(
                        ResourceInfoVO::id,
                        Function.identity()));

        List<ResourceInfoVO> roots = new ArrayList<>();

        for (ResourceInfoVO node : list) {
            String pid = node.pid();

            if (pid == null || "0".equals(pid) || pid.isBlank()) {
                roots.add(node);
            } else {
                ResourceInfoVO parent = map.get(pid);
                if (parent != null) {
                    parent.children().add(node);
                }
            }
        }

        sortTreeByOrder(roots);

        return roots;
    }

    private void sortTreeByOrder(List<ResourceInfoVO> nodes){
        if(nodes == null || nodes.isEmpty()) return;

        nodes.sort(Comparator.comparingInt(x -> x.meta().order()));

        // 递归对子节点进行排序
        for(ResourceInfoVO node : nodes){
            sortTreeByOrder(node.children());
        }
    }

}
