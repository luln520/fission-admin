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
      <a-input-search v-model:value="searchUserName" placeholder="请输入用户ID"  enter-button @search="async () => {
        pagination.current = 1;
        await loadData();
        message.success('查询成功');
      }" />
    </a-space>
  </a-card>
  <!-- 表格 -->
  <a-card style="min-height: 88%;margin-top: 10px;">
    <a-table :columns="columns" :data-source="tableData" @change="tableChange" :scroll="{ x: 1500 }"
      :pagination="pagination" :bordered="true">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'btc'">
          <div>可用:{{record.btc}}</div>
          <div>冻结:{{record.btcd}}</div>
          <div>总计:{{record.btc+record.btcd}}</div>
        </template>
        <template v-if="column.key === 'eth'">
          <div>可用:{{record.eth}}</div>
          <div>冻结:{{record.ethd}}</div>
          <div>总计:{{record.eth+record.ethd}}</div>
        </template>
        <template v-if="column.key === 'usdt'">
          <div>可用:{{record.usdt}}</div>
          <div>冻结:{{record.usdtd}}</div>
          <div>总计:{{record.usdt+record.usdtd}}</div>
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
import { userCoinApi } from '/@/api/business/admin/userCoin-api';
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
const searchUserName=ref("");
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
    title: '用户名',
    dataIndex: 'username',
    key: 'username',
    width:200
  },
  {
    title: 'BTC(BTC)',
    dataIndex: 'btc',
    key: 'btc',
    width: 150
  },
  {
    title: 'ERC20(USDT)',
    dataIndex: 'eth',
    key: 'eth',
    width: 100,
    ellipsis: true,
  },
  {
    title: 'TRC20(USDT)',
    dataIndex: 'usdt',
    key: 'usdt',
    width: 100,
    ellipsis: true,
  }
];

//分页方法
async function tableChange(pag: { pageSize: number; current: number }) {
  pagination.value.pageSize = pag.pageSize;
  pagination.value.current = pag.current;
  loadData();
}
//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await userCoinApi.addOrUpdate(submitData);
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
  let data = await userCoinApi.list({
    pageNum: pagination.value.current, pageSize: pagination.value.pageSize,username:searchUserName.value
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
