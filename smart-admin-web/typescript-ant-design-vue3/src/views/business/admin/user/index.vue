<template>
  <!-- 头部 -->
  <a-card style="height: 10%;min-height: 80px;">
    <a-space>
      <a-button type="primary" :icon="h(PlusCircleOutlined)" @click="() => {
        isOpenEdit = true;
        addOrEditType = 1;
        addOrEditData = {};
      }">新增</a-button>
      <a-button type="primary" :icon="h(CommentOutlined)" @click="() => {
        isOpenSendMsg = true;
        sendMsgData.type = 2;
        sendMsgData.id = 10000;
      }">群发通知</a-button>
      <a-button type="" :icon="h(UndoOutlined)" @click="async () => {
        await loadData();
        message.success('刷新成功');
      }">刷新</a-button>
      <a-input-search v-model:value="searchUserName" placeholder="请输入用户ID" enter-button @search="async () => {
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

        <template v-if="column.key === 'logins'">
          {{ record.logins }}次
        </template>

        <template v-if="column.key === 'userType'">
          <span v-if="record.userType == 1" style="color: green;">真实用户</span>
          <span v-if="record.userType == 2" style="color: red;">测试用户</span>
        </template>
        <template v-if="column.key === 'status'">
          <span v-if="record.status == 1" style="color: green;">否</span>
          <span v-if="record.status != 1" style="color: red;">是</span>
        </template>
        <template v-if="column.key === 'addtime'">
          {{ timestampToStr(record.addtime * 1000) }}
        </template>
        <template v-if="column.key === 'rztype'">
          {{ rztypeStrs[record.rztype - 1] }}
        </template>
        <template v-if="column.key === 'winOrLose'">
          {{ winStrs[record.winOrLose - 1] }}
        </template>
        <template v-if="column.key === 'rzstatus'">
          <span v-if="record.rzstatus == 0" style=";">未申请</span>
          <span v-if="record.rzstatus == 1" style="color: red;">已提交</span>
          <span v-if="record.rzstatus == 2" style="color: green;">认证成功</span>
          <span v-if="record.rzstatus == 3" style="">已驳回</span>
        </template>
        <template v-if="column.key === 'action'">
          <div>
            <a-tooltip @click="openEdit(record)">
              <template #title>编辑</template>
              <FormOutlined />
            </a-tooltip>
            <a-divider type="vertical" />
            <a-tooltip @click="() => {
              isOpenSendMsg = true;
              sendMsgData.type = 1;
              sendMsgData.id = record.id;
            }">
              <template #title>发通知</template>
              <MessageOutlined />
            </a-tooltip>
            <a-divider type="vertical" />
            <span v-privilege="'system:user:updatekj'">
              <a-tooltip @click="openEditKJ(record)">
                <template #title>矿机单控</template>
                <UngroupOutlined />
              </a-tooltip>
              <a-divider type="vertical" />
            </span>
            <span v-privilege="'system:user:win'">
              <a-tooltip @click="() => {
                isOpenEditFK = true;
                addOrEditDataFK = record;
              }">
                <template #title>用户风控</template>
                <ThunderboltOutlined />
              </a-tooltip>
            </span>
            <!-- <a v-if="record.isAgent == 0" @click="setAgent(record.id)"><a-divider type="vertical" />设为代理</a> -->
            <!-- <div style="height: 10px;"></div>
            <a @click="setWin(record.id, 2)">指定必输</a>
            <a-divider type="vertical" />
            <a @click="setWin(record.id, 1)">指定必赢</a>
            <a-divider type="vertical" />
            <a @click="setWin(record.id, 3)">正常交易</a> -->
          </div>
        </template>
        <template v-if="column.key === 'action1'">
          <div>
            <span v-privilege="'system:user:money'">
              <a-tooltip @click="() => {
                isOpenUserMoney = true;
                userMoneyData.id = record.id;
              }">
                <template #title>资产</template>
                <DollarCircleOutlined />
              </a-tooltip>
              <a-divider type="vertical" />
            </span>
            <a-tooltip @click="() => {
              isOpenEditJY = true;
              addOrEditDataJY = record;
            }">
              <template #title>交易</template>
              <SwapOutlined />
            </a-tooltip>
            <a-divider type="vertical" />
            <!-- <a v-if="record.buyOn == 1" @click="setBuy(record.id, 2)" style="color: red;">禁止交易</a>
            <a v-if="record.buyOn == 2" @click="setBuy(record.id, 1)">允许交易</a>
            <a-divider type="vertical" /> -->
            <a-tooltip @click="() => {
              isOpenEditTB = true;
              addOrEditDataTB = record;
            }">
              <template #title>提币</template>
              <CopyrightCircleOutlined />
            </a-tooltip>
            <a-divider type="vertical" />
            <!-- <a v-if="record.txstate == 1" @click="setUser(record.id, 4)" style="color: rgba(89, 0, 255, 0.623);">禁止提币</a>
            <a v-if="record.txstate == 2" @click="setUser(record.id, 3)">允许提币</a>
            <a-divider type="vertical" /> -->
            <a-tooltip @click="() => {
              isOpenEditZH = true;
              addOrEditDataZH = record;
            }">
              <template #title>账号</template>
              <UserOutlined />
            </a-tooltip>
            <!-- <a-divider type="vertical" />
            <a v-if="record.status == 2" @click="() => {
              setUser(record.id, 2)
            }">解冻</a>
            <a v-if="record.status == 1" style="color: red;" @click="() => {
              setUser(record.id, 1)
            }
              ">冻结</a> -->
          </div>
        </template>
      </template>
    </a-table>
  </a-card>
  <!-- 编辑弹框 -->
  <UserEdit v-if="isOpenEdit" ref="addOrEditRef" :isOpen="isOpenEdit" :formItems="formItems" :type="addOrEditType"
    :name="'会员'" :data="addOrEditData" @close="() => {
      isOpenEdit = false;
    }
      " @submit="addOrEditSubmit" />
  <!-- 群发 -->
  <AddOrEdit v-if="isOpenSendMsg" ref="sendMsgRef" :isOpen="isOpenSendMsg" :formItems="sendMsgFormItems"
    :type="sendMsgType" :name="'通知'" :isALlName="true" :data="sendMsgData" @close="() => {
      isOpenSendMsg = false;
    }
      " @submit="sendMsgSubmit" />
  <!-- 操作资产 -->
  <AddOrEdit v-if="isOpenUserMoney" ref="userMoneyRef" :isOpen="isOpenUserMoney" :formItems="userMoneyFormItems"
    :type="userMoneyType" :name="'资产'" :isALlName="true" :data="userMoneyData" @close="() => {
      isOpenUserMoney = false;
    }
      " @submit="userMoneySubmit" />
  <!-- kj单控 -->
  <KJTableEdit v-if="isOpenEditKJ" ref="addOrEditRefKJ" :isOpen="isOpenEditKJ" :data="addOrEditDataKJ.twUserKuangji"
    @close="() => {
      isOpenEditKJ = false;
    }
      " @submit="editKJSubmit" />
  <!-- <KJEdit v-if="isOpenEditKJ" ref="addOrEditRefKJ" :isOpen="isOpenEditKJ" :formItems="userKJFormItems"
    :data="addOrEditDataKJ" @close="() => {
      isOpenEditKJ = false;
    }
      " @submit="editKJSubmit" /> -->
  <!-- 交易控制 -->
  <JYEdit v-if="isOpenEditJY" ref="addOrEditRefJY" :isOpen="isOpenEditJY" :formItems="[]" :data="addOrEditDataJY" @close="() => {
    isOpenEditJY = false;
  }
    " @submit="editJYSubmit" />
  <!-- 提币控制 -->
  <TBEdit v-if="isOpenEditTB" ref="addOrEditRefTB" :isOpen="isOpenEditTB" :formItems="[]" :data="addOrEditDataTB" @close="() => {
    isOpenEditTB = false;
  }
    " @submit="editTBSubmit" />
  <!-- 账号控制 -->
  <ZHEdit v-if="isOpenEditZH" ref="addOrEditRefZH" :isOpen="isOpenEditZH" :formItems="[]" :data="addOrEditDataZH" @close="() => {
    isOpenEditZH = false;
  }
    " @submit="editZHSubmit" />
  <!-- 用户风控 -->
  <FKEdit v-if="isOpenEditFK" ref="addOrEditRefFK" :isOpen="isOpenEditFK" :formItems="[]" :data="addOrEditDataFK" @close="() => {
    isOpenEditFK = false;
  }
    " @submit="editFKSubmit" />
