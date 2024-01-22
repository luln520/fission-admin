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
import { tradeApi } from '/@/api/business/admin/trade-api';
import AddOrEdit from "/@/components/edit/editIn.vue"
const isShow = ref(false)
const baseData = ref({});
const formItems = [
  {
    name: "hySxf",
    label: "交易手续费",
    placeholder: '注意：交易的手续费，如：10%；写成10；',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "hyTime",
    label: "合约结单时间",
    placeholder: '注意： 如时间为：1分钟、3分钟、5分钟、30分钟，则请用字母逗号将时间分开，如输入：1,3,5,8。如没有此玩法则留空。必须为四个',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "hyYkbl",
    label: "合约盈亏比例",
    placeholder: '注意： 如比例为：75%、77%，80%、85%，则请用字母逗号将比例分开，如输入：75,77,80,85。必须为四个，且不得为空',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "hyTzed",
    label: "投资最低额度",
    placeholder: '注意： 如额度为：10USDT、50USDT，100USDT、1000USDT，则请用字母逗号将比例分开，如输入：10,50,100,1000。',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "hyKstime",
    label: "合约开市时间",
    placeholder: '填写格式如：00:00~24:00',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }
  // , {
  //   name: "hyKsid",
  //   label: "指定亏损ID",
  //   placeholder: '说明： 此处设置会员ID（如：8888），多个用户用|符号分开（如：8888|9999）设置之后该会员所有订单都会亏损，请谨慎操作。如停止该功能请在上面留空或者填0，并提交。',
  //   type: "input",
  //   defaultValue: '',
  //   rules: [
  //     {
  //       required: false,
  //       message: '必填选项',
  //     },
  //   ]
  // }, {
  //   name: "hyYlid",
  //   label: "指定盈利ID",
  //   placeholder: '说明： 此处设置会员ID（如：8888），多个用户用|符号分开（如：8888|9999）设置之后该会员所有订单都会亏损，请谨慎操作。如停止该功能请在上面留空或者填0，并提交。',
  //   type: "input",
  //   defaultValue: '',
  //   rules: [
  //     {
  //       required: false,
  //       message: '必填选项',
  //     },
  //   ]
  // }, {
  //   name: "hyFkgl",
  //   label: "风控概率",
  //   placeholder: '表示总盈利比例，填写20表示20%订单盈利，例 如同时结算10单，其中只有2单盈利',
  //   type: "input",
  //   defaultValue: '',
  //   rules: [
  //     {
  //       required: false,
  //       message: '必填选项',
  //     },
  //   ]
  // }, {
  //   name: "hyMin",
  //   label: "投资最低额度",
  //   placeholder: '每单最低投资额度',
  //   type: "input",
  //   defaultValue: '',
  //   rules: [
  //     {
  //       required: false,
  //       message: '必填选项',
  //     },
  //   ]
  // }
];

//新增或者编辑
async function addOrEditSubmit(submitData) {
  let data = await tradeApi.edit(submitData);
  if (data.ok) {
    message.success("操作成功！");
  } else {
    message.error("操作失败！请重试~");
  }
  loadData();
}
//获取表格数据
async function loadData() {
  let data = await tradeApi.hysettingId();
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
