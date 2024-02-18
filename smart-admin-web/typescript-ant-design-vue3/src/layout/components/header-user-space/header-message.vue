<!--
  * 消息通知
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-09-06 20:17:18 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->

<template>
  <a-dropdown trigger="click" v-model:visible="show">
    <div @click="fetchMessage">
      <a-badge :count="(message&&message!={}) ? message?.authCount + message?.myzcCount + message?.rechargeCount : 0">
        <div style="width: 26px; height: 26px">
          <BellOutlined :style="{ fontSize: '18px' }" />
        </div>
      </a-badge>
    </div>

    <template #overlay>
      <div>d
        <a-spin :spinning="loading">
          <a-tabs class="dropdown-tabs" centered :tabBarStyle="{ textAlign: 'center' }" style="width: 200px">
            <a-tab-pane tab="通知" key="1">
              <div class="messageDiv" @click="() => {
                router.push({ path: '/finance/recharge' })
              }">
                <a-badge :showZero="true" :count="message ? message?.rechargeCount : 0">
                  <div class="messageInfoDiv">入款</div>
                </a-badge>
              </div>
              <div class="messageDiv" @click="() => {
                router.push({ path: '/finance/extract' })
              }">
                <a-badge :showZero="true" :count="message ? message?.myzcCount : 0">
                  <div class="messageInfoDiv">出款</div>
                </a-badge>
              </div>
              <div class="messageDiv" @click="() => {
                router.push({ path: '/user/auth' })
              }">
                <a-badge :showZero="true" :count="message ? message?.authCount : 0">
                  <div class="messageInfoDiv">认证</div>
                </a-badge>
              </div>
            </a-tab-pane>
          </a-tabs>
        </a-spin>
      </div>
    </template>
  </a-dropdown>
  <audio hidden ref="audioMessage" src="/message.mp3" />
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue';
import { BellOutlined } from '@ant-design/icons-vue';
import { adminlogApi } from '/@/api/business/admin/adminLog-api';
import { useRouter } from 'vue-router';
defineExpose({ showMessage });
const message = ref({} as any);
const router = useRouter();
const audioMessage = ref();
let timer;
//分页数据
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 20
});

function showMessage() {
  show.value = true;
}

const loading = ref(false);
const show = ref(false);

const fetchMessage = () => {
  if (loading.value) {
    loading.value = false;
    return;
  }
  loading.value = true;
  setTimeout(() => {
    loading.value = false;
  }, 700);
};

//获取表格数据
async function loadData() {
  let data = await adminlogApi.message({
    pageNum: pagination.value.current, pageSize: pagination.value.pageSize
  });
  if (data.ok) {
    data = data.data;
    message.value = data;
    if (data.authCount + data.myzcCount + data.rechargeCount > 0) {
      audioMessage.value.play();
    }
  }
}
onMounted(() => {
  loadData();
  timer = setInterval(() => {
    loadData();
  }, 10000);
});
// 在组件即将卸载时触发的钩子
onBeforeUnmount(() => {
  clearInterval(timer);
});
</script>

<style lang="less">
.header-notice {
  display: inline-block;
  transition: all 0.3s;

  span {
    vertical-align: initial;
  }

  .notice-badge {
    color: inherit;
  }
}

.dropdown-tabs {
  background-color: @base-bg-color;
  box-shadow: 0 2px 8px @shadow-color;
  border-radius: 4px;
}

.messageDiv {
  text-align: center;
  margin: 15px 0;
  cursor: pointer;
}

.messageInfoDiv {
  width: 4em;
}

.messageInfoDiv:hover {
  color: #1890ff;
}
</style>
