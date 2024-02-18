<template>
  <!-- 表格 -->
  <a-card style="min-height:100%;margin-top: 10px;">
    <!-- 编辑弹框 -->
    <AddOrEdit v-if="isShow" @submit="addOrEditSubmit" :formItems="formItems" :data="baseData"
      privilege="system:webConfig:add" />
  </a-card>
</template>
<script setup lang="ts">
import { message } from 'ant-design-vue';
import { h, ref, onMounted } from 'vue';
import { PlusCircleOutlined, UndoOutlined } from '@ant-design/icons-vue';
import { companyApi } from '/@/api/business/admin/company-api';
import AddOrEdit from "/@/components/edit/editIn.vue"
const isShow = ref(false)
const baseData = ref({});
const formItems = [
  {
    name: "name",
    label: "公司名称",
    placeholder: "请输入公司名称！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "companyName",
    label: "平台名称",
    placeholder: "请输入平台名称！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "companyAccount",
    label: "平台账号",
    placeholder: "请输入平台账号！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "companyPwd",
    label: "平台密码",
    placeholder: "请输入平台密码！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "companyDomain",
    label: "平台域名",
    placeholder: "请输入平台域名！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  }, {
    name: "companySkin",
    label: "平台皮肤",
    placeholder: "请输入平台皮肤！",
    type: "select",
    defaultValue: 1,
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ],
    selects: [
      { name: "碧蓝色", value: 1 },
      { name: "翠绿色", value: 2 },
      { name: "柠檬黄", value: 3 }
    ]
  }, {
    name: "companyLogoName",
    label: "平台logo名称",
    placeholder: "请输入平台logo名称！",
    type: "image",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "companyLogo",
    label: "平台logo",
    placeholder: "请输入平台logo！",
    type: "image",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "companyMail",
    label: "平台邮箱",
    placeholder: "请输入平台邮箱！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "companyMailPwd",
    label: "平台邮箱密码",
    placeholder: "请输入平台邮箱密码！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "hyFee",
    label: "合约交易手续费",
    placeholder: "请输入合约交易手续费！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "kjFee",
    label: "矿机交易手续费",
    placeholder: "请输入矿机交易手续费！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "leverFee",
    label: "杠杆交易手续费",
    placeholder: "请输入杠杆交易手续费！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  }, {
    name: "logo1",
    label: "H5轮播图1",
    placeholder: "请输入H5轮播图1！",
    type: "image",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  }, {
    name: "logo2",
    label: "H5轮播图2",
    placeholder: "请输入H5轮播图2！",
    type: "image",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  }, {
    name: "logo3",
    label: "H5轮播图3",
    placeholder: "请输入H5轮播图3！",
    type: "image",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "pcLogo1",
    label: "PC轮播图1",
    placeholder: "请输入PC轮播图1！",
    type: "image",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },

  {
    name: "pcLogo2",
    label: "PC轮播图2",
    placeholder: "请输入PC轮播图2！",
    type: "image",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },

  {
    name: "pcLogo3",
    label: "PC轮播图3",
    placeholder: "请输入PC轮播图3！",
    type: "image",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },

  {
    name: "pcLogo4",
    label: "PC轮播图4",
    placeholder: "请输入PC轮播图4！",
    type: "image",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  }, {
    name: "iosDomain",
    label: "苹果下载页域名",
    placeholder: "请输入苹果下载页域名！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "androidDomain",
    label: "安卓下载页域名",
    placeholder: "请输入安卓下载页域名！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "phoneDomian",
    label: "手机下载页域名",
    placeholder: "请输入手机下载页域名！",
    type: "input",
    defaultValue: "",
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
  },
  {
    name: "status",
    label: "状态",
    placeholder: "请输入状态！",
    type: "select",
    defaultValue: 1,
    rules: [
      {
        required: true,
        message: "必填选项"
      }
    ]
    ,
    selects: [
      { name: "开启", value: 1 },
      { name: "关闭", value: 2 }
    ]
  }
]


//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await companyApi.addOrUpdate(submitData);
  if (data.ok) {
    message.success("操作成功！");
  } else {
    message.error("操作失败！请重试~");
  }
  loadData();
}
//获取表格数据
async function loadData() {
  const id = localStorage.getItem("companyId");
  let data = await companyApi.detail({ id });
  if (data.ok) {
    data = data.data;
    baseData.value = data;
    isShow.value = true;
  }
}
onMounted(() => {
  loadData()
});
</script>
<style lang="less" scoped>
@import './index.less';
</style>
