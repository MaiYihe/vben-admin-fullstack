<script lang="ts" setup>
import type { ChangeEvent } from 'ant-design-vue/es/_util/EventInterface';

import type { Recordable } from '@vben/types';

import type { VbenFormSchema } from '#/adapter/form';

import { computed, h, ref } from 'vue';

import { useVbenDrawer } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';
import { $te } from '@vben/locales';
import { getPopupContainer } from '@vben/utils';

import { breakpointsTailwind, useBreakpoints } from '@vueuse/core';

import { useVbenForm, z } from '#/adapter/form';
import {
  createResource,
  getResourceList,
  isResourceNameExists,
  isResourcePathExists,
  SystemResourceApi,
  updateResource,
} from '#/api/system/resource';
import { $t } from '#/locales';
import { componentKeys } from '#/router/routes';

import { getResourceTypeOptions } from '../data';

const emit = defineEmits<{
  success: [];
}>();
const formData = ref<SystemResourceApi.SystemResource>();
const titleSuffix = ref<string>();

const schema: VbenFormSchema[] = [
  {
    component: 'RadioGroup',
    componentProps: {
      buttonStyle: 'solid',
      options: getResourceTypeOptions(),
      optionType: 'button',
    },
    defaultValue: 'page',
    fieldName: 'type',
    formItemClass: 'col-span-2 md:col-span-2',
    label: $t('system.resource.type'),
  },
  // 资源节点名称
  {
    component: 'Input',
    fieldName: 'name',
    label: $t('system.resource.resourceName'),
    rules: z
      .string()
      .min(
        2,
        $t('ui.formRules.minLength', [$t('system.resource.resourceName'), 2]),
      )
      .max(
        30,
        $t('ui.formRules.maxLength', [$t('system.resource.resourceName'), 30]),
      )
      .refine(
        async (value: string) => {
          return !(await isResourceNameExists(value, formData.value?.id));
        },
        (value) => ({
          message: $t('ui.formRules.alreadyExists', [
            $t('system.resource.resourceName'),
            value,
          ]),
        }),
      ),
  },
  // 上级菜单
  {
    component: 'ApiTreeSelect',
    componentProps: {
      api: getResourceList,
      class: 'w-full',
      filterTreeNode(input: string, node: Recordable<any>) {
        if (!input || input.length === 0) {
          return true;
        }
        const title: string = node.meta?.title ?? '';
        if (!title) return false;
        return title.includes(input) || $t(title).includes(input);
      },
      getPopupContainer,
      labelField: 'meta.title',
      showSearch: true,
      treeDefaultExpandAll: true,
      valueField: 'id',
      childrenField: 'children',
    },
    fieldName: 'pid',
    label: $t('system.resource.parent'),
    renderComponentContent() {
      return {
        title({ label, meta }: { label: string; meta: Recordable<any> }) {
          const coms = [];
          if (!label) return '';
          if (meta?.icon) {
            coms.push(h(IconifyIcon, { class: 'size-4', icon: meta.icon }));
          }
          coms.push(h('span', { class: '' }, $t(label || '')));
          return h('div', { class: 'flex items-center gap-1' }, coms);
        },
      };
    },
  },
  // 标题
  {
    component: 'Input',
    componentProps() {
      // 不需要处理多语言时就无需这么做
      return {
        addonAfter: titleSuffix.value,
        onChange({ target: { value } }: ChangeEvent) {
          titleSuffix.value = value && $te(value) ? $t(value) : undefined;
        },
      };
    },
    fieldName: 'meta.title',
    label: $t('system.resource.resourceTitle'),
    rules: 'required',
  },
  {
    component: 'Input',
    // 动态显示/隐藏的核心
    dependencies: {
      show: (values) => {
        // 当 type 字段发生变化时，重新计算 show()。参数 values 是整个表单当前值
        // values = 当前整个表单的值（form model）。values.type 是表单中的字段
        return ['catalog', 'embedded', 'page'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'path',
    label: $t('system.resource.path'),
    rules: z
      .string()
      .min(2, $t('ui.formRules.minLength', [$t('system.resource.path'), 2]))
      .max(100, $t('ui.formRules.maxLength', [$t('system.resource.path'), 100]))
      .refine(
        (value: string) => {
          return value.startsWith('/');
        },
        $t('ui.formRules.startWith', [$t('system.resource.path'), '/']),
      )
      .refine(
        async (value: string) => {
          return !(await isResourcePathExists(value, formData.value?.id));
        },
        (value) => ({
          message: $t('ui.formRules.alreadyExists', [
            $t('system.resource.path'),
            value,
          ]),
        }),
      ),
  },
  {
    component: 'Input',
    dependencies: {
      show: (values) => {
        return ['embedded', 'page'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'activePath',
    help: $t('system.resource.activePathHelp'),
    label: $t('system.resource.activePath'),
    rules: z
      .string()
      .min(2, $t('ui.formRules.minLength', [$t('system.resource.path'), 2]))
      .max(100, $t('ui.formRules.maxLength', [$t('system.resource.path'), 100]))
      .refine(
        (value: string) => {
          return value.startsWith('/');
        },
        $t('ui.formRules.startWith', [$t('system.resource.path'), '/']),
      )
      .refine(async (value: string) => {
        return await isResourcePathExists(value, formData.value?.id);
      }, $t('system.resource.activePathMustExist'))
      .optional(),
  },
  {
    component: 'IconPicker',
    componentProps: {
      prefix: 'carbon',
    },
    dependencies: {
      show: (values) => {
        return ['catalog', 'embedded', 'link', 'page'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'meta.icon',
    label: $t('system.resource.icon'),
  },
  {
    component: 'IconPicker',
    componentProps: {
      prefix: 'carbon',
    },
    dependencies: {
      show: (values) => {
        return ['catalog', 'embedded', 'page'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'meta.activeIcon',
    label: $t('system.resource.activeIcon'),
  },
  {
    component: 'AutoComplete',
    componentProps: {
      allowClear: true,
      class: 'w-full',
      filterOption(input: string, option: { value: string }) {
        return option.value.toLowerCase().includes(input.toLowerCase());
      },
      options: componentKeys.map((v) => ({ value: v })),
    },
    dependencies: {
      rules: (values) => {
        return values.type === 'page' ? 'required' : null;
      },
      show: (values) => {
        return values.type === 'page';
      },
      triggerFields: ['type'],
    },
    fieldName: 'component',
    label: $t('system.resource.component'),
  },
  {
    component: 'Input',
    dependencies: {
      show: (values) => {
        return ['embedded', 'link'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'linkSrc',
    label: $t('system.resource.linkSrc'),
    rules: z.string().url($t('ui.formRules.invalidURL')),
  },
  {
    component: 'Input',
    dependencies: {
      rules: (values) => {
        return values.type === 'button' ? 'required' : null;
      },
      show: (values) => {
        return ['button', 'catalog', 'embedded', 'page'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'authCode',
    label: $t('system.resource.authCode'),
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
    label: $t('system.resource.status'),
  },
  {
    component: 'Select',
    componentProps: {
      allowClear: true,
      class: 'w-full',
      options: [
        { label: $t('system.resource.badgeType.dot'), value: 'dot' },
        { label: $t('system.resource.badgeType.normal'), value: 'normal' },
      ],
    },
    dependencies: {
      show: (values) => {
        return values.type !== 'button';
      },
      triggerFields: ['type'],
    },
    fieldName: 'meta.badgeType',
    label: $t('system.resource.badgeType.title'),
  },
  {
    component: 'Input',
    componentProps: (values) => {
      return {
        allowClear: true,
        class: 'w-full',
        disabled: values.meta?.badgeType !== 'normal',
      };
    },
    dependencies: {
      show: (values) => {
        return values.type !== 'button';
      },
      triggerFields: ['type'],
    },
    fieldName: 'meta.badge',
    label: $t('system.resource.badge'),
  },
  {
    component: 'Select',
    componentProps: {
      allowClear: true,
      class: 'w-full',
      options: SystemResourceApi.BadgeVariants.map((v) => ({
        label: v,
        value: v,
      })),
    },
    dependencies: {
      show: (values) => {
        return values.type !== 'button';
      },
      triggerFields: ['type'],
    },
    fieldName: 'meta.badgeVariants',
    label: $t('system.resource.badgeVariants'),
  },
  {
    component: 'Input',
    dependencies: {
      rules: (values) => {
        return ['catalog', 'embedded', 'link', 'page'].includes(values.type)
          ? 'required'
          : null;
      },
      show: (values) => {
        return values.type !== 'button';
      },
      triggerFields: ['type'],
    },
    fieldName: 'meta.order',
    label: $t('system.resource.order'),
  },
  // 其他设置
  {
    component: 'Divider',
    dependencies: {
      show: (values) => {
        return !['button', 'link'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'divider1',
    formItemClass: 'col-span-2 md:col-span-2 pb-0',
    hideLabel: true,
    renderComponentContent() {
      return {
        default: () => $t('system.resource.advancedSettings'),
      };
    },
  },
  {
    component: 'Checkbox',
    dependencies: {
      show: (values) => {
        return ['page'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'meta.keepAlive',
    renderComponentContent() {
      return {
        default: () => $t('system.resource.keepAlive'),
      };
    },
  },
  {
    component: 'Checkbox',
    dependencies: {
      show: (values) => {
        return ['embedded', 'page'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'meta.affixTab',
    renderComponentContent() {
      return {
        default: () => $t('system.resource.affixTab'),
      };
    },
  },
  {
    component: 'Checkbox',
    dependencies: {
      show: (values) => {
        return !['button'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'meta.hideResource',
    renderComponentContent() {
      return {
        default: () => $t('system.resource.hideResource'),
      };
    },
  },
  {
    component: 'Checkbox',
    dependencies: {
      show: (values) => {
        return ['catalog', 'page'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'meta.hideChildrenPage',
    renderComponentContent() {
      return {
        default: () => $t('system.resource.hideChildrenPage'),
      };
    },
  },
  {
    component: 'Checkbox',
    dependencies: {
      show: (values) => {
        return !['button', 'link'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'meta.hideInBreadcrumb',
    renderComponentContent() {
      return {
        default: () => $t('system.resource.hideInBreadcrumb'),
      };
    },
  },
  {
    component: 'Checkbox',
    dependencies: {
      show: (values) => {
        return !['button', 'link'].includes(values.type);
      },
      triggerFields: ['type'],
    },
    fieldName: 'meta.hideInTab',
    renderComponentContent() {
      return {
        default: () => $t('system.resource.hideInTab'),
      };
    },
  },
];

const breakpoints = useBreakpoints(breakpointsTailwind);
const isHorizontal = computed(() => breakpoints.greaterOrEqual('md').value);

const [Form, formApi] = useVbenForm({
  commonConfig: {
    colon: true,
    formItemClass: 'col-span-2 md:col-span-1',
  },
  schema,
  showDefaultActions: false,
  wrapperClass: 'grid-cols-2 gap-x-4',
});

const [Drawer, drawerApi] = useVbenDrawer({
  onConfirm: onSubmit,
  onOpenChange(isOpen) {
    if (isOpen) {
      const data = drawerApi.getData<SystemResourceApi.SystemResource>();
      if (data?.type === 'link') {
        data.linkSrc = data.meta?.link;
      } else if (data?.type === 'embedded') {
        data.linkSrc = data.meta?.iframeSrc;
      }
      if (data) {
        formData.value = data;
        formApi.setValues(formData.value);
        titleSuffix.value = formData.value.meta?.title
          ? $t(formData.value.meta.title)
          : '';
      } else {
        formApi.resetForm();
        titleSuffix.value = '';
      }
    }
  },
});

async function onSubmit() {
  const { valid } = await formApi.validate();
  if (valid) {
    drawerApi.lock();
    const data =
      await formApi.getValues<
        Omit<SystemResourceApi.SystemResource, 'children' | 'id'>
      >();
    if (data.type === 'link') {
      data.meta = { ...data.meta, link: data.linkSrc };
    } else if (data.type === 'embedded') {
      data.meta = { ...data.meta, iframeSrc: data.linkSrc };
    }
    delete data.linkSrc;
    try {
      await (formData.value?.id
        ? updateResource(formData.value.id, data)
        : createResource(data));
      drawerApi.close();
      emit('success');
    } finally {
      drawerApi.unlock();
    }
  }
}
const getDrawerTitle = computed(() =>
  formData.value?.id
    ? $t('ui.actionTitle.edit', [$t('system.resource.name')])
    : $t('ui.actionTitle.create', [$t('system.resource.name')]),
);
</script>
<template>
  <Drawer class="w-full max-w-[800px]" :title="getDrawerTitle">
    <Form class="mx-4" :layout="isHorizontal ? 'horizontal' : 'vertical'" />
  </Drawer>
</template>
