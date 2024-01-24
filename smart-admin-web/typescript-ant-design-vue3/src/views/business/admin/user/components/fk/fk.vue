<template>
    <a-modal width="800px" :destroyOnClose="true" :visible="props.isOpen" :open="props.isOpen" title="用户风控"
        @ok="addHandleOk" @cancel="close">
        <a-form ref="dataFormRef" :label-col="labelCol" :model="dataModel">
            <a-form-item :name="'x'" :label="'用户风控'">
                <a-radio-group v-model:value="dataModel.winOrLose" :options="optionsWithDisabled" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script setup lang="ts">
import { ref, shallowRef, computed, defineExpose } from 'vue';
const props = defineProps(["isOpen", "type", "data", "name", "formItems"]);
const emit = defineEmits(["close", "submit"]);
const dataFormRef = shallowRef();
const dataModel = ref(props.data);
const formItems = props.formItems;
const labelCol = { style: { width: '120px' } };
const optionsWithDisabled = [
    { label: '盈利', value: 1 },
    { label: '亏损', value: 2 },
    { label: '正常', value: 3 },
];
//点击确认
async function addHandleOk() {
    await dataFormRef.value?.validate();
    let data = {};
    if (props.data.id) {
        data = { ...dataModel.value, id: props.data.id };
    } else {
        data = { ...dataModel.value, id: props.data.i };
    }
    console.info(data);
    emit("submit", data);
}
//窗口关闭
async function close() {
    dataModel.value = {};
    emit("close");
}
//控件数据 计算属性
const formItemsNodes = computed(() => {
    const pairs = [] as any[];
    for (let i = 0; i < formItems.length; i += 2) {
        const item1 = formItems[i];
        const item2 = formItems[i + 1];
        pairs.push([item1, item2]);
    }
    return pairs;
});

//这里需要暴露出去不然父组件调用不到这个方法
defineExpose({
    close
});
</script>
<style lang="less" scoped></style>
