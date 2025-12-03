import type { OnActionClickFn, VxeTableGridOptions } from '#/adapter/vxe-table';
import type { SystemResourceApi } from '#/api/system/resource';

import { $t } from '#/locales';

export function getResourceTypeOptions() {
  return [
    {
      color: 'processing',
      label: $t('system.resource.typeCatalog'),
      value: 'catalog',
    },
    {
      color: 'default',
      label: $t('system.resource.typePage'),
      value: 'page',
    },
    {
      color: 'error',
      label: $t('system.resource.typeButton'),
      value: 'button',
    },
    {
      color: 'success',
      label: $t('system.resource.typeEmbedded'),
      value: 'embedded',
    },
    {
      color: 'warning',
      label: $t('system.resource.typeLink'),
      value: 'link',
    },
  ];
}

export function useColumns(
  onActionClick: OnActionClickFn<SystemResourceApi.SystemResource>,
): VxeTableGridOptions<SystemResourceApi.SystemResource>['columns'] {
  return [
    {
      align: 'left',
      field: 'meta.title',
      fixed: 'left',
      slots: { default: 'title' },
      title: $t('system.resource.resourceTitle'),
      treeNode: true,
      width: 250,
    },
    {
      align: 'center',
      cellRender: { name: 'CellTag', options: getResourceTypeOptions() },
      field: 'type',
      title: $t('system.resource.type'),
      width: 100,
    },
    {
      field: 'authCode',
      title: $t('system.resource.authCode'),
      width: 200,
    },
    {
      align: 'left',
      field: 'path',
      title: $t('system.resource.path'),
      width: 200,
    },

    {
      align: 'left',
      field: 'component',
      formatter: ({ row }) => {
        switch (row.type) {
          case 'catalog':
          case 'page': {
            return row.component ?? '';
          }
          case 'embedded': {
            return row.meta?.iframeSrc ?? '';
          }
          case 'link': {
            return row.meta?.link ?? '';
          }
        }
        return '';
      },
      minWidth: 200,
      title: $t('system.resource.component'),
    },
    {
      cellRender: { name: 'CellTag' },
      field: 'status',
      title: $t('system.resource.status'),
      width: 100,
    },

    {
      align: 'right',
      cellRender: {
        attrs: {
          nameField: 'name',
          onClick: onActionClick,
        },
        name: 'CellOperation',
        options: [
          {
            code: 'append',
            text: '新增下级',
          },
          'edit', // 默认的编辑按钮
          'delete', // 默认的删除按钮
        ],
      },
      field: 'operation',
      fixed: 'right',
      headerAlign: 'center',
      showOverflow: false,
      title: $t('system.resource.operation'),
      width: 200,
    },
  ];
}
