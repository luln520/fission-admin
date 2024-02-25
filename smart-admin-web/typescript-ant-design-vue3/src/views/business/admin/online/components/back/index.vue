<template>
  <a-modal width="800px" :destroyOnClose="true" :visible="props.isOpen" :open="props.isOpen" :maskClosable="false"
    :title="`在线客服（${props.id}用户——${props.username}）`" @ok="close" cancelText=' ' okText="关闭" @cancel="close">
    <!-- 聊天框 -->
    <div id="scrollDiv" style="height: 500px;overflow-y: scroll;border: #d8d8d8 2px solid;padding: 5px;">
      <div v-for="(item, index) in    msgData   " :key="index">
        <div style="">
          <div v-if="item.type == 1" style="border-radius: 5px;width: 70%;float: right;margin-bottom: 10px;">
            <div style="float: right;padding: 10px;background-color: #d8d8d8;border-radius: 5px;">
              <a-image v-if="item.content.startsWith('image:')"
                :src="imageConfig.baseImageUrl + (item.content.replace('image:', ''))" :width="100" />
            </div>
            <div v-if="!item.content.startsWith('image:')"
              style="background-color: #d8d8d8;border-radius: 5px;padding:5px ;">
              {{ item.content }}
            </div>
          </div>
          <div v-if="item.type == 2" style="border-radius: 5px;width: 70%;float: left;margin-bottom: 10px;">
            <div style="float: left;padding: 10px;background-color: #d8d8d8;border-radius: 5px;">
              <a-image v-if="item.content.startsWith('image:')"
                :src="imageConfig.baseImageUrl + (item.content.replace('image:', ''))" :width="100" />
            </div>
            <div v-if="!item.content.startsWith('image:')"
              style="background-color: #d8d8d8;border-radius: 5px;padding:5px ;">
              {{ item.content }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <div style="margin-top: 5px;height: 50px;">
      <a-input-search v-model:value="msg" placeholder="请输入回复内容！！" enter-button="发送" size="large" @search="sendMsg">
        <template #suffix>
          <a-upload v-model:file-list="fileList" name="file" accept="image/*" :action="imageConfig.uploadUrl"
            :headers="headers" @change="handleChange">
            <PlusCircleOutlined style="font-size: 23px;" />
          </a-upload>
        </template>
      </a-input-search>
    </div>
  </a-modal>
</template>
<script setup lang="ts">
import { message, UploadChangeParam } from 'ant-design-vue';
import { h, ref, onMounted, onBeforeMount } from 'vue';
import { PlusCircleOutlined } from '@ant-design/icons-vue';
import { onlineApi } from '/@/api/business/admin/online-api';
import { imageConfig } from '/@/config/app-config';
const props = defineProps(["id", "username", "isOpen"]);
const emit = defineEmits(["close", "submit"]);
const msgData = ref([] as any);
const msg = ref("");
const timerId = ref();
const fileList = ref([]);
const length = ref(0);
const headers = {
  authorization: 'authorization-text',
};

const handleChange = (info: UploadChangeParam) => {
  console.info(info);
  if (info.file.status === 'done') {
    if (info.file.response && info.file.response.ok) {
      fileList.value = [];
      message.success(`${info.file.name} 上传成功`);
      const data = info.file.response;
      sendImgMsg(`image:${data.data}`);
    } else {
      message.error(`上传失败`);
      fileList.value = [];
    }
  } else if (info.file.status === 'error') {
    message.error(`上传失败`);
    fileList.value = [];
  }
};

//发送数据
async function sendMsg() {
  if (msg.value.length < 1) {
    message.error("请输入回复内容！");
    return;
  }
  let data = await onlineApi.backOnline({ uid: props.id, content: msg.value });
  if (data.ok) {
    message.success("发送成功！")
  } else {
    message.error("发送失败！请重试~")
  }
  msg.value = "";
  loadData();
}
async function sendImgMsg(msg) {
  let data = await onlineApi.backOnline({ uid: props.id, content: msg });
  if (data.ok) {
    message.success("发送成功！")
  } else {
    message.error("发送失败！请重试~")
  }
  loadData();
}
//获取表格数据
async function loadData() {
  let data = await onlineApi.getId({ uid: props.id });
  if (data.ok) {
    data = data.data;
    msgData.value = data;
  }
  let scrollDiv = document.getElementById("scrollDiv");
  //滚动
  if (length.value < msgData.value.length) {
    console.info(length.value, msgData.value.length);
    setTimeout(function () {
      // 滚动到底部
      scrollDiv.scrollTop = scrollDiv.scrollHeight;
    }, 0); // 假设1秒后加载内容
    length.value = msgData.value.length
  }
}

//窗口关闭
async function close() {
  clearInterval(timerId.value);
  emit("close");
}
//这里需要暴露出去不然父组件调用不到这个方法
defineExpose({
  close
});
onMounted(() => {
  loadData();
  timerId.value = setInterval(() => {
    loadData();
  }, 2000);
});
onBeforeMount(() => {
  // 在组件卸载前清除定时器
  clearInterval(timerId.value);
});
</script>
<style lang="less" scoped>
@import './index.less';
</style>
