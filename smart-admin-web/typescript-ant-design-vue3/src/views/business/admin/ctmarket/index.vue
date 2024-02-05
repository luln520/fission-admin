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
          <span v-if="record.status == 1" style="color: green;">可用</span>
          <span v-if="record.status != 1" style="color: red;">禁用</span>
        </template>
        <template v-if="column.key === 'state'">
          <span v-if="record.state == 1" style="color: green;">可用</span>
          <span v-if="record.state != 1" style="color: red;">禁用</span>
        </template>
        <template v-if="column.key === 'logo'">
          <a-image height="60px" :preview="true"
                                    :src="imageConfig.baseImageUrl+record.logo"
                                    fallback="data:image/png;base64,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" />
                            
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
    :name="'市场'" :data="addOrEditData" @close="() => {
      isOpenEdit = false;
    }" @submit="addOrEditSubmit" />
</template>
<script setup lang="ts">
import { message } from 'ant-design-vue';
import { h, ref, onMounted } from 'vue';
import { PlusCircleOutlined, UndoOutlined } from '@ant-design/icons-vue';
import { ctmarketConfigApi } from '/@/api/business/admin/ctmarketConfig-api';
import AddOrEdit from "/@/components/edit/edit.vue"
import { toLower, toUpper } from 'lodash';
import { imageConfig } from '/@/config/app-config';
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
//编辑配置
const formItems = [{
  name: "name",
  label: "市场名称",
  placeholder: '如：BTC',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "publishTime",
  label: "发行时间",
  placeholder: '时间',
  type: "dateTime",
  defaultValue: '',
  rules: [
    {
      required: false,
      message: '必填选项',
    },
  ]
}, {
  name: "publishNum",
  label: "发行数量",
  placeholder: '填写数字',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "logo",
  label: "logo",
  placeholder: 'logo',
  type: "image",
  defaultValue: '',
  rules: [
    {
      required: false,
      message: '必填选项',
    },
  ]
}, {
  name: "infoZh",
  label: "简介（中文）",
  placeholder: '请填写中文',
  type: "textarea",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "infoEn",
  label: "简介（英文）",
  placeholder: '请填写英文',
  type: "textarea",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "infoEs",
  label: "简介（西班牙语）",
  placeholder: '请填写西班牙语',
  type: "textarea",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "infoAr",
  label: "简介（阿拉伯语）",
  placeholder: '请填写阿拉伯语',
  type: "textarea",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "infoVi",
  label: "简介（越语）",
  placeholder: '请填写越语',
  type: "textarea",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "infoJa",
  label: "简介（日语）",
  placeholder: '请填写日语',
  type: "textarea",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "status",
  label: "显示状态",
  placeholder: '',
  type: "select",
  defaultValue: '',
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
  name: "state",
  label: "开启交易",
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
    title: '市场交易对',
    dataIndex: 'title',
    key: 'title',
    width: 200
  },
  {
    title: '发行时间',
    dataIndex: 'publishTime',
    key: 'publishTime',
    width: 200
  },
  {
    title: '发行数量',
    dataIndex: 'publishNum',
    key: 'publishNum',
    width: 200
  },
  {
    title: 'logo',
    dataIndex: 'logo',
    key: 'logo',
    width: 200
  },
  {
    title: '简介',
    dataIndex: 'infoZh',
    key: 'infoZh',
    width: 200
  },
  {
    title: '排序',
    dataIndex: 'sort',
    key: 'sort',
    width: 150
  },
  {
    title: '显示状态',
    dataIndex: 'status',
    key: 'status',
    width: 150
  },
  {
    title: '交易状态',
    dataIndex: 'state',
    key: 'state',
    width: 150
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
//删除数据
async function deleteData(id) {
  let data = await ctmarketConfigApi.delete({ id });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//启用禁用
async function updateStatus(id, status) {
  let data = await ctmarketConfigApi.updateStatus({ id, status });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//新增或者编辑
async function addOrEditSubmit(submitData) {
  //处理其他字段
  submitData["title"] = submitData["name"] + "/USDT";
  submitData["coinname"] = toLower(submitData["name"]);
  submitData["name"] = submitData["coinname"] + "_usdt";
  submitData["symbol"] = submitData["coinname"] + "usdt";
  let data = await ctmarketConfigApi.addOrUpdate(submitData);
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
  let data = await ctmarketConfigApi.list({ pageNum: pagination.value.current, pageSize: pagination.value.pageSize });
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
