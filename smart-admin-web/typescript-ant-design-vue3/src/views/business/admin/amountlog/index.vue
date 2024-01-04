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
        <template v-if="column.key === 'type'">
          {{ typeStrs[record.type - 1] }}
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
import { amountLogApi } from '/@/api/business/admin/amountLog-api';
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
//状态
const typeStrs = [
  "充币", "提币", "购买合约", "出售合约", "购买矿机", "购机奖励", "矿机收益冻结", "释放冻结收益", "币币交易USDT", "币币交易币种", "认购扣除", "认购增加", "13一代认购奖励", "二代认购奖励", "三代认购奖励", "提币退回", "充币成功"
]
const formItems = [];
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
    width: 220
  },
  {
    title: '币种',
    dataIndex: 'coinname',
    key: 'coinname',
    width: 150
  }, {
    title: '变动金额',
    dataIndex: 'num',
    key: 'num',
    width: 150
  },
  {
    title: '变动后金额',
    dataIndex: 'afternum',
    key: 'afternum',
    width: 150
  },
  {
    title: '操作类型',
    dataIndex: 'type',
    key: 'type',
    width: 150
  }, {
    title: '时间',
    dataIndex: 'addtime',
    key: 'addtime',
    width: 200
  },
  {
    title: '备注',
    dataIndex: 'remark',
    key: 'remark',
    width: 500,
    ellipsis: true,
  }
];

//分页方法
async function tableChange(pag: { pageSize: number; current: number }) {
  pagination.value.pageSize = pag.pageSize;
  pagination.value.current = pag.current;
  loadData();

}
//设置取消代理
async function setAgent(id) {
  let data = await amountLogApi.setAgent({ id, isAgent: 0 });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await amountLogApi.addOrUpdate(submitData);
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
  let data = await amountLogApi.list({
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