</template>
<script setup lang="ts">
import { message } from 'ant-design-vue';
import { h, ref, onMounted } from 'vue';
import { PlusCircleOutlined, UndoOutlined, CommentOutlined, DollarCircleOutlined, MessageOutlined, FormOutlined, SwapOutlined, CopyrightCircleOutlined, UngroupOutlined, UserOutlined, ThunderboltOutlined } from '@ant-design/icons-vue';
import { userApi } from '/@/api/business/admin/user-api';
import { agentApi } from '/@/api/business/admin/agent-api';
import AddOrEdit from "/@/components/edit/edit.vue"
import KJEdit from "./components/kj/kj.vue"
import KJTableEdit from "./components/kjtable/kjtable.vue"
import JYEdit from "./components/jy/jy.vue"
import TBEdit from "./components/tb/tb.vue"
import ZHEdit from "./components/zh/zh.vue"
import FKEdit from "./components/fk/fk.vue"
import UserEdit from "./components/edit/edit.vue"
import { toLower, toUpper } from 'lodash';
import { timestampToStr } from '/@/utils/util';
const rztypeStrs = [
  '护照', '驾驶证', 'SSN', '身份ID '
];
const winStrs = [
  '盈利', '亏损', '正常'
];
const addOrEditRef = ref();
const isOpenEdit = ref(false);
const addOrEditType = ref(1);
const addOrEditData = ref({});
const sendMsgRef = ref();
const isOpenSendMsg = ref(false);
const sendMsgType = ref(1);
const sendMsgData = ref({} as any);
//kj
const isOpenEditKJ = ref(false);
const addOrEditDataKJ = ref({} as any);
const addOrEditRefKJ = ref();
//交易
const isOpenEditJY = ref(false);
const addOrEditDataJY = ref({});
const addOrEditRefJY = ref();
//提币
const isOpenEditTB = ref(false);
const addOrEditDataTB = ref({});
const addOrEditRefTB = ref();
//账号
const isOpenEditZH = ref(false);
const addOrEditDataZH = ref({});
const addOrEditRefZH = ref();
//风控
const isOpenEditFK = ref(false);
const addOrEditDataFK = ref({});
const addOrEditRefFK = ref();

