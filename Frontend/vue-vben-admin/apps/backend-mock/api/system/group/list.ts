import { eventHandler, getQuery } from 'h3';
import { verifyAccessToken } from '~/utils/jwt-utils';
import { mockGroupData } from '~/utils/mock-data';
import { unAuthorizedResponse, usePageResponseSuccess } from '~/utils/response';

// 路由处理器。最后 return 的数据 = 真正返回给前端的响应
export default eventHandler(async (event) => {
  const userinfo = verifyAccessToken(event);
  if (!userinfo) {
    return unAuthorizedResponse(event);
  }
  // page 和 pageSize 提供默认值，提高后端健壮性
  // 根据 params(表单数据+page数据) 进行条件查询
  const {
    page = 1,
    pageSize = 20,
    name,
    code,
    status,
    roles,
    remark,
    startTime,
    endTime,
  } = getQuery(event);

  let listData = structuredClone(mockGroupData);
  if (name) {
    listData = listData.filter((item) =>
      item.name.toLowerCase().includes(String(name).toLowerCase()),
    );
  }
  if (code) {
    listData = listData.filter(
      (item) => item.code.toLowerCase() === String(code).toLowerCase(),
    );
  }
  // 根据 roles 筛选
  if (roles) {
    const rolesArr = Array.isArray(roles) ? roles : [roles];
    listData = listData.filter((item) =>
      item.roles?.some((r: string) => rolesArr.includes(r)),
    );
  }

  if (remark) {
    listData = listData.filter((item) =>
      item.remark?.toLowerCase()?.includes(String(remark).toLowerCase()),
    );
  }

  if (startTime) {
    listData = listData.filter((item) => item.createTime >= startTime);
  }

  if (endTime) {
    listData = listData.filter((item) => item.createTime <= endTime);
  }

  if (['0', '1'].includes(status as string)) {
    listData = listData.filter((item) => item.status === Number(status));
  }
  return usePageResponseSuccess(page as string, pageSize as string, listData);
});
