<template>
  <!-- 头部 -->
  <a-card style="height: 10%;min-height: 80px;">
    <a-space>
      <!-- <a-button type="primary" :icon="h(PlusCircleOutlined)" @click="() => {
        isOpenEdit = true;
        addOrEditType = 1;
        addOrEditData = {};
      }">新增</a-button> -->
      <a-button type="" :icon="h(UndoOutlined)" @click="async () => {
        await loadData();
        message.success('刷新成功');
      }">刷新</a-button>
    </a-space>
  </a-card>
  <!-- 表格 -->
  <a-card style="min-height: 88%;margin-top: 10px;">
    <a-table :columns="columns" :data-source="tableData" @change="tableChange" :scroll="{ x: 1500 }"
      :pagination="pagination" :bordered="true">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'addip'">
          <div>IP：{{ record.addip }}</div>
          <div>时间：{{ record.lgtime }}</div>
        </template>
        <template v-if="column.key === 'action'">
          <span v-if="record.isAgent == 1">
            <a-popconfirm title="确认取消代理吗?" ok-text="确认" cancel-text="取消" @confirm="() => {
              setAgent(record.id);
            }">
              <a>取消代理</a>
            </a-popconfirm>
          </span>
          <span v-if="record.isAgent == 0">
            不是代理
          </span>
        </template>
      </template>
    </a-table>
  </a-card>
  <!-- 编辑弹框 -->
  <AddOrEdit v-if="isOpenEdit" ref="addOrEditRef" :isOpen="isOpenEdit" :formItems="formItems" :type="addOrEditType"
    :name="'币种'" :data="addOrEditData" @close="() => {
      isOpenEdit = false;
    }" @submit="addOrEditSubmit" />
</template>
<script setup lang="ts">
import { message } from 'ant-design-vue';
import { h, ref, onMounted } from 'vue';
import { PlusCircleOutlined, UndoOutlined } from '@ant-design/icons-vue';
import { agentApi } from '/@/api/business/admin/agent-api';
import AddOrEdit from "/@/components/edit/edit.vue"
const addOrEditRef = ref();
const isOpenEdit = ref(false);
const addOrEditType = ref(1);
const addOrEditData = ref({});
//分页数据
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10
});
const formItems=[];
//表格数据
const tableData = ref([] as any[]);
//行配置
const columns = [
  {
    name: 'id',
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    width: 100
  },
  {
    title: '代理账号',
    dataIndex: 'username',
    key: 'username',
    width: 180
  },
  {
    title: '注册ip/时间',
    dataIndex: 'addip',
    key: 'addip',
    width: 250
  },
  {
    title: '三代总人数（存疑）',
    dataIndex: 'addip1ALl',
    key: 'addip1ALl',
    width: 100
  },
  {
    title: '一代人数（存疑）',
    dataIndex: 'addip1',
    key: 'addip1',
    width: 100
  },
  {
    title: '二代人数（存疑）',
    dataIndex: 'addip2',
    key: 'addip2',
    width: 100
  },
  {
    title: '三代人数（存疑）',
    dataIndex: 'addip3',
    key: 'addip3',
    width: 100
  },
  {
    title: '邀请码',
    dataIndex: 'invit',
    key: 'invit',
    width: 150
  },
  {
    title: '操作',
    key: 'action',
    width: 300,
    fixed: 'right',
  },
];

//分页方法
async function tableChange(pag: { pageSize: number; current: number }) {
  pagination.value.pageSize = pag.pageSize;
  pagination.value.current = pag.current;
  loadData();

}
//设置取消代理
async function setAgent(id) {
  let data = await agentApi.setAgent({ id, isAgent: 0 });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await agentApi.addOrUpdate(submitData);
  if (data.ok) {
    message.success("操作成功！");
    addOrEditRef.value.close();
  } else {
    message.error("操作失败！请重试~");
  }
  loadData();
}
//获取表格数据
async function loadData() {
  let data = await agentApi.list({
    pageNum: pagination.value.current, pageSize: pagination.value.pageSize
  });
  if (data.ok) {
    data = data.data;
    tableData.value = data.records;
    pagination.value.total = data.total;
  }
}
onMounted(() => {
  loadData()
});
</script>
<style lang="less" scoped>
@import './index.less';
</style>
