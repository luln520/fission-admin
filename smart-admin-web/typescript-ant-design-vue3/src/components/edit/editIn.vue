<template>
    <a-form ref="dataFormRef" :label-col="labelCol" :model="dataModel">
        <div v-for="(formItem, index) in formItems" :key="index" style="min-height: 50px;overflow: hidden;">
            <div style="width: 40%;float: left;min-height: 50px;">
                <a-form-item v-if="formItem" :name="formItem.name" :label="formItem.label" :rules="formItem.rules">
                    <!-- 判断控件类型 -->
                    <!-- input -->
                    <a-input v-if="formItem.type == 'input'" v-model:value="dataModel[formItem.name]" allow-clear
                        width="500px" autocomplete="off" :defaultValue="formItem.defaultValue" />
                    <!-- textarea -->
                    <a-textarea v-if="formItem.type == 'textarea'" v-model:value="dataModel[formItem.name]"
                        :rows="formItem.rows" />
                    <!-- select -->
                    <a-select v-if="formItem.type == 'select'" v-model:value="dataModel[formItem.name]">
                        <a-select-option v-for="(selectsItem, selectsIndex) in formItem.selects"
                            :key="`selectsItem${selectsIndex}`" :value="selectsItem.value">{{ selectsItem.name
                            }}</a-select-option>
                    </a-select>
                    <!-- image -->
                    <div v-if="formItem.type == 'image'"
                        style="padding: 10px;background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASAAAAEgCAIAAACb4TnXAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA0ZJREFUeNrs28FpxEAABMGTU1L+GUgx6RIQxmCG/lQ97+bb7ILY43mez5v7vl9/P8/T3t7+j/ufDzAjMBAYCAwQGAgMBAb87riu6/UP3zfs7f+/d4KBKyIIDBAYCAwEBggMMof3YPb2u70TDFwRQWCAwEBgIDBAYJDxHszefrh3goErIggMEBgIDAQGCAwy3oPZ2w/3TjBwRQSBAQIDgYHAAIFBxnswe/vh3gkGroggMEBgIDAQGCAwyHgPZm8/3DvBwBURBAYIDAQGAgMEBhnvwezth3snGLgigsAAgYHAQGCAwCDjPZi9/XDvBANXRBAYIDAQGAgMEBhkvAeztx/unWDgiggCAwQGAgOBAQKDjPdg9vbDvRMMXBFBYIDAQGAgMEBgkPEezN5+uHeCgSsiCAwQGAgMBAYIDDLeg9nbD/dOMHBFBIEBAgOBgcAAgUHGezB7++HeCQauiCAwQGAgMBAYIDDIeA9mbz/cO8HAFREEBggMBAYCAwQGGe/B7O2HeycYuCKCwACBgcBAYIDAIOM9mL39cO8EA1dEEBggMBAYCAwQGGS8B7O3H+6dYOCKCAIDBAYCA4EBAoOM92D29sO9EwxcEUFggMBAYCAwQGCQ8R7M3n64d4KBKyIIDBAYCAwEBggMMt6D2dsP904wcEUEgQECA4GBwACBQcZ7MHv74d4JBq6IIDBAYCAwEBggMMh4D2ZvP9w7wcAVEQQGCAwEBgIDBAYZ78Hs7Yd7Jxi4IoLAAIGBwEBggMAg4z2Yvf1w7wQDV0QQGCAwEBgIDBAYZLwHs7cf7p1g4IoIAgMEBgIDgQECg4z3YPb2w70TDFwRQWCAwEBgIDBAYJDxHszefrh3goErIggMEBgIDAQGCAwy3oPZ2w/3TjBwRQSBAQIDgYHAAIFBxnswe/vh3gkGroggMEBgIDAQGCAwyHgPZm8/3DvBwBURBAYIDAQGAgMEBhnvwezth3snGLgigsAAgYHAQGCAwCDjPZi9/XDvBANXRBAYIDAQGAgMEBhkvAeztx/unWDgiggCAwQGAgOBAQKDjPdg9vbDvRMMXBFBYIDAQGAgMEBgkPEezN5+uHeCgSsiCAwQGAgMBAYIDDJfAQYAESDFWPMcwB4AAAAASUVORK5CYII=);">
                        <!-- 目标元素隐藏 文本 -->
                        <a-input v-model:value="dataModel[formItem.name]" style="display: none;" />
                        <a-upload v-model:file-list="fileList" name="file" accept="image/*" :data="{
                            folder: 1
                        }" action="http://206.238.199.169:1024/api/admin/file/upload" :headers="headers"
                            :dataName="formItem.name" @change="handleChange">
                            <a-image height="60px" :preview="false" @click="preview(formItem.name)"
                            :src="dataModel[formItem.name]?dataModel[formItem.name]:'xxx'"
                                fallback="data:image/png;base64,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" />
                        </a-upload>
                    </div>
                </a-form-item>
            </div>
            <div v-show="formItem.placeholder" style="width: 50%;float: left;height: 50px;color: red;padding-left: 20px;">
                *{{ formItem.placeholder }}
            </div>
        </div>
    </a-form>
    <div style="padding-left: 200px;">
        <a-button type="primary" @click="submit">提交</a-button>
    </div>
</template>
<script setup lang="ts">
import { ref, shallowRef } from 'vue'
import { message } from 'ant-design-vue';
import { UploadChangeParam } from 'ant-design-vue/es/upload/interface';
const props = defineProps(["data", "formItems"]);
const emit = defineEmits(["submit"]);
const dataFormRef = shallowRef();
const dataModel = ref(props.data);
const labelCol = { style: { width: '200px' } };
const formItems = props.formItems;
const fileList = ref([]);
const fileNowName = ref("");
const headers = {
    authorization: 'authorization-text',
};
//点击
const preview = (name) => {
    fileNowName.value = name;
}
const handleChange = (info: UploadChangeParam) => {
    if (info.file.status === 'done') {
        if (info.file.response && info.file.response.ok) {
            message.success(`${info.file.name} 上传成功`);
            const data=info.file.response;
            //替换原来图片
            dataModel[fileNowName.value].value = data.fileUrl;
            fileList.value = [];
        } else {
            message.error(`上传失败`);
            fileList.value = [];
        }
    } else if (info.file.status === 'error') {
        message.error(`上传失败`);
        fileList.value = [];
    }
};
async function submit() {
    await dataFormRef.value?.validate();
    emit("submit", dataModel.value);
}
</script>
<style lang="less" scoped></style>
