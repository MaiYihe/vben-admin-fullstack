<script setup lang="ts">
import type { CheckboxValueType } from 'ant-design-vue/lib/checkbox/interface';

import { computed, ref } from 'vue';

import { cn } from '@vben-core/shared/utils';

import { Button, Checkbox, Dropdown, Input } from 'ant-design-vue';

const props = withDefaults(
  defineProps<{
    bordered?: boolean;
    modelValue?: CheckboxValueType[];
    options?: { label: string; value: CheckboxValueType }[];
  }>(),
  {
    modelValue: () => [],
    options: () => [],
    bordered: true,
  },
);

const emits = defineEmits(['update:modelValue']);

const filterMode = ref<'all' | 'checked'>('all');

const keyword = ref('');

const filteredOptions = computed(() => {
  let opts = props.options ?? [];

  if (keyword.value) {
    opts = opts.filter((o) => String(o.label).includes(keyword.value));
  }

  if (filterMode.value === 'checked') {
    opts = opts.filter((o) => props.modelValue.includes(o.value));
  }

  return opts;
});

function updateValue(v: CheckboxValueType[]) {
  emits('update:modelValue', v);
}
</script>

<template>
  <div
    :class="
      cn('container flex flex-col gap-2 p-2', props.bordered ? 'border' : '')
    "
  >
    <!-- 固定区域 -->
    <div class="mb-1 flex shrink-0 items-center gap-2">
      <Input
        v-model:value="keyword"
        placeholder="搜索..."
        allow-clear
        class="flex-1"
      />

      <Dropdown trigger="click">
        <Button type="default">筛选</Button>

        <template #overlay>
          <div class="ant-dropdown-menu w-[120px]">
            <div
              class="ant-dropdown-menu-item flex justify-between"
              @click="filterMode = 'all'"
            >
              显示全部
              <span v-if="filterMode === 'all'">✔</span>
            </div>

            <div
              class="ant-dropdown-menu-item flex justify-between"
              @click="filterMode = 'checked'"
            >
              仅已勾选
              <span v-if="filterMode === 'checked'">✔</span>
            </div>
          </div>
        </template>
      </Dropdown>
    </div>

    <!-- 滚动区域 -->
    <div class="overflow-auto" style="max-height: 260px">
      <Checkbox.Group
        :value="props.modelValue"
        :options="filteredOptions"
        @change="updateValue"
      />
    </div>
  </div>
</template>
