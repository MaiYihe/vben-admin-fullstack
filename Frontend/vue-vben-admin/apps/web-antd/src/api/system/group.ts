import type { Recordable } from '@vben/types';

import { requestClient } from '#/api/request';

export namespace SystemGroupApi {
  /** 用户组显示实体，展示模型 */
  export interface SystemGroup {
    [key: string]: any;
    /** ID */
    id: string;
    /** 组显示名 */
    name: string;
    /** 系统编码（稳定 & 唯一、不允许随便修改） */
    code: string;
    /** 所属角色编码数组 */
    roles?: string[];
    /** 用户数量（可选，后端统计） */
    userCount?: number;
    /** 状态 1=启用 0=禁用 */
    status: 0 | 1;
    /** 描述备注 */
    remark?: string;
    /** 创建时间 */
    createTime: string;
  }
  /** 用户组创建实体、修改实体 */
  export interface SystemGroupDto {
    [key: string]: any;
    /** ID */
    id: string;
    /** 组显示名 */
    name: string;
    /** 系统编码（稳定 & 唯一、不允许随便修改） */
    code: string;
    /** 状态 1=启用 0=禁用 */
    status: 0 | 1;
    /** 描述备注 */
    remark?: string;

    /** 所属角色ID数组 */
    rolesIds?: string[];
    /** 用户成员ID数组 */
    usersIds?: string[];
  }
}

/** （带条件的）获取用户组列表-用于表格显示 */
async function getGroupList(params: Recordable<any>) {
  return requestClient.get<Array<SystemGroupApi.SystemGroup>>(
    '/system/group/list',
    { params },
  );
}

/** 获取用户组列表-用于表单显示 */
async function getGroupDetail(id: string) {
  return requestClient.get<SystemGroupApi.SystemGroupDto>(
    `/system/group/${id}`,
  );
}

/**
 * 创建用户组
 * @param data 用户组数据
 */
async function createGroup(data: Omit<SystemGroupApi.SystemGroupDto, 'id'>) {
  return requestClient.post('/system/group', data);
}

/**
 * 更新用户组
 *
 * @param id 用户组 ID
 * @param data 用户组数据
 */
async function updateGroup(
  id: string,
  data: Omit<SystemGroupApi.SystemGroupDto, 'id'>,
) {
  return requestClient.put(`/system/group/${id}`, data);
}

/**
 * 删除用户组
 * @param id 用户组 ID
 */
async function deleteGroup(id: string) {
  return requestClient.delete(`/system/group/${id}`);
}

export { createGroup, deleteGroup, getGroupDetail, getGroupList, updateGroup };
