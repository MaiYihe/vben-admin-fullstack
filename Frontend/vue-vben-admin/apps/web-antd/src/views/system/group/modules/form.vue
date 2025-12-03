<script lang="ts" setup>
import type { DataNode } from 'ant-design-vue/es/tree';

import type { SystemGroupApi } from '#/api/system/group';

import { computed, nextTick, ref } from 'vue';

import { MultiCheck, useVbenDrawer } from '@vben/common-ui';

import { useVbenForm } from '#/adapter/form';
import { createGroup, getGroupDetail, updateGroup } from '#/api/system/group';
import { getAllRoleList } from '#/api/system/role';
import { getAllUserList } from '#/api/system/user';
import { $t } from '#/locales';

import { useFormSchema } from '../data';

const emits = defineEmits(['success']);

const formData = ref<SystemGroupApi.SystemGroupDto>();

const [Form, formApi] = useVbenForm({
  schema: useFormSchema(),
  showDefaultActions: false,
});
// 角色节点
const roleNodes = ref<DataNode[]>([]);
const loadingRoleNodes = ref(false);
const roleKeyword = ref('');

// 用户节点
const userNodes = ref<DataNode[]>([]);
const loadingUserNodes = ref(false);
const userKeyword = ref('');

const id = ref();
// 抽屉
const [Drawer, drawerApi] = useVbenDrawer({
  // 点击确认按钮时触发
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;
    // values 就是表单当前输入的数据
    const values = await formApi.getValues();
    drawerApi.lock();
    // 调用更新或者增加的 Api
    (id.value ? updateGroup(id.value, values) : createGroup(values))
      .then(() => {
        emits('success');
        drawerApi.close();
      })
      .catch(() => {
        drawerApi.unlock();
      });
  },
  // 打开抽屉(表单)时做的初始化操作。无论新增还是修改，只要 Drawer 被“打开”，都会执行
  async onOpenChange(isOpen) {
    if (!isOpen) return;

    formApi.resetForm();
    // 用于加载所属角色的 checkBox：roleNodes
    if (roleNodes.value.length === 0) {
      await loadRoleNodes();
    }
    // 用于加载所含成员的 checkBox：userNodes
    if (userNodes.value.length === 0) {
      await loadUserNodes();
    }
    // 获取传入的数据（当前行数据）
    const data = drawerApi.getData<SystemGroupApi.SystemGroupDto>();

    if (data) {
      // 编辑情况，设置抽屉表单需要显示的数据
      const detail = await getGroupDetail(data.id);
      formData.value = detail; // 更新组件内部的响应式数据
      id.value = detail.id;

      // await nextTick() ：等 Vue 把 DOM/UI 更新完毕，再继续执行下面的代码
      await nextTick();
      formApi.setValues(detail); // 实际填入表单控件（value）
    } else {
      // 新增情况
      id.value = undefined;
    }
  },
});

async function loadRoleNodes() {
  loadingRoleNodes.value = true;
  try {
    const res = await getAllRoleList();
    roleNodes.value = res as unknown as DataNode[];
  } finally {
    loadingRoleNodes.value = false;
  }
}
async function loadUserNodes() {
  loadingUserNodes.value = true;
  try {
    const res = await getAllUserList();
    userNodes.value = res as unknown as DataNode[];
  } finally {
    loadingUserNodes.value = false;
  }
}
// computed(() => {...})：表示自动计算的值，会根据依赖自动更新
const getDrawerTitle = computed(() => {
  return formData.value?.id
    ? $t('common.edit', $t('system.group.name'))
    : $t('common.create', $t('system.group.name'));
});

const filteredRoleOptions = computed(() => {
  if (!roleKeyword.value) {
    // checkBox 的显示名称：r.name
    // value = 实际绑定到表单里的值
    return roleNodes.value.map((r) => ({ label: r.name, value: r.id }));
  }
  return roleNodes.value
    .filter((r) =>
      r.name.toLowerCase().includes(roleKeyword.value.toLowerCase()),
    )
    .map((r) => ({ label: r.name, value: r.id }));
});
const filteredUserOptions = computed(() => {
  if (!userKeyword.value) {
    // label = checkBox 的显示名称：r.username
    return userNodes.value.map((r) => ({ label: r.username, value: r.id }));
  }
  return userNodes.value
    .filter((r) =>
      r.name.toLowerCase().includes(userKeyword.value.toLowerCase()),
    )
    .map((r) => ({ label: r.name, value: r.id }));
});
</script>
<template>
  <Drawer :title="getDrawerTitle">
    <Form>
      <template #rolesIds="slotProps">
        <MultiCheck v-bind="slotProps" :options="filteredRoleOptions" />
      </template>
      <template #usersIds="slotProps">
        <MultiCheck v-bind="slotProps" :options="filteredUserOptions" />
      </template>
    </Form>
  </Drawer>
</template>
<style lang="css" scoped>
:deep(.ant-tree-title) {
  .tree-actions {
    display: none;
    margin-left: 20px;
  }
}

:deep(.ant-tree-title:hover) {
  .tree-actions {
    display: flex;
    flex: auto;
    justify-content: flex-end;
    margin-left: 20px;
  }
}
</style>
