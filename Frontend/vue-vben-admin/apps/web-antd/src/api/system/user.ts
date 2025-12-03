import type { Recordable } from '@vben/types';

import { requestClient } from '#/api/request';

export namespace SystemUserApi {
  /** 用户实体 */
  export interface SystemUser {
    [key: string]: any;
    /** 用户ID */
    id: string;
    /** 头像 */
    avatar?: string;
    /** 登录用户名 */
    username: string;
    /** 姓名 */
    realName: string;
    /** 所属角色编码数组 */
    roles?: string[];
    /** 所属组编码数组 */
    groups?: string[];
    /** 状态 1=启用 0=禁用 */
    status: 0 | 1;
    /** 创建时间 */
    createTime: string;
    /** 备注 */
    remark?: string;
  }
}

/**
 * 获取用户列表数据
 */
async function getUserList(params: Recordable<any>) {
  return requestClient.get<Array<SystemUserApi.SystemUser>>(
    '/system/user/list',
    { params },
  );
}
/** 获取用户组列表 */
async function getAllUserList() {
  return requestClient.get<Array<SystemUserApi.SystemUser>>('/system/user/all');
}

/**
 * 更新用户
 *
 * @param id 用户ID
 * @param data 用户数据
 */
async function updateUser(
  id: string,
  data: Omit<SystemUserApi.SystemUser, 'id'>,
) {
  return requestClient.put(`/system/user/${id}`, data);
}

/**
 * 创建用户
 * @param data 用户数据
 */
async function createUser(data: Omit<SystemUserApi.SystemUser, 'id'>) {
  return requestClient.post('/system/user', data);
}

/**
 * 删除用户
 * @param id 用户 ID
 */
async function deleteUser(id: string) {
  return requestClient.delete(`/system/user/${id}`);
}

export { createUser, deleteUser, getAllUserList, getUserList, updateUser };
