import type { VbenFormSchema } from '#/adapter/form';
import type { OnActionClickFn, VxeTableGridOptions } from '#/adapter/vxe-table';
import type { SystemGroupApi } from '#/api';

import { $t } from '#/locales';

// 用于 Form 提交表单
export function useFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Input',
      fieldName: 'name',
      label: $t('system.group.groupName'),
      rules: 'required',
    },
    {
      component: 'Input',
      fieldName: 'code',
      label: $t('system.group.code'),
      rules: 'required',
    },
    {
      component: 'RadioGroup',
      componentProps: {
        buttonStyle: 'solid',
        options: [
          { label: $t('common.enabled'), value: 1 },
          { label: $t('common.disabled'), value: 0 },
        ],
        optionType: 'button',
      },
      defaultValue: 1,
      fieldName: 'status',
      label: $t('system.group.status'),
    },
    {
      component: 'Textarea',
      fieldName: 'remark',
      label: $t('system.group.remark'),
    },
    {
      component: 'Input',
      fieldName: 'rolesIds',
      formItemClass: 'items-start',
      label: $t('system.group.ofRoles'),
      modelPropName: 'modelValue',
    },
    {
      component: 'Input',
      fieldName: 'usersIds',
      formItemClass: 'items-start',
      label: $t('system.group.ofUsers'),
      modelPropName: 'modelValue',
    },
  ];
}

// 用于 Grid + Form 联动场景（搜索过滤/筛选）
export function useGridFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Input',
      fieldName: 'name',
      label: $t('system.group.groupName'),
    },
    {
      component: 'Input',
      fieldName: 'code',
      label: $t('system.group.code'),
    },
    {
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: [
          { label: $t('common.enabled'), value: 1 },
          { label: $t('common.disabled'), value: 0 },
        ],
      },
      fieldName: 'status',
      label: $t('system.group.status'),
    },
    {
      component: 'Input',
      fieldName: 'roles',
      label: $t('system.group.roles'),
    },
    {
      component: 'Input',
      fieldName: 'remark',
      label: $t('system.user.remark'),
    },
    {
      component: 'RangePicker',
      fieldName: 'createTime',
      label: $t('system.user.createTime'),
    },
  ];
}

// 生成表格 columns 配置（列定义）的函数
export function useColumns<T = SystemGroupApi.SystemGroup>(
  onActionClick: OnActionClickFn<T>,
  onStatusChange?: (newStatus: any, row: T) => PromiseLike<boolean | undefined>,
): VxeTableGridOptions['columns'] {
  return [
    {
      field: 'name',
      title: $t('system.group.groupName'),
      width: 100,
    },
    {
      field: 'code',
      title: $t('system.group.code'),
      width: 100,
    },
    {
      field: 'userCount',
      width: 100,
      title: $t('system.group.userCount'),
    },
    {
      cellRender: {
        attrs: { beforeChange: onStatusChange },
        name: onStatusChange ? 'CellSwitch' : 'CellTag',
      },
      field: 'status',
      title: $t('system.group.status'),
      width: 100,
    },
    {
      field: 'roles',
      width: 200,
      title: $t('system.group.roles'),
    },
    {
      field: 'remark',
      minWidth: 100,
      title: $t('system.group.remark'),
    },
    {
      field: 'createTime',
      title: $t('system.group.createTime'),
      width: 200,
    },
    {
      align: 'center',
      cellRender: {
        attrs: {
          nameField: 'name',
          nameTitle: $t('system.group.name'),
          onClick: onActionClick,
        },
        name: 'CellOperation',
      },
      field: 'operation',
      fixed: 'right',
      title: $t('system.group.operation'),
      width: 130,
    },
  ];
}
