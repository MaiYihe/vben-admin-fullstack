import type { Recordable } from '@vben/types';

import { requestClient } from '#/api/request';

export namespace SystemResourceApi {
  /** 徽标颜色集合 */
  export const BadgeVariants = [
    'default',
    'destructive',
    'primary',
    'success',
    'warning',
  ] as const;
  /** 徽标类型集合 */
  export const BadgeTypes = ['dot', 'normal'] as const;
  /** 菜单类型集合 */
  export const ResourceTypes = [
    'catalog',
    'page',
    'embedded',
    'link',
    'button',
  ] as const;
  /** 资源节点显示 */
  export interface SystemResource {
    [key: string]: any;
    /** 后端权限标识 */
    authCode: string;
    /** 子级 */
    children?: SystemResource[];
    /** 组件 */
    component?: string;
    /** 菜单ID */
    id: string;
    /** 菜单元数据 */
    meta?: {
      /** 激活时显示的图标 */
      activeIcon?: string;
      /** 作为路由时，需要激活的菜单的Path */
      activePath?: string;
      /** 固定在标签栏 */
      affixTab?: boolean;
      /** 在标签栏固定的顺序 */
      affixTabOrder?: number;
      /** 徽标内容(当徽标类型为normal时有效) */
      badge?: string;
      /** 徽标类型 */
      badgeType?: (typeof BadgeTypes)[number];
      /** 徽标颜色 */
      badgeVariants?: (typeof BadgeVariants)[number];
      /** 在菜单中隐藏下级 */
      hideChildrenInMenu?: boolean;
      /** 在面包屑中隐藏 */
      hideInBreadcrumb?: boolean;
      /** 在菜单中隐藏 */
      hideInMenu?: boolean;
      /** 在标签栏中隐藏 */
      hideInTab?: boolean;
      /** 图标 */
      icon?: string;
      /** 内嵌Iframe的URL */
      iframeSrc?: string;
      /** 是否缓存页面 */
      keepAlive?: boolean;
      /** 外链页面的URL */
      link?: string;
      /** 同一个路由最大打开的标签数 */
      maxNumOfOpenTab?: number;
      /** 无需基础布局 */
      noBasicLayout?: boolean;
      /** 是否在新窗口打开 */
      openInNewWindow?: boolean;
      /** 菜单排序 */
      order?: number;
      /** 额外的路由参数 */
      query?: Recordable<any>;
      /** 菜单标题 */
      title?: string;
    };
    /** 菜单名称 */
    name: string;
    /** 路由路径 */
    path: string;
    /** 父级ID */
    pid: string;
    /** 重定向 */
    redirect?: string;
    /** 菜单类型 */
    type: (typeof ResourceTypes)[number];
  }
}

/**
 * 获取资源节点数据列表
 */
async function getResourceList() {
  return requestClient.get<Array<SystemResourceApi.SystemResource>>(
    '/system/resource/list',
  );
}

async function isResourceNameExists(
  name: string,
  id?: SystemResourceApi.SystemResource['id'],
) {
  return requestClient.get<boolean>('/system/resource/name-exists', {
    params: { id, name },
  });
}

async function isResourcePathExists(
  path: string,
  id?: SystemResourceApi.SystemResource['id'],
) {
  return requestClient.get<boolean>('/system/resource/path-exists', {
    params: { id, path },
  });
}

/**
 * 创建资源节点
 * @param data 资源节点数据
 */
async function createResource(
  data: Omit<SystemResourceApi.SystemResource, 'children' | 'id'>,
) {
  return requestClient.post('/system/resource', data);
}

/**
 * 更新资源节点
 *
 * @param id 资源节点 ID
 * @param data 资源节点数据
 */
async function updateResource(
  id: string,
  data: Omit<SystemResourceApi.SystemResource, 'children' | 'id'>,
) {
  return requestClient.put(`/system/resource/${id}`, data);
}

/**
 * 删除资源节点
 * @param id 资源节点 ID
 */
async function deleteResource(id: string) {
  return requestClient.delete(`/system/resource/${id}`);
}

export {
  createResource,
  deleteResource,
  getResourceList,
  isResourceNameExists,
  isResourcePathExists,
  updateResource,
};
