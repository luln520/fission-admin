<template>
    <a-modal width="1000px" :destroyOnClose="true" :visible="props.isOpen" :open="props.isOpen" title="矿机列表"
        @ok="addHandleOk" @cancel="close">
        <a-card style="height: 10%;min-height: 30px;margin:0">
            <a-space>
                <a-form>
                    <a-row>
                        <a-col :span="12">
                            <a-form-item label="最高投资额度" name="max" :rules="[{ required: false, message: '请输入最高投资额度' }]">
                                <a-input v-model:value="max" />
                            </a-form-item>
                        </a-col>
                        <a-col :span="12">
                            <a-form-item label="最低投资额度" name="min" :rules="[{ required: false, message: '请输入最低投资额度' }]">
                                <a-input v-model:value="min" />
                            </a-form-item>
                        </a-col>
                    </a-row>
                </a-form>
            </a-space>
        </a-card>
        <a-table :columns="columns" :data-source="props.data" @change="tableChange" :scroll="{ x: 900 }"
            :pagination="pagination" :bordered="true">
            <template #bodyCell="{ column, record }">
                <template v-if="column.key === 'action'">
                    <div @click="() => {
                        tempData = record;
                        open = true;
                    }">
                        <a-tooltip>
                            <template #title>次数</template>
                            <FormOutlined />
                        </a-tooltip>

                    </div>
                </template>
            </template>
        </a-table>
    </a-modal>
    <!-- 数量 -->
    <a-modal :visible="open" v-model:open="open" title="购买次数" @cancel="() => {
        open = false;
    }" @ok="addHandleOk2">
        <a-space>
            <a-form>
                <a-row>
                    <a-col :span="22">
                        <a-form-item label="最高购买次数" name="num" :rules="[{ required: false, message: '请输入最高购买次数' }]">
                            <a-input v-model:value="tempData.num" />
                        </a-form-item>
                    </a-col>
                </a-row>
            </a-form>
        </a-space>
    </a-modal>
</template>
<script setup lang="ts">
import { h, ref, shallowRef, computed, defineExpose } from 'vue';
import { PlusCircleOutlined, UndoOutlined, CommentOutlined, DollarCircleOutlined, FormOutlined } from '@ant-design/icons-vue';
const props = defineProps(["isOpen", "type", "data", "name", "formItems"]);
const emit = defineEmits(["close", "submit"]);
// const tableData = ref(props.data);
const open = ref(false);
const max = ref(props.data[0].max);
const min = ref(props.data[0].min);
const tempData = ref({} as any);
//分页数据
const pagination = ref({
    total: 0,
    current: 1,
    pageSize: 5
});
//行配置
const columns = [
    {
        name: 'id',
        title: 'ID',
        dataIndex: 'id',
        key: 'id',
        width: 70
    },
    {
        title: '矿机id',
        dataIndex: 'kjId',
        key: 'kjId',
        width: 70
    },
    {
        title: '矿机名称',
        dataIndex: 'kjName',
        key: 'kjName',
        width: 100
    },
    {
        title: '最低投资额度',
        dataIndex: 'min',
        key: 'min',
        width: 100
    }, {
        title: '最高投资额度',
        dataIndex: 'max',
        key: 'max',
        width: 100
    }, {
        title: '最高购买次数',
        dataIndex: 'num',
        key: 'num',
        width: 80
    }, {
        title: '创建时间',
        dataIndex: 'createTime',
        key: 'createTime',
        width: 80
    },
    {
        title: '操作',
        key: 'action',
        width: 50,
    },
];
//分页方法
async function tableChange(pag: { pageSize: number; current: number }) {
    pagination.value.pageSize = pag.pageSize;
    pagination.value.current = pag.current;
    pagination.value.total = props.data.length;
    // tableData.value = props.data.slice((pag.current - 1) * pag.pageSize, pag.current * pag.pageSize);
}
//点击确认
async function addHandleOk() {
    emit("submit", { ...props.data[0], min: min.value, max: max.value, noClose: false });
}
//点击确认
async function addHandleOk2() {
    emit("submit", { ...tempData.value, min: min.value, max: max.value, noClose: true });
    open.value=false;
    // tableData.value = props.data;
}
//窗口关闭
async function close() {
    emit("close");
}
//窗口关闭
async function openNum() {
    open.value = true;
}
//这里需要暴露出去不然父组件调用不到这个方法
defineExpose({
    close
});
</script>
<style lang="less" scoped></style>
