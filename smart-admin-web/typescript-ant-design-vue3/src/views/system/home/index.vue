<!-- 首页 -->
<template>
  <a-row>
    <a-col :span="6">
      <a-card style="min-height: 100px;box-sizing: border-box;margin: 10px;">
        <div style="width: 80px;float: left;">
          <UserOutlined style="font-size: 50px;color: #4acea1;" />
        </div>
        <div style="width: 200px;float: left;">
          <div style="font-size: 20px;">{{ refData.allUser }}</div>
          <div style="color: #666666;">注册总人数（人）</div>
        </div>
      </a-card>
    </a-col>
    <a-col :span="6">
      <a-card style="min-height: 100px;box-sizing: border-box;margin: 10px;">
        <div style="width: 80px;float: left;">
          <MergeCellsOutlined style="font-size: 50px;color: #fa4b4c;" />
        </div>
        <div style="width: 200px;float: left;">
          <div style="font-size: 20px;">{{ refData.allHyOrders }}</div>
          <div style="color: #666666;">秒合约未平仓（条）</div>
        </div>
      </a-card>
    </a-col>
    <a-col :span="6">
      <a-card style="min-height: 100px;box-sizing: border-box;margin: 10px;">
        <div style="width: 80px;float: left;">
          <InteractionOutlined style="font-size: 50px;color: #ffdc3a;" />
        </div>
        <div style="width: 200px;float: left;">
          <div style="font-size: 20px;">{{ refData.allRecharge }}</div>
          <div style="color: #666666;">币币交易额度（USDT）</div>
        </div>
      </a-card>
    </a-col>
    <a-col :span="6">
      <a-card style="min-height: 100px;box-sizing: border-box;margin: 10px;">
        <div style="width: 80px;float: left;">
          <UngroupOutlined  style="font-size: 50px;color: #4b9afa;" />
        </div>
        <div style="width: 200px;float: left;">
          <div style="font-size: 20px;">{{ refData.allKjOrders }}</div>
          <div style="color: #666666;">全网有效矿机总数（台）</div>
        </div>
      </a-card>
    </a-col>
  </a-row>
  <a-row>
    <a-col :span="6">
      <a-card style="min-height: 100px;box-sizing: border-box;margin: 10px;">
        <div style="width: 80px;float: left;">
          <ShoppingOutlined  style="font-size: 50px;color: #4acea1;" />
        </div>
        <div style="width: 200px;float: left;">
          <div style="font-size: 20px;">{{ refData.allIssueLogs }}</div>
          <div style="color: #666666;">认购记录数（条）</div>
        </div>
      </a-card>
    </a-col>
    <a-col :span="6">
      <a-card style="min-height: 100px;box-sizing: border-box;margin: 10px;">
        <div style="width: 80px;float: left;">
          <LoginOutlined  style="font-size: 50px;color: #fa4b4c;" />
        </div>
        <div style="width: 200px;float: left;">
          <div style="font-size: 20px;">{{ refData.dayRecharge.toFixed(2) }} | {{ refData.allRecharge }}</div>
          <div style="color: #666666;">今日充值/充值总量（USDT）</div>
        </div>
      </a-card>
    </a-col>
    <a-col :span="6">
      <a-card style="min-height: 100px;box-sizing: border-box;margin: 10px;">
        <div style="width: 80px;float: left;">
          <LogoutOutlined  style="font-size: 50px;color: #ffdc3a;" />
        </div>
        <div style="width: 200px;float: left;">
          <div style="font-size: 20px;">{{ refData.dayWithdraw.toFixed(2) }} | {{ refData.allWithdraw }}</div>
          <div style="color: #666666;">今日提现/提币总量（USDT）</div>
        </div>
      </a-card>
    </a-col>
    <a-col :span="6">
      <a-card style="min-height: 100px;box-sizing: border-box;margin: 10px;">
        <div style="width: 80px;float: left;">
          <EyeOutlined  style="font-size: 50px;color: #4b9afa;" />
        </div>
        <div style="width: 200px;float: left;">
          <div style="font-size: 20px;">{{ refData.allLineUsers }}</div>
          <div style="color: #666666;">今日访客量（人）</div>
        </div>
      </a-card>
    </a-col>
  </a-row>
</template>
<script setup lang="ts">
import { onMounted } from 'vue';
import { homeApi } from '/@/api/business/admin/home-api';
import { ref } from 'vue'
const refData = ref({
  allKjOrders: 0,
  allRecharge: 0,
  dayWithdraw: 0,
  allHyOrders: 0,
  allWithdraw: 0,
  dayRecharge: 0,
  allUser: 0,
  allLineUsers: 0,
  allIssueLogs: 0
});
//获取数据
async function loadData() {
  let data = await homeApi.getHomeData();
  if (data.ok) {
    data = data.data;
    refData.value = data;
  }
}
onMounted(() => {
  loadData()
});
</script>
<style lang="less" scoped>
@import './index.less';
</style>
