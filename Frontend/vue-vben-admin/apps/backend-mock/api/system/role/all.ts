import { eventHandler } from 'h3';
import { verifyAccessToken } from '~/utils/jwt-utils';
import { mockRoleData } from '~/utils/mock-data';
import { unAuthorizedResponse, useResponseSuccess } from '~/utils/response';

export default eventHandler(async (event) => {
  // 校验 token
  const userinfo = verifyAccessToken(event);
  if (!userinfo) {
    return unAuthorizedResponse(event);
  }

  // 克隆保证安全
  const listData = structuredClone(mockRoleData);

  // 无分页、无过滤
  return useResponseSuccess(listData);
});
