<template>
  <!-- 头部 -->
  <a-card style="height: 10%;min-height: 80px;">
    <a-space>
      <a-button type="" :icon="h(UndoOutlined)" @click="async () => {
        await loadData();
        message.success('刷新成功');
      }">刷新</a-button>
      <a-input-search v-model:value="searchUserName" placeholder="请输入用户名" enter-button @search="async () => {
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
        <template v-if="column.key === 'hyzd'">
          <div v-if="record.hyzd == 1" style="color: green;">买涨</div>
          <div v-if="record.hyzd != 1" style="color: red;">买跌</div>
          时间：{{ record.intselltime }}
        </template>

        <template v-if="column.key === 'status'">
          <div v-if="record.status == 1" style="color: red;">待结算</div>
          <div v-if="record.status == 2" style="color: green;">已结算</div>
          <div v-if="record.status == 3" style="">无效结算</div>
        </template>
        <template v-if="column.key === 'kongyk'">
          <div v-if="record.kongyk == 1" style="color: green;">盈利</div>
          <div v-if="record.kongyk == 2" style="color: red;">亏损</div>
          <div v-if="record.kongyk == 0" style=";">未指定</div>
        </template>

        <template v-if="column.key === 'ploss'">
          <div v-if="record.isWin == 1" style="color: green;">+{{ record.ploss }}</div>
          <div v-if="record.isWin == 2" style="color: red;">-{{ record.ploss }}</div>
        </template>

        <template v-if="column.key === 'action'">
          <span>
            <!-- <a @click="() => {
              isOpenEdit = true;
              addOrEditType = 2;
              addOrEditData = record;
            }">编辑</a>
            <a-divider type="vertical" /> -->
            <a @click="() => {
              updateStatus(record.id, 2);
            }">设置亏</a>
            <a-divider type="vertical" />
            <a @click="() => {
              updateStatus(record.id, 1);
            }">设置盈</a>
          </span>
        </template>
      </template>
    </a-table>
  </a-card>
  <!-- 编辑弹框 -->
  <AddOrEdit v-if="isOpenEdit" ref="addOrEditRef" :isOpen="isOpenEdit" :formItems="formItems" :type="addOrEditType"
    :name="'合约订单'" :data="addOrEditData" @close="() => {
      isOpenEdit = false;
    }" @submit="addOrEditSubmit" />
</template>
<script setup lang="ts">
import { message } from 'ant-design-vue';
import { h, ref, onMounted } from 'vue';
import { PlusCircleOutlined, UndoOutlined } from '@ant-design/icons-vue';
import { tradeApi } from '/@/api/business/admin/trade-api';
import AddOrEdit from "/@/components/edit/edit.vue"
const addOrEditRef = ref();
const isOpenEdit = ref(false);
const addOrEditType = ref(1);
const addOrEditData = ref({});
const formItems = [];

//分页数据
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10
});
const searchUserName = ref("");
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
    title: '会员账号',
    dataIndex: 'username',
    key: 'username',
    width: 250
  },
  {
    title: '交易对',
    dataIndex: 'coinname',
    key: 'coinname',
    width: 100
  },
  {
    title: '方向/合约时间',
    dataIndex: 'hyzd',
    key: 'hyzd',
    width: 200
  },
  {
    title: '状态',
    dataIndex: 'status',
    key: 'status',
    width: 130
  },
  {
    title: '亏盈设置',
    dataIndex: 'kongyk',
    key: 'kongyk',
    width: 130
  },
  {
    title: '委托额度',
    dataIndex: 'num',
    key: 'num',
    width: 100
  },
  {
    title: '建仓单价',
    dataIndex: 'buyprice',
    key: 'buyprice',
    width: 150
  },
  {
    title: '平仓单价',
    dataIndex: 'sellprice',
    key: 'sellprice',
    width: 150
  },
  {
    title: '建仓时间',
    dataIndex: 'buytime',
    key: 'buytime',
    width: 200
  },
  {
    title: '亏盈额度',
    dataIndex: 'ploss',
    key: 'ploss',
    width: 150
  },
  {
    title: '买后余额',
    dataIndex: 'buyOrblance',
    key: 'buyOrblance',
    width: 150
  },
  {
    title: '操作',
    key: 'action',
    width: 200,
    fixed: 'right',
  },
];

//分页方法
async function tableChange(pag: { pageSize: number; current: number }) {
  pagination.value.pageSize = pag.pageSize;
  pagination.value.current = pag.current;
  loadData();

}
//设置亏盈
async function updateStatus(id, kongyk) {
  let data = await tradeApi.editKongyK({ id, kongyk });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await tradeApi.addOrUpdate(submitData);
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
  let data = await tradeApi.hyorderlist({
    pageNum: pagination.value.current, pageSize: pagination.value.pageSize, username: searchUserName.value
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
