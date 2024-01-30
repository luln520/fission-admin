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
    </a-space>
  </a-card>
  <!-- 表格 -->
  <a-card style="min-height: 88%;margin-top: 10px;">
    <a-table :columns="columns" :data-source="tableData" @change="tableChange" :scroll="{ x: 1500 }"
      :pagination="pagination" :bordered="true">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'status'">
          <span v-if="record.status == 1" style="color: green;">显示</span>
          <span v-if="record.status != 1" style="color: red;">隐藏</span>
        </template>
        <template v-if="column.key === 'action'">
          <span>
            <a @click="openEdit(record)">编辑</a>
            <a-divider type="vertical" />
            <a-popconfirm title="确认删除吗?" ok-text="确认" cancel-text="取消" @confirm="() => {
              deleteData(record.id);
            }">
              <a>删除</a>
            </a-popconfirm>
            <!-- <a-divider type="vertical" />
            <a @click="() => {
              updateStatus(record.id, 1);
            }">启用</a>
            <a-divider type="vertical" />
            <a @click="() => {
              updateStatus(record.id, 2);
            }">禁用</a> -->
          </span>
        </template>
      </template>
    </a-table>
  </a-card>
  <!-- 编辑弹框 -->
  <AddOrEdit v-if="isOpenEdit" ref="addOrEditRef" :isOpen="isOpenEdit" :formItems="formItems" :type="addOrEditType"
    :name="'杠杆倍数'" :data="addOrEditData" @close="() => {
      isOpenEdit = false;
    }" @submit="addOrEditSubmit" />
</template>
<script setup lang="ts">
import { message } from 'ant-design-vue';
import { h, ref, onMounted } from 'vue';
import { PlusCircleOutlined, UndoOutlined } from '@ant-design/icons-vue';
import { leverageApi } from '/@/api/business/admin/leverage-api';
import { ctmarketConfigApi } from '/@/api/business/admin/ctmarketConfig-api';
import AddOrEdit from "/@/components/edit/edit.vue"
import { toLower, toUpper } from 'lodash';
const addOrEditRef = ref();
const isOpenEdit = ref(false);
const addOrEditType = ref(1);
const addOrEditData = ref({});
//表格数据
const tableData = ref([] as any[]);
const conis = ref([] as any[])
//分页数据
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10
});
//编辑配置
const formItems = [{
  name: "symbol",
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
  name: "num",
  label: "倍数",
  placeholder: '请输入倍数！',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}];
//行配置
const columns = [
  // {
  //   name: 'id',
  //   title: 'ID',
  //   dataIndex: 'id',
  //   key: 'id',
  //   width: 150
  // },
  {
    title: '币种',
    dataIndex: 'symbol',
    key: 'symbol',
    width: 250
  },
  {
    title: '数值',
    dataIndex: 'num',
    key: 'num',
    width: 150
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    key: 'createTime',
    width: 150
  },
  // {
  //   title: '编辑时间',
  //   dataIndex: 'updateTime',
  //   key: 'updateTime',
  //   width: 150
  // },
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
//打开编辑
async function openEdit(record) {
  //处理数据
  record.name = toUpper(record.coinname);
  isOpenEdit.value = true;
  addOrEditType.value = 2;
  addOrEditData.value = record;
}
//删除数据
async function deleteData(id) {
  let data = await leverageApi.delete({ id });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//启用禁用
async function updateStatus(id, status) {
  let data = await leverageApi.updateStatus({ id, status });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await leverageApi.addOrUpdate(submitData);
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
  let data = await leverageApi.list({ pageNum: pagination.value.current, pageSize: pagination.value.pageSize });
  if (data.ok) {
    data = data.data;
    tableData.value = data.records;
    pagination.value.total = data.total;
    console.info(tableData.value)
  }
}


//获取币
async function coinLoadData() {
  let data = await ctmarketConfigApi.list({
    pageNum: pagination.value.current, pageSize: 100
  });
  if (data.ok) {
    data = data.data;
    for (const iterator of data.records) {
      if (iterator.status == 1) {
        conis.value.push({ name: iterator.title, value: iterator.title });
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
