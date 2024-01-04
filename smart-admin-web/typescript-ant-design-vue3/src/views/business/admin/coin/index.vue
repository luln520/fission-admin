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
        <template v-if="column.key === 'type'">
          {{ record.type == 1 ? "钱包币" : (record.type == 2 ? "平台币" : (record.type == 3 ? "认购币" : "未知")) }}
        </template>
        <template v-if="column.key === 'agentId'">
          <span v-if="record.defaultOn == 1" style="color: red;">系统默认通道</span>
          <span v-if="record.defaultOn != 1" style="color: green;">{{ record.defaultOn }}</span>
        </template>
        <template v-if="column.key === 'czaddress'">
          <div>{{ record.czaddress }}</div>
          <div>USDT： {{ record.blance.toFixed(4) }} <span style="width: 10px;"> </span> TRX：{{ record.trxBlance.toFixed(4)
          }}</div>
        </template>
        <template v-if="column.key === 'status'">
          <span v-if="record.status == 1" style="color: green;">可用</span>
          <span v-if="record.status != 1" style="color: red;">禁用</span>
        </template>
        <template v-if="column.key === 'czstatus'">
          <span v-if="record.czstatus == 1" style="color: green;">正常</span>
          <span v-if="record.czstatus != 1" style="color: red;">禁止</span>
        </template>
        <template v-if="column.key === 'txstatus'">
          <span v-if="record.txstatus == 1" style="color: green;">正常</span>
          <span v-if="record.txstatus != 1" style="color: red;">禁止</span>
        </template>
        <template v-if="column.key === 'sxftype'">
          <span>{{ record.sxftype == 1 ? "按比例" : "按数量" }}：{{ record.txsxf.toFixed(2) }}%</span>
        </template>
        <template v-if="column.key === 'bbsxf'">
          <span>{{ record.bbsxf.toFixed(2) }}%</span>
        </template>
        <template v-if="column.key === 'hysxf'">
          <span>{{ record.hysxf.toFixed(2) }}%</span>
        </template>
        <template v-if="column.key === 'defaultOn'">
          <span v-if="record.defaultOn == 1" style="color: green;">是</span>
          <span v-if="record.defaultOn != 1" style="color: red;">否</span>
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
            <a-divider type="vertical" />
            <a @click="() => {
              updateStatus(record.id, 1);
            }">启用</a>
            <a-divider type="vertical" />
            <a @click="() => {
              updateStatus(record.id, 2);
            }">禁用</a>
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
import { coinApi } from '/@/api/business/admin/coin-api';
import AddOrEdit from "/@/components/edit/edit.vue"
const addOrEditRef = ref();
const isOpenEdit = ref(false);
const addOrEditType = ref(1);
const addOrEditData = ref({});
const formItems = [{
  name: "name",
  label: "币种名称",
  placeholder: '必须是小写！',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "title",
  label: "中文名称",
  placeholder: '请输入中文名称！',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ],
  selects: [
    { name: "是", value: 1 },
    { name: "否", value: 0 }
  ]
}, {
  name: "defaultOn",
  label: "默认通道",
  placeholder: '请输入中文名称！',
  type: "select",
  defaultValue: 1,
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ],
  selects: [
    { name: "是", value: 1 },
    { name: "否", value: 0 }
  ]
}, {
  name: "agentId",
  label: "代理Id",
  placeholder: '用户只能用该地址支付',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "type",
  label: "币种类型",
  placeholder: '请输入中文名称！',
  type: "select",
  defaultValue: 1,
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ],
  selects: [
    { name: "钱包币", value: 1 },
    { name: "平台币", value: 2 },
    { name: "认购币", value: 3 }
  ]
}, {
  name: "czline",
  label: "充值网络1",
  placeholder: '请输入充值网络1',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "czaddress",
  label: "合约地址1",
  placeholder: '请输入合约地址1',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "czline2",
  label: "充值网络2",
  placeholder: '请输入充值网络2',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: false,
      message: '',
    },
  ]
}, {
  name: "czaddress2",
  label: "合约地址2",
  placeholder: '请输入合约地址2',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: false,
      message: '',
    },
  ]
}, {
  name: "czstatus",
  label: "充币状态",
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
    { name: "正常", value: 1 },
    { name: "禁止", value: 2 }
  ]
}, {
  name: "czminnum",
  label: "最小充值额度",
  placeholder: '请输入最小充值额度',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "sxftype",
  label: "手续费类型",
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
    { name: "按百分比扣除", value: 1 },
    { name: "按数量扣除", value: 2 }
  ]
}, {
  name: "txsxf",
  label: "按百分比",
  placeholder: '%（填写0.01-100内数字）',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "txsxfN",
  label: "按数量",
  placeholder: '填写0.01-100内数字',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "txminnum",
  label: "最小提币数量",
  placeholder: '大于等于0.01数字',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "txmaxnum",
  label: "最大提币数量",
  placeholder: '大于等于10000数字',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "txstatus",
  label: "提币状态",
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
    { name: "正常提币", value: 1 },
    { name: "禁止提币", value: 2 }
  ]
}, {
  name: "status",
  label: "币种状态",
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
}, {
  name: "bbsxf",
  label: "币币交易手续费",
  placeholder: '%（填写0.01-100内数字）',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "hysxf",
  label: "合约交易手续费",
  placeholder: '%（填写0.01-100内数字）',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "sort",
  label: "排序",
  placeholder: '填写数字',
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
    title: '代码',
    dataIndex: 'name',
    key: 'name',
    width: 70
  },
  {
    title: '名称',
    dataIndex: 'title',
    key: 'title',
    width: 100
  },
  {
    title: '类型',
    dataIndex: 'type',
    key: 'type',
    width: 100
  },
  {
    title: '代理信息(有疑问)',
    dataIndex: 'agentId',
    key: 'agentId',
    width: 150
  },
  {
    title: '合约地址',
    dataIndex: 'czaddress',
    key: 'czaddress',
    width: 400
  },
  {
    title: '状态',
    dataIndex: 'status',
    key: 'status',
    width: 80
  },
  {
    title: '充币',
    dataIndex: 'czstatus',
    key: 'czstatus',
    width: 80
  },
  {
    title: '提币',
    dataIndex: 'txstatus',
    key: 'txstatus',
    width: 80
  },
  {
    title: '手续费类型',
    dataIndex: 'sxftype',
    key: 'sxftype',
    width: 150
  },
  {
    title: '币币手续费',
    dataIndex: 'bbsxf',
    key: 'bbsxf',
    width: 120
  },
  {
    title: '合约手续费',
    dataIndex: 'hysxf',
    key: 'hysxf',
    width: 120
  },
  {
    title: '默认通道(有疑问)',
    dataIndex: 'defaultOn',
    key: 'defaultOn',
    width: 100
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
  let data = await coinApi.delete({ id });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//启用禁用
async function updateStatus(id, status) {
  let data = await coinApi.updateStatus({ id, status });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await coinApi.addOrUpdate(submitData);
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
  let data = await coinApi.list({
    pageNum: pagination.value.current, pageSize: pagination.value.pageSize});
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
