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

        <template v-if="column.key === 'state'">
          <span v-if="record.state == 0" style="color: red;">未回复</span>
          <span v-if="record.state == 1" style="color: green;">已回复</span>
        </template>


        <template v-if="column.key === 'action'">
          <span>
            <a @click="() => {
              showInfo(record);
            }">查看详情</a>
          </span>
        </template>
      </template>
    </a-table>
  </a-card>
  <!-- 聊天框 -->
  <Msg v-if="isOpenMsg" :isOpen="isOpenMsg" @close="() => {
    isOpenMsg = false;
  }" :username="username" :id="userId" />
</template>
<script setup lang="ts">
import { message } from 'ant-design-vue';
import { h, ref, onMounted } from 'vue';
import { UndoOutlined } from '@ant-design/icons-vue';
import { onlineApi } from '/@/api/business/admin/online-api';
import Msg from "./components/back/index.vue"
const isOpenMsg = ref(false);
const username = ref("");
const userId = ref(0);
//分页数据
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10
});
//表格数据
const tableData = ref([] as any[]);
//行配置
const columns = [
  {
    name: 'id',
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    width: 50
  },
  {
    title: '会员号',
    dataIndex: 'username',
    key: 'username',
    width: 200
  },
  {
    title: '回复状态',
    dataIndex: 'state',
    key: 'state',
    width: 100
  },
  {
    title: '时间',
    dataIndex: 'addtime',
    key: 'addtime',
    width: 100
  },
  {
    title: '操作',
    key: 'action',
    width: 100,
    fixed: 'right',
  },
];

//分页方法
async function tableChange(pag: { pageSize: number; current: number }) {
  pagination.value.pageSize = pag.pageSize;
  pagination.value.current = pag.current;
  loadData();

}
//启用禁用
async function showInfo(userInfo) {
  userId.value = userInfo.uid;
  username.value = userInfo.username;
  isOpenMsg.value = true;
}
//获取表格数据
async function loadData() {
  let data = await onlineApi.list({
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
