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
        <template v-if="column.key === 'rtype'">
          <div v-if="record.rtype == 1" style="color: green;">购买</div>
          <div v-if="record.rtype == 2" style="color: red;">赠送</div>
        </template>
        <template v-if="column.key === 'type'">
          {{ record.type == 1 ? "独资" : "共享" }}
        </template>
        <template v-if="column.key === 'status'">
          <div v-if="record.status == 1" style="color: green;">正常</div>
          <div v-if="record.status == 2" style="color: red;">停止</div>
          <div v-if="record.status == 3" style="">过期</div>
        </template>
        <template v-if="column.key === 'imgs'">
          <a-image height="60px" :preview="true"
                                    :src="record.imgs?record.imgs:'xxx'"
                                    fallback="data:image/png;base64,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" />
                            
        </template>
        <template v-if="column.key === 'outtype'">
          <div>产出类型：{{ record.outtype == 1 ? "按产值" : "按币量" }}</div>
          <div>日产量：{{ record.dayoutnum }}</div>
        </template>
        <template v-if="column.key === 'pricenum'">
          {{ record.pricenum }}usdt
        </template>
        <template v-if="column.key === 'buymax'">
          {{ record.buymax }}台
        </template>
        <template v-if="column.key === 'cycle'">
          {{ record.cycle }}天
        </template>

        <template v-if="column.key === 'action'">
          <span>
            <a @click="() => {
              isOpenEdit = true;
              addOrEditType = 2;
              addOrEditData = record;
            }">编辑</a>
            <a-divider type="vertical" />
            <a @click="() => {
              updateStatus(record.id, 1);
            }">启用</a>
            <a-divider type="vertical" />
            <a @click="() => {
              updateStatus(record.id, 2);
            }">禁用</a>
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
    :name="'矿机'" :data="addOrEditData" @close="() => {
      isOpenEdit = false;
    }" @submit="addOrEditSubmit" />
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
const formItems = [{
  name: "title",
  label: "矿机名称",
  placeholder: '请输入矿机名称！',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "content",
  label: "矿机详情",
  placeholder: '请输入矿机详情',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "imgs",
  label: "矿机图片",
  placeholder: '矿机图片',
  type: "image",
  defaultValue: '',
  rules: [
    {
      required: false,
      message: '必填选项',
    },
  ]
}, {
  name: "outcoin",
  label: "产出币种",
  placeholder: '',
  type: "select",
  defaultValue: 1,
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ],
  selects: [
    { name: "usdtERC20", value: 'usdt' },
    { name: "btcBTC", value: 'btc' }
  ]
}, {
  name: "dayoutnum",
  label: "收益率",
  placeholder: '设置数字（0-100）%',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "pricecoin",
  label: "矿机单价类型",
  placeholder: '',
  type: "select",
  defaultValue: 1,
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ],
  selects: [
    { name: "usdtERC20", value: 'usdt' },
    { name: "btcBTC", value: 'btc' }
  ]
}, {
  name: "pricenum",
  label: "矿机单价",
  placeholder: '请输入矿机单价',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "cycle",
  label: "矿机周期",
  placeholder: '请输入矿机周期',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "status",
  label: "矿机状态",
  placeholder: '',
  type: "select",
  defaultValue: 1,
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ],
  selects: [
    { name: "可用", value: 1 },
    { name: "禁用", value: 2 }
  ]
}
];

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
    width: 100
  },
  {
    title: '矿机标题',
    dataIndex: 'title',
    key: 'title',
    width: 250
  },
  {
    title: '图片',
    dataIndex: 'imgs',
    key: 'imgs',
    width: 100
  }, {
    title: '购买单价',
    dataIndex: 'pricenum',
    key: 'pricenum',
    width: 150
  },
  {
    title: '周期',
    dataIndex: 'cycle',
    key: 'cycle',
    width: 80
  },
  {
    title: '矿机状态',
    dataIndex: 'status',
    key: 'status',
    width: 100
  },
  {
    title: '添加时间',
    dataIndex: 'addtime',
    key: 'addtime',
    width: 170
  },
  {
    title: '操作',
    key: 'action',
    width: 250,
    fixed: 'right',
  },
];

//分页方法
async function tableChange(pag: { pageSize: number; current: number }) {
  pagination.value.pageSize = pag.pageSize;
  pagination.value.current = pag.current;
  loadData();

}
//设置
async function updateStatus(id, type) {
  let data = await kuangmApi.kuangjStatus({ id, type });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//删除数据
async function deleteData(id) {
  let data = await kuangmApi.kuangjStatus({ id, type: 3 });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await kuangmApi.addkj(submitData);
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
  let data = await kuangmApi.getkuangjiList({
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
