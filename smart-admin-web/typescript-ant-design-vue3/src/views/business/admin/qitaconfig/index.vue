<template>
  <!-- 表格 -->
  <a-card style="min-height:100%;margin-top: 10px;">
    <!-- 编辑弹框 -->
    <AddOrEdit v-if="isShow" @submit="addOrEditSubmit" :formItems="formItems" :data="baseData" />
  </a-card>
</template>
<script setup lang="ts">
import { message } from 'ant-design-vue';
import { h, ref, onMounted } from 'vue';
import { PlusCircleOutlined, UndoOutlined } from '@ant-design/icons-vue';
import { webConfigApi } from '/@/api/business/admin/webConfig-api';
import AddOrEdit from "/@/components/edit/editIn.vue"
const isShow = ref(false)
const baseData = ref({});
const formItems = [
  {
    name: "smsemail",
    label: "验证码发送邮箱",
    placeholder: '发送邮箱验证码的邮箱账号',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "emailcode",
    label: "邮箱授权码",
    placeholder: '发送邮箱验证码的邮箱授权码',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "smstemple",
    label: "验证码模板",
    placeholder: '短信验证码模板',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "smtpdz",
    label: "邮箱服务器",
    placeholder: '163邮箱是：smtp.163.com QQ邮箱是smtp.qq.com 谷歌邮箱是：smtp.gmail.com(谷歌邮箱授权码填密码)',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "smsUrl",
    label: "短信服务器",
    placeholder: '提交发送的网关地址',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "smsId",
    label: "短信商户ID",
    placeholder: '商户ID',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "smsKey",
    label: "短信商户key",
    placeholder: '商户密钥',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "startmoney",
    label: "归集起始金额",
    placeholder: 'USDT一键归集起始金额',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "shouxufeiid",
    label: "TRX账户私钥",
    placeholder: '用来给所有账户转TRX手续费',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "guijiid",
    label: "USDT接收地址",
    placeholder: '所有账户的USDT都将自动转账到该账户',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "tgtext",
    label: "推荐页面推广语",
    placeholder: '推荐页面的推广语，不要多于40个字',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "gfemail",
    label: "官方客服邮箱",
    placeholder: '官方客服邮箱',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "footertext",
    label: "PC端下方文字",
    placeholder: '显示在PC端LOGO下的文字',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "tymoney",
    label: "注册赠送体验金",
    placeholder: '注册赠送体验金',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "webswitch",
    label: "网站注册开关",
    placeholder: '关闭时禁止注册',
    type: "select",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ],
    selects: [
      { name: "开放", value: 1 },
      { name: "关闭", value: 2 }
    ]
  }, {
    name: "tbswitch",
    label: "提币总开关",
    placeholder: '关闭时禁止所有币种提币',
    type: "select",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ],
    selects: [
      { name: "开放", value: 1 },
      { name: "关闭", value: 2 }
    ]
  }, {
    name: "regjl",
    label: "注册是否赠送体验矿机",
    placeholder: '请选择注册是否赠送体验矿机',
    type: "select",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ],
    selects: [
      { name: "赠送", value: 1 },
      { name: "不送", value: 2 },
      { name: "不送", value: 3 }
    ]
  }
];

//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await webConfigApi.addOrUpdate(submitData);
  if (data.ok) {
    message.success("操作成功！");
  } else {
    message.error("操作失败！请重试~");
  }
  loadData();
}
//获取表格数据
async function loadData() {
  let data = await webConfigApi.find();
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
