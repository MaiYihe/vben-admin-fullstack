import type { RouteRecordRaw } from 'vue-router';

import { $t } from '#/locales';

const routes: RouteRecordRaw[] = [
  {
    meta: {
      icon: 'ion:settings-outline',
      order: 9997,
      title: $t('system.title'),
    },
    name: 'System',
    path: '/system',
    children: [
      {
        path: '/system/resource',
        name: 'SystemResource',
        meta: {
          icon: 'mdi:menu',
          title: $t('system.resource.title'),
        },
        component: () => import('#/views/system/rez/list.vue'),
      },
      {
        path: '/system/role',
        name: 'SystemRole',
        meta: {
          icon: 'mdi:account-group',
          title: $t('system.role.title'),
        },
        component: () => import('#/views/system/role/list.vue'),
      },
      {
        path: '/system/group',
        name: 'SystemGroup',
        meta: {
          icon: 'carbon:group-objects',
          title: $t('system.group.title'),
        },
        component: () => import('#/views/system/group/list.vue'),
      },
      {
        path: '/system/user',
        name: 'SystemUser',
        meta: {
          icon: 'carbon:user-filled',
          title: $t('system.user.title'),
        },
        component: () => import('#/views/system/user/list.vue'),
      },
      {
        path: '/system/dept',
        name: 'SystemDept',
        meta: {
          icon: 'charm:organisation',
          title: $t('system.dept.title'),
        },
        component: () => import('#/views/system/dept/list.vue'),
      },
    ],
  },
];

export default routes;
