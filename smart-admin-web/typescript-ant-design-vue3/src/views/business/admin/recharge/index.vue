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
        <template v-if="column.key === 'czline'">
          <div>{{ record.czline }}</div>
          <div>{{ record.address }}</div>
        </template>
        <template v-if="column.key === 'status'">
          <div>{{ statusStrs[record.status - 1] }}</div>
          <div>{{ record.atype == 0 ? "用户充值" : "管理员充值" }}</div>
        </template>
        <!-- 操作 -->
        <template v-if="column.key === 'action'">
          <span v-if="record.status == 1">
            <a @click="() => {
              confirm(record.id);
            }">通过</a>
            <a-divider type="vertical" />
            <a @click="() => {
              reject(record.id);
            }">驳回</a>
          </span>
          <span v-if="record.status != 1">
            已处理
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
import { financeApi } from '/@/api/business/admin/finance-api';
import AddOrEdit from "/@/components/edit/edit.vue"
import { toLower, toUpper } from 'lodash';
const addOrEditRef = ref();
const isOpenEdit = ref(false);
const addOrEditType = ref(1);
const addOrEditData = ref({});
//表格数据
const tableData = ref([] as any[]);
//分页数据
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10
});
const searchUserName=ref("");
const statusStrs = [
  "待审核", "审核通过", "不通过"
]
//编辑配置
const formItems = [];
//行配置
const columns = [
{
    title: '订单号',
    dataIndex: 'orderNo',
    key: 'orderNo',
    width: 150
  },
  {
    title: '用户名',
    dataIndex: 'username',
    key: 'username',
    width: 150
  },
  {
    title: '充值网络',
    dataIndex: 'czline',
    key: 'czline',
    width: 300
  },
  {
    title: '充值时间',
    dataIndex: 'addtime',
    key: 'addtime',
    width: 200
  },
  {
    title: '处理时间',
    dataIndex: 'updatetime',
    key: 'updatetime',
    width: 200
  },
  {
    title: '充值数量',
    dataIndex: 'num',
    key: 'num',
    width: 120
  },
  {
    title: '实际到账(存疑)',
    dataIndex: 'num',
    key: 'num',
    width: 120
  },
  {
    title: '转账凭证',
    dataIndex: 'payimg',
    key: 'payimg',
    width: 120
  },
  {
    title: '状态',
    key: 'status',
    dataIndex: 'status',
    width: 200,
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
//打开编辑
async function openEdit(record) {
  //处理数据
  record.name = toUpper(record.coinname);
  isOpenEdit.value = true;
  addOrEditType.value = 2;
  addOrEditData.value = record;
}
//确认
async function confirm(id) {
  let data = await financeApi.confirm({ id });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//驳回
async function reject(id) {
  let data = await financeApi.reject({ id });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await financeApi.addOrUpdate(submitData);
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
  let data = await financeApi.rechargeList({ pageNum: pagination.value.current, pageSize: pagination.value.pageSize,username:searchUserName.value });
  if (data.ok) {
    data = data.data;
    tableData.value = data.records;
    pagination.value.total = data.total;
    console.info(tableData.value)
  }
}
onMounted(() => {
  loadData()
});
</script>
<style lang="less" scoped>
@import './index.less';
</style>
