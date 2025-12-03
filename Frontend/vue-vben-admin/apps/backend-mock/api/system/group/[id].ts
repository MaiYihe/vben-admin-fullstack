import { eventHandler } from 'h3';
import { verifyAccessToken } from '~/utils/jwt-utils';
import { mockGroupData } from '~/utils/mock-data';
import { unAuthorizedResponse, useResponseSuccess } from '~/utils/response';

export default eventHandler(async (event) => {
  const userinfo = verifyAccessToken(event);
  if (!userinfo) {
    return unAuthorizedResponse(event);
  }

  const id = event.context.params?.id;

  if (!id) {
    return useResponseSuccess(null);
  }

  const group = mockGroupData.find((item) => String(item.id) === String(id));

  if (!group) {
    return useResponseSuccess(null);
  }

  const dto = {
    ...group,
    rolesIds: group.roles ?? [],
    usersIds: group.users?.map((u) => u.id) ?? [],
  };

  return useResponseSuccess(dto);
});
