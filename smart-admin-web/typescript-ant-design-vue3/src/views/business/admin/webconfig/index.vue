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
    name: "webname",
    label: "网站名称",
    placeholder: '请输入网站名称！',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "webtitle",
    label: "网站标题",
    placeholder: '请输入网站标题',
    type: "input",
    defaultValue: '',
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "weblogo",
    label: "手机端Logo图片",
    placeholder: '200*200px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "waplogo",
    label: "PC端logo图片",
    placeholder: '200*200px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "websildea",
    label: "手机端轮播图1",
    placeholder: '700*350px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "websildeb",
    label: "手机端轮播图2",
    placeholder: '700*350px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "websildec",
    label: "手机端轮播图3",
    placeholder: '700*350px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "webissue",
    label: "手机端新币认购图片",
    placeholder: '700*350px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "webkj",
    label: "手机端矿机首页图片",
    placeholder: '700*350px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "wapsildea",
    label: "PC端轮播图1",
    placeholder: '700*350px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "wapsildb",
    label: "PC端轮播图2",
    placeholder: '700*350px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "wapsildec",
    label: "PC端轮播图3",
    placeholder: '700*350px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "wapsilded",
    label: "PC端轮播图4",
    placeholder: '700*350px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "wapissue",
    label: "PC端新币认购图片",
    placeholder: '700*350px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "wapkj",
    label: "PC端矿机首页图片",
    placeholder: '700*350px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "webtjimgs",
    label: "手机端推荐页面logo图片",
    placeholder: '200*200px',
    type: "image",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ]
  }, {
    name: "webswitch",
    label: "网站状态",
    placeholder: '请选择网站状态',
    type: "select",
    rules: [
      {
        required: false,
        message: '必填选项',
      },
    ],
    selects: [
      { name: "开", value: 1 },
      { name: "关", value: 2 }
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
