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
        <template v-if="column.key === 'status'">
          {{ record.status == 1 ? "可用" : "不可用" }}
        </template>
        <template v-if="column.key === 'addtime'">
          {{ timestampToStr(record.addtime*1000) }}
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
import { userlogApi } from '/@/api/business/admin/userLog-api';
import AddOrEdit from "/@/components/edit/edit.vue"
import { timestampToStr } from '/@/utils/util';
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
    title: '用户名',
    dataIndex: 'username',
    key: 'username',
    width: 180
  },
  {
    title: '类型',
    dataIndex: 'type',
    key: 'type',
    width: 150
  },
  {
    title: '内容',
    dataIndex: 'remark',
    key: 'remark',
    width: 150
  },
  {
    title: '操作ip',
    dataIndex: 'addip',
    key: 'addip',
    width: 100,
    ellipsis: true,
  },
  {
    title: '操作时间',
    dataIndex: 'addtime',
    key: 'addtime',
    width: 100,
    ellipsis: true,
  }
  ,
  {
    title: '状态',
    dataIndex: 'status',
    key: 'status',
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
//设置取消代理
async function setAgent(id) {
  let data = await userlogApi.setAgent({ id, isAgent: 0 });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await userlogApi.addOrUpdate(submitData);
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
  let data = await userlogApi.list({
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
