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
          <div v-if="record.hyzd == 1" style="color: green;">涨</div>
          <div v-if="record.hyzd != 1" style="color: red;">跌</div>
        </template>

        <template v-if="column.key === 'status'">
          <div v-if="record.status == 1" style="color: red;">待结算</div>
          <div v-if="record.status == 2" style="color: green;">
            <div v-if="record.isWin == 1" style="color: green;">赢</div>
            <div v-if="record.isWin == 2" style="color: red;">输</div>
          </div>
          <div v-if="record.status == 3" style="">无效结算</div>
        </template>
        <template v-if="column.key === 'kongyk'">
          <div v-if="record.kongyk == 1" style="color: green;">盈利</div>
          <div v-if="record.kongyk == 2" style="color: red;">亏损</div>
          <div v-if="record.kongyk == 0" style=";">正常</div>
        </template>
        <template v-if="column.key === 'win'">
          {{ record?.win }}/{{ record?.loss }}
        </template>
        <template v-if="column.key === 'isWin'">
          <div v-if="record.isWin == 1" style="color: green;">赢</div>
          <div v-if="record.isWin == 2" style="color: red;">输</div>
        </template>

        <template v-if="column.key === 'ploss'">
          <div v-if="record.isWin == 1" style="color: green;">+{{ record.ploss }}</div>
          <div v-if="record.isWin == 2" style="color: red;">-{{ record.ploss }}</div>
        </template>

        <template v-if="column.key === 'action'">
          <span v-if="record.status == 1">
            <!-- <a @click="() => {
              isOpenEdit = true;
              addOrEditType = 2;
              addOrEditData = record;
            }">编辑</a>
            <a-divider type="vertical" /> -->
            <a-tooltip @click="() => {
                isOpenEditFK = true;
                addOrEditDataFK = record;
              }">
                <template #title>输赢控制</template>
                <ThunderboltOutlined />
              </a-tooltip>
            <!-- <a-button type="primary" @click="() => {
              updateStatus(record.id, 1);
            }">赢</a-button>
            <a-divider type="vertical" />
            <a-button type="primary" danger @click="() => {
              updateStatus(record.id, 2);
            }">输</a-button> -->
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
  <!-- 用户风控 -->
  <FKEdit v-if="isOpenEditFK" ref="addOrEditRefFK" :isOpen="isOpenEditFK" :formItems="[]" :data="addOrEditDataFK" @close="() => {
    isOpenEditFK = false;
  }
    " @submit="editFKSubmit" />
</template>
<script setup lang="ts">
import { message } from 'ant-design-vue';
import { h, ref, onMounted } from 'vue';
import { PlusCircleOutlined, UndoOutlined } from '@ant-design/icons-vue';
import { leverOrderApi } from '/@/api/business/admin/leverOrder-api';
import AddOrEdit from "/@/components/edit/edit.vue"
import FKEdit from "./components/fk/fk.vue"
const addOrEditRef = ref();
const isOpenEdit = ref(false);
const addOrEditType = ref(1);
const addOrEditData = ref({});
const formItems = [];
const winStrs = [
'正常','盈利', '亏损'
];
//风控
const isOpenEditFK = ref(false);
const addOrEditDataFK = ref({});
const addOrEditRefFK = ref();
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
  // {
  //   name: 'id',
  //   title: 'ID',
  //   dataIndex: 'id',
  //   key: 'id',
  //   width: 100
  // },
  {
    title: '订单号',
    dataIndex: 'orderNo',
    key: 'orderNo',
    width: 120
  },
  // {
  //   title: '用户ID',
  //   dataIndex: 'uid',
  //   key: 'uid',
  //   width: 100
  // },
  {
    title: '用户名',
    dataIndex: 'username',
    key: 'username',
    width: 100
  },
  {
    title: '购买金额',
    dataIndex: 'num',
    key: 'num',
    width: 100
  },
  {
    title: '倍数',
    dataIndex: 'fold',
    key: 'fold',
    width: 100
  },
  {
    title: '止盈/止损 ',
    dataIndex: 'win',
    key: 'win',
    width: 100
  },
  {
    title: '买后余额',
    dataIndex: 'buyOrblance',
    key: 'buyOrblance',
    width: 100
  },
  {
    title: '盈亏金额',
    dataIndex: 'ploss',
    key: 'ploss',
    width: 80
  },
  {
    title: '手续费',
    dataIndex: 'premium',
    key: 'premium',
    width: 70
  },
  {
    title: '类型',
    dataIndex: 'hyzd',
    key: 'hyzd',
    width: 100
  },
  {
    title: '输赢控制',
    dataIndex: 'kongyk',
    key: 'kongyk',
    width: 100
  },
  {
    title: '状态',
    dataIndex: 'status',
    key: 'status',
    width: 100
  },
  {
    title: '币种',
    dataIndex: 'coinname',
    key: 'coinname',
    width: 100
  },
  {
    title: '建仓时间',
    dataIndex: 'buytime',
    key: 'buytime',
    width: 150
  },
  {
    title: '操作',
    key: 'action',
    width: 100,
    fixed: 'right',
  },
];

//风控
async function editFKSubmit(data) {
  updateStatus(data.id, data.kongyk);
  isOpenEditFK.value = false;
}
//分页方法
async function tableChange(pag: { pageSize: number; current: number }) {
  pagination.value.pageSize = pag.pageSize;
  pagination.value.current = pag.current;
  loadData();

}
//设置亏盈
async function updateStatus(id, kongyk) {
  let data = await leverOrderApi.editKonglo({ id, kongyk });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await leverOrderApi.addOrUpdate(submitData);
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
  let data = await leverOrderApi.list({
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
