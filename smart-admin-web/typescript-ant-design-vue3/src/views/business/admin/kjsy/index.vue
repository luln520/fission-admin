<template>
  <!-- 头部 -->
  <a-card style="height: 10%;min-height: 80px;">
    <a-space>
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
        <template v-if="column.key === 'hyzd'">
          <div v-if="record.hyzd == 1" style="color: green;">买涨</div>
          <div v-if="record.hyzd != 1" style="color: red;">买跌</div>
        </template>

        <template v-if="column.key === 'status'">
          <div v-if="record.status == 1" style="color: red;">待结算</div>
          <div v-if="record.status == 2" style="color: green;">已结算</div>
          <div v-if="record.status == 3" style="">无效结算</div>
        </template>

        <template v-if="column.key === 'ploss'">
          <div v-if="record.isWin == 1" style="color: green;">+{{ record.ploss }}</div>
          <div v-if="record.isWin == 2" style="color: red;">-{{ record.ploss }}</div>
        </template>
      </template>
    </a-table>
  </a-card>
</template>
<script setup lang="ts">
import { message } from 'ant-design-vue';
import { h, ref, onMounted } from 'vue';
import { PlusCircleOutlined, UndoOutlined } from '@ant-design/icons-vue';
import { kuangmApi } from '/@/api/business/admin/kuangm-api';
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
    title: '矿机名称',
    dataIndex: 'ktitle',
    key: 'ktitle',
    width: 200
  },
  {
    title: '收益额度',
    dataIndex: 'num',
    key: 'num',
    width: 200
  },
  {
    title: '收益币种',
    dataIndex: 'coin',
    key: 'coin',
    width: 130
  },
  {
    title: '收益时间',
    dataIndex: 'addtime',
    key: 'addtime',
    width: 100
  }
];

//分页方法
async function tableChange(pag: { pageSize: number; current: number }) {
  pagination.value.pageSize = pag.pageSize;
  pagination.value.current = pag.current;
  loadData();

}
//获取表格数据
async function loadData() {
  let data = await kuangmApi.kjsylist({
    pageNum: pagination.value.current, pageSize: pagination.value.pageSize, userCode: searchUserName.value
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