const userMoneyRef = ref();
const isOpenUserMoney = ref(false);
const userMoneyType = ref(2);
const userMoneyData = ref({} as any);

//表格数据
const tableData = ref([] as any[]);
//分页数据
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10
});
const searchUserName = ref('');
//用户余额配置
const userMoneyFormItems = [{
  name: "money",
  label: "金额",
  placeholder: '请输入变动金额',
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
  label: "方法",
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
    { name: "增加", value: 1 },
    { name: "减少", value: 2 }
  ]
}
  // , {
  //   name: "bz",
  //   label: "币种",
  //   placeholder: '',
  //   type: "select",
  //   defaultValue: 1,
  //   rules: [
  //     {
  //       required: true,
  //       message: '必填选项',
  //     },
  //   ],
  //   selects: [
  //     { name: "USDT", value: 1 },
  //     { name: "TRX", value: 2 }
  //   ]
  // }
];

//矿机单控
const userKJFormItems = [
  {
    name: "kjNum",
    label: "矿机购买次数",
    placeholder: '请输入数字',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: true,
        message: '必填选项',
      },
    ]
  },
  {
    name: "kjMinnum",
    label: "矿机最低单价",
    placeholder: '请输入数字',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: true,
        message: '必填选项',
      },
    ]
  }, {
    name: "kjMaxnum",
    label: "矿机最高单价",
    placeholder: '请输入数字',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: true,
        message: '必填选项',
      },
    ]
  }
]
//发送短信配置
const sendMsgFormItems = [{
  name: "title",
  label: "通知标题",
  placeholder: '请输入通知标题',
  type: "textarea",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "content",
  label: "通知内容",
  placeholder: '请输入手机号',
  type: "textarea",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}
];
//编辑配置
const formItems = [{
  name: "username",
  label: "账号",
  placeholder: '请输入账号',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}
// , {
//   name: "userCode",
//   label: "用户ID",
//   placeholder: '请输入用户ID',
//   type: "input",
//   defaultValue: '',
//   rules: [
//     {
//       required: true,
//       message: '必填选项',
//     },
//   ]
// }
, {
  name: "phone",
  label: "手机号",
  placeholder: '请输入手机号',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "jifen",
  label: "信用积分",
  placeholder: '请输入信用积分',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: true,
      message: '必填选项',
    },
  ]
}, {
  name: "password",
  label: "用户密码",
  placeholder: '空则不更新',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: false,
      message: '必填选项',
    },
  ]
}, {
  name: "paypassword",
  label: "交易密码",
  placeholder: '空则不更新',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: false,
      message: '必填选项',
    },
  ]
}, {
  name: "invit",
  label: "邀请码",
  placeholder: '请输入邀请码',
  type: "input",
  defaultValue: '',
  rules: [
    {
      required: false,
      message: '必填选项',
    },
  ]
},
{
  name: "rztype",
  label: "身份类型",
  placeholder: '',
  type: "select",
  defaultValue: '',
  rules: [
    {
      required: false,
      message: '必填选项',
    },
  ],
  selects: [
    { name: "护照", value: 1 },
    { name: "驾驶证", value: 2 },
    { name: "SSN", value: 3 },
    { name: "身份ID", value: 4 }
  ]
},
{
  name: "cardzm",
  label: "证件正面",
  placeholder: '',
  type: "image",
  defaultValue: '',
  rules: [
    {
      required: false,
      message: '必填选项',
    },
  ]
},
{
  name: "cardfm",
  label: "证件反面",
  placeholder: '',
  type: "image",
  defaultValue: '',
  rules: [
    {
      required: false,
      message: '必填选项',
    },
  ]
}
  , {
  name: "status",
  label: "封存账号",
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
  name: "txstate",
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
    { name: "正常", value: 1 },
    { name: "禁止", value: 2 }
  ]
}, {
  name: "buyOn",
  label: "交易状态",
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
    { name: "允许交易", value: 1 },
    { name: "禁止交易", value: 2 }
  ]
}, {
  name: "userType",
  label: "身份标识",
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
    { name: "正常用户", value: 1 },
    { name: "测试用户", value: 2 }
  ]
}
];
//行配置
const columns = [
  // {
  //   name: 'id',
  //   title: 'ID',
  //   dataIndex: 'id',
  //   key: 'id',
  //   width: 70
  // },
  {
    title: '用户ID',
    dataIndex: 'userCode',
    key: 'userCode',
    width: 150
  },
  {
    title: '手机号',
    dataIndex: 'phone',
    key: 'phone',
    width: 120
  },
  {
    title: '用户名',
    dataIndex: 'username',
    key: 'username',
    width: 150
  },
  {
    title: '真实姓名',
    dataIndex: 'realName',
    key: 'realName',
    width: 100
  }, {
    title: '身份标识',
    dataIndex: 'userType',
    key: 'userType',
    width: 100
  }, {
    title: '封存账号',
    dataIndex: 'status',
    key: 'status',
    width: 100
  }, {
    title: '注册时间',
    dataIndex: 'addtime',
    key: 'addtime',
    width: 120
  }, {
    title: '信息审核',
    dataIndex: 'rztype',
    key: 'rztype',
    width: 100
  }, {
    title: '用户风控',
    dataIndex: 'winOrLose',
    key: 'winOrLose',
    width: 100
  },
  {
    title: '用户总余额',
    dataIndex: 'money',
    key: 'money',
    width: 120
  },
  {
    title: '邀请码',
    dataIndex: 'invit',
    key: 'invit',
    width: 100
  },
  {
    title: '上级代理',
    dataIndex: 'path',
    key: 'path',
    width: 150
  },
  {
    title: '资产操作',
    key: 'action1',
    width: 150,
    fixed: 'right',
  },
  {
    title: '操作',
    key: 'action',
    width: 150,
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
  record.password = '';
  record.paypassword = '';
  isOpenEdit.value = true;
  addOrEditType.value = 2;
  addOrEditData.value = record;
}

//打开编辑
async function openEditKJ(record) {
  //处理数据
  isOpenEditKJ.value = true;
  addOrEditDataKJ.value = record;
}

//设置输赢
async function setWin(id, type) {
  let data = await userApi.setWin({ id, uid: id, type });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//交易
async function editJYSubmit(data) {
  setBuy(data.id, data.buyOn);
  isOpenEditJY.value = false;
}
//提币
async function editTBSubmit(data) {
  if (data.txstate == 1) {
    setUser(data.id, 3);
  }
  if (data.txstate == 2) {
    setUser(data.id, 4);
  }
  isOpenEditTB.value = false;
}
//账号
async function editZHSubmit(data) {
  if (data.status == 1) {
    setUser(data.id, 2);
  }
  if (data.status == 2) {
    setUser(data.id, 1);
  }
  isOpenEditZH.value = false;
}
//风控
async function editFKSubmit(data) {
  setWin(data.id, data.winOrLose);
  isOpenEditFK.value = false;
}
//设置交易
async function setBuy(id, buyOn) {
  let data = await userApi.setBuy({ id, uid: id, buyOn });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//设置用户信息
async function setUser(id, type) {
  let data = await userApi.setUser({ id, uid: id, type });
  if (data.ok) {
    message.success("操作成功！")
  } else {
    message.error("操作失败！请重试~")
  }
  loadData();
}
//设置为代理
async function setAgent(id) {
  let data = await agentApi.setAgent({ id, isAgent: 1 });
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
  let data = await userApi.addOrUpdate(submitData);
  if (data.ok) {
    message.success("操作成功！");
    addOrEditRef.value.close();
  } else {
    message.error(data.msg);
  }
  loadData();
}
//用户余额
async function userMoneySubmit(submitData) {
  submitData.uid = submitData.id;
  let data = await userApi.setMoney(submitData);
  if (data.ok) {
    message.success("操作成功！");
    userMoneyRef.value.close();
  } else {
    message.error("操作失败！请重试~");
  }
  loadData();
}

//kg单控
async function editKJSubmit(submitData) {
  let data = await userApi.updatekj(submitData);
  if (data.ok) {
    message.success("操作成功！");
    if (!submitData.noClose) {
      addOrEditRefKJ.value.close();
    }
  } else {
    message.error("操作失败！请重试~");
  }
  loadData();
}

//发送通知
async function sendMsgSubmit(submitData) {
  submitData.imgs = " ";

  if (submitData.type == 2) {
    userApi.userNotice(submitData);
    message.success("异步执行中！");
    sendMsgRef.value.close();
    return;
  }
  let data = await userApi.userNotice(submitData);
  if (data.ok) {
    message.success("操作成功！");
    sendMsgRef.value.close();
  } else {
    message.error("操作失败！请重试~");
  }
  loadData();
}
//获取表格数据
async function loadData() {
  let data = await userApi.list({ pageNum: pagination.value.current, pageSize: pagination.value.pageSize, userCode: searchUserName.value });
  if (data.ok) {
    data = data.data;
    tableData.value = data.records;
    pagination.value.total = data.total;
    //更新二级列表
    if (addOrEditDataKJ.value?.id) {
      for (let index = 0; index < tableData.value.length; index++) {
        const d = tableData.value[index];
        if (d.id == addOrEditDataKJ.value?.id) {
          addOrEditDataKJ.value = d;
        }

      }
    }
  }
}
onMounted(() => {
  loadData()
});
</script>
<style lang="less" scoped>
@import './index.less';
</style>
