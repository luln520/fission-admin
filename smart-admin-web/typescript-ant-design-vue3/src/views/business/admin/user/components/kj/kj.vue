<template>
    <a-modal width="800px" :destroyOnClose="true" :visible="props.isOpen" :open="props.isOpen" title="矿机单控"
        @ok="addHandleOk" @cancel="close">
        <a-form ref="dataFormRef" :label-col="labelCol" :model="dataModel">
            <a-row v-for="(item, index) in formItemsNodes" :key="index">
                <a-col v-for="(formItem, formItemIndex) in item" :key="'formItem' + formItemIndex + '' + index" :span="11">
                    <a-form-item v-if="formItem" :name="formItem.name" :label="formItem.label" :rules="formItem.rules">
                        <!-- 判断控件类型 -->
                        <!-- input -->
                        <a-input v-if="formItem.type == 'input'" v-model:value="dataModel[formItem.name]" allow-clear
                            autocomplete="off" :placeholder="formItem.placeholder" :defaultValue="formItem.defaultValue" />
                        <!-- textarea -->
                        <a-textarea v-if="formItem.type == 'textarea'" v-model:value="dataModel[formItem.name]"
                            :rows="formItem.rows" :placeholder="formItem.defaultValue" />
                        <!-- select -->
                        <a-select v-if="formItem.type == 'select'" v-model:value="dataModel[formItem.name]">
                            <a-select-option v-for="(selectItem, selectItemIndex) in formItem.selects"
                                :key="'selectItem' + selectItemIndex + 'formItem' + formItemIndex + '' + index"
                                :value="selectItem.value">
                                {{ selectItem.name }}
                            </a-select-option>
                        </a-select>
                    </a-form-item>
                </a-col>
            </a-row>
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
