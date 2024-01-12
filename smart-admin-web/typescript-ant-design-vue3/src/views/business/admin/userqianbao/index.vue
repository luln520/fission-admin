<template>
  <!-- 头部 -->
  <a-card style="height: 10%;min-height: 80px;">
    <a-space>
      <a-button type="primary" :icon="h(PlusCircleOutlined)" @click="() => {
        isOpenEdit = true;
        addOrEditType = 1;
        addOrEditData = {};
      }">新增</a-button>
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

        <template v-if="column.key === 'status'">
          <span v-if="record.status == 1" style="color: green;">可用</span>
          <span v-if="record.status != 1" style="color: red;">禁用</span>
        </template>
        <template v-if="column.key === 'action'">
          <span>
            <a @click="() => {
              isOpenEdit = true;
              addOrEditType = 2;
              addOrEditData = record;
            }">编辑</a>
            <a-divider type="vertical" />
            <a-popconfirm title="确认删除吗?" ok-text="确认" cancel-text="取消" @confirm="() => {
              deleteData(record.id);
            }">
              <a>删除</a>
            </a-popconfirm>
          </span>
        </template>
      </template>
    </a-table>
  </a-card>
  <!-- 编辑弹框 -->
  <AddOrEdit v-if="isOpenEdit" ref="addOrEditRef" :isOpen="isOpenEdit" :formItems="formItems" :type="addOrEditType"
    :name="'用户钱包'" :data="addOrEditData" @close="() => {
      isOpenEdit = false;
    }" @submit="addOrEditSubmit" />
</template>
<script setup lang="ts">
import { message } from 'ant-design-vue';
import { h, ref, onMounted } from 'vue';
import { PlusCircleOutlined, UndoOutlined } from '@ant-design/icons-vue';
import { userQianBaoApi } from '/@/api/business/admin/userQianBao-api';
import { coinApi } from '/@/api/business/admin/coin-api';
import AddOrEdit from "/@/components/edit/edit.vue"
const addOrEditRef = ref();
const isOpenEdit = ref(false);
const addOrEditType = ref(1);
const addOrEditData = ref({});
const conis = ref([] as any[])
//编辑数据
const formItems = [{
  name: "coinname",
  label: "用户名称",
  placeholder: '请输入用户名！',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "name",
  label: "币种",
  placeholder: '',
  type: "select",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ],
  selects: conis.value
}, {
  name: "remark",
  label: "钱包名称",
  placeholder: '请输入钱包名称！',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "addr",
  label: "钱包地址",
  placeholder: '请输入钱包地址！',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}
];

//分页数据
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10
});
const searchUserName=ref("");
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
    title: '用户名',
    dataIndex: 'coinname',
    key: 'coinname',
    width: 200
  },
  {
    title: '币种',
    dataIndex: 'name',
    key: 'name',
    width: 100
  },
  {
    title: '钱包名称',
    dataIndex: 'remark',
    key: 'remark',
    width: 100
  },
  {
    title: '钱包地址',
    dataIndex: 'addr',
    key: 'addr',
    width: 350
  },
  {
    title: '操作时间',
    dataIndex: 'addtime',
    key: 'addtime',
    width: 200
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
//删除数据
async function deleteData(id) {
  let data = await userQianBaoApi.del({ id });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//新增或者编辑
async function addOrEditSubmit(submitData) {
  submitData["userid"]=2052;
  let data = await userQianBaoApi.addUpdate(submitData);
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
  let data = await userQianBaoApi.list({
    pageNum: pagination.value.current, pageSize: pagination.value.pageSize,username:searchUserName.value
  });
  if (data.ok) {
    data = data.data;
    tableData.value = data.records;
    pagination.value.total = data.total;
  }
}

//获取币
async function coinLoadData() {
  let data = await coinApi.list({
    pageNum: pagination.value.current, pageSize: pagination.value.pageSize
  });
  if (data.ok) {
    data = data.data;
    for (const iterator of data.records) {
      if (iterator.status == 1) {
        conis.value.push({ name: iterator.title, value: iterator.name });
      }
    }
  }
}
onMounted(() => {
  loadData();
  coinLoadData();
});
</script>
<style lang="less" scoped>
@import './index.less';
</style>
