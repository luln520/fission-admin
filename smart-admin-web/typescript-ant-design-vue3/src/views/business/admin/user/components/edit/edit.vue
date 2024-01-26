<template>
    <a-modal width="800px" :destroyOnClose="true" :visible="props.isOpen" :open="props.isOpen"
        :title="props.type == 1 ? `新增（${props.name}）` : `编辑（${props.name}-${props.data?.id}）`" @ok="addHandleOk"
        @cancel="close">
        <a-form ref="dataFormRef" :label-col="labelCol" :model="dataModel">
            <!-- 显示 -->
            <div v-if="props.type != 1">
                <a-divider orientation="left"><span style="color:#999999;font-size:15px">基本信息</span></a-divider>
                <div>
                    <a-row>
                        <a-col :span="8" style="padding-left:10px">ID：<span style="color:#999999;">{{ dataModel?.id
                        }}</span></a-col>
                        <a-col :span="8" style="padding-left:10px">身份状态：<span style="color:#999999;">{{
                            rztypeStrs[dataModel?.rztype] }}</span></a-col>
                        <a-col :span="8" style="padding-left:10px">登陆时间：<span style="color:#999999;">{{
                            dataModel?.logintime }}</span></a-col>
                    </a-row>
                    <a-row>
                        <a-col :span="8" style="padding-left:10px;margin-top:10px">注册IP ：<span style="color:#999999;">{{
                            dataModel?.addip
                        }}</span></a-col>
                        <a-col :span="8" style="padding-left:10px">区域：<span style="color:#999999;">{{
                            dataModel?.addr }}</span></a-col>
                        <a-col :span="8" style="padding-left:10px">邀请码：<span style="color:#999999;">{{
                            dataModel?.invit }}</span></a-col>
                        <a-col :span="8" style="padding-left:10px"></a-col>
                    </a-row>
                </div>
            </div>
            <!-- 信誉积分 -->
            <div>
                <a-divider orientation="left"><span style="color:#999999;font-size:15px">信誉积分</span></a-divider>
                <div>
                    <!-- 一行一个 -->
                    <a-row v-for="(formItem, index) in formItemsNodes[1]" :key="index">
                        <a-col :span="22">
                            <a-form-item v-if="formItem && (formItem.name === 'jifen')" :name="formItem.name"
                                :label="formItem.label" :rules="formItem.rules">
                                <!-- 判断控件类型 -->
                                <!-- input -->
                                <a-input v-if="formItem.type == 'input'" v-model:value="dataModel[formItem.name]"
                                    allow-clear autocomplete="off" :placeholder="formItem.placeholder"
                                    :defaultValue="formItem.defaultValue" />
                                <!-- textarea -->
                                <a-textarea v-if="formItem.type == 'textarea'" v-model:value="dataModel[formItem.name]"
                                    :rows="formItem.rows" :placeholder="formItem.defaultValue" />
                                <!-- select -->
                                <a-select v-if="formItem.type == 'select'" v-model:value="dataModel[formItem.name]">
                                    <a-select-option v-for="(selectItem, selectItemIndex) in formItem.selects"
                                        :key="'selectItem' + selectItemIndex + '' + index" :value="selectItem.value">
                                        {{ selectItem.name }}
                                    </a-select-option>
                                </a-select>
                                <!-- image -->
                                <div v-if="formItem.type == 'image'"
                                    style="padding: 10px;background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASAAAAEgCAIAAACb4TnXAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA0ZJREFUeNrs28FpxEAABMGTU1L+GUgx6RIQxmCG/lQ97+bb7ILY43mez5v7vl9/P8/T3t7+j/ufDzAjMBAYCAwQGAgMBAb87riu6/UP3zfs7f+/d4KBKyIIDBAYCAwEBggMMof3YPb2u70TDFwRQWCAwEBgIDBAYJDxHszefrh3goErIggMEBgIDAQGCAwy3oPZ2w/3TjBwRQSBAQIDgYHAAIFBxnswe/vh3gkGroggMEBgIDAQGCAwyHgPZm8/3DvBwBURBAYIDAQGAgMEBhnvwezth3snGLgigsAAgYHAQGCAwCDjPZi9/XDvBANXRBAYIDAQGAgMEBhkvAeztx/unWDgiggCAwQGAgOBAQKDjPdg9vbDvRMMXBFBYIDAQGAgMEBgkPEezN5+uHeCgSsiCAwQGAgMBAYIDDLeg9nbD/dOMHBFBIEBAgOBgcAAgUHGezB7++HeCQauiCAwQGAgMBAYIDDIeA9mbz/cO8HAFREEBggMBAYCAwQGGe/B7O2HeycYuCKCwACBgcBAYIDAIOM9mL39cO8EA1dEEBggMBAYCAwQGGS8B7O3H+6dYOCKCAIDBAYCA4EBAoOM92D29sO9EwxcEUFggMBAYCAwQGCQ8R7M3n64d4KBKyIIDBAYCAwEBggMMt6D2dsP904wcEUEgQECA4GBwACBQcZ7MHv74d4JBq6IIDBAYCAwEBggMMh4D2ZvP9w7wcAVEQQGCAwEBgIDBAYZ78Hs7Yd7Jxi4IoLAAIGBwEBggMAg4z2Yvf1w7wQDV0QQGCAwEBgIDBAYZLwHs7cf7p1g4IoIAgMEBgIDgQECg4z3YPb2w70TDFwRQWCAwEBgIDBAYJDxHszefrh3goErIggMEBgIDAQGCAwy3oPZ2w/3TjBwRQSBAQIDgYHAAIFBxnswe/vh3gkGroggMEBgIDAQGCAwyHgPZm8/3DvBwBURBAYIDAQGAgMEBhnvwezth3snGLgigsAAgYHAQGCAwCDjPZi9/XDvBANXRBAYIDAQGAgMEBhkvAeztx/unWDgiggCAwQGAgOBAQKDjPdg9vbDvRMMXBFBYIDAQGAgMEBgkPEezN5+uHeCgSsiCAwQGAgMBAYIDDJfAQYAESDFWPMcwB4AAAAASUVORK5CYII=);">
                                    <!-- 目标元素隐藏 文本 -->
                                    <a-input v-model:value="dataModel[formItem.name]" style="display: none;" />
                                    <a-upload v-model:file-list="fileList" name="file" accept="image/*" :data="{
                                        folder: 1
                                    }" :action="imageConfig.uploadUrl" :headers="headers" :dataName="formItem.name"
                                        @change="handleChange">
                                        <a-image height="60px" :preview="false" @click="preview(formItem.name)"
                                            :src="`${imageConfig.baseImageUrl}${dataModel[formItem.name] ? dataModel[formItem.name] : 'xxx'}`"
                                            fallback="data:image/png;base64,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" />
                                    </a-upload>
                                </div>
                                <!--editor  -->
                                <div v-if="formItem.type == 'editor'">
                                    <QuillEditor :options="editorOption" v-model:content="dataModel[formItem.name]"
                                        contentType="html" style="height: 200px;" />
                                </div>
                                <!-- dateTime -->
                                <div v-if="formItem.type == 'dateTime'">
                                    <a-date-picker show-time placeholder="请选择时间" v-model:value="dateTimes[index]"
                                        style="width: 100%;" @change="(value, dateString) => {
                                            onDateTimeChange(value, dateString, index);
                                        }" @ok="(value) => {
    onDateTimeOk(value, index, formItem.name)
}" />
                                </div>
                            </a-form-item>
                        </a-col>
                    </a-row>
                </div>
            </div>
            <!-- 身份标识 -->
            <div>
                <a-divider orientation="left"><span style="color:#999999;font-size:15px">身份标识</span></a-divider>
                <div>
                    <!-- 一行一个 -->
                    <a-row v-for="(formItem, index) in formItemsNodes[1]" :key="index">
                        <a-col :span="22">
                            <a-form-item v-if="formItem && (formItem.name === 'status' || formItem.name === 'userType')"
                                :name="formItem.name" :label="formItem.label" :rules="formItem.rules">
                                <!-- 判断控件类型 -->
                                <!-- input -->
                                <a-input v-if="formItem.type == 'input'" v-model:value="dataModel[formItem.name]"
                                    allow-clear autocomplete="off" :placeholder="formItem.placeholder"
                                    :defaultValue="formItem.defaultValue" />
                                <!-- textarea -->
                                <a-textarea v-if="formItem.type == 'textarea'" v-model:value="dataModel[formItem.name]"
                                    :rows="formItem.rows" :placeholder="formItem.defaultValue" />
                                <!-- select -->
                                <a-select v-if="formItem.type == 'select'" v-model:value="dataModel[formItem.name]">
                                    <a-select-option v-for="(selectItem, selectItemIndex) in formItem.selects"
                                        :key="'selectItem' + selectItemIndex + '' + index" :value="selectItem.value">
                                        {{ selectItem.name }}
                                    </a-select-option>
                                </a-select>
                                <!-- image -->
                                <div v-if="formItem.type == 'image'"
                                    style="padding: 10px;background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASAAAAEgCAIAAACb4TnXAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA0ZJREFUeNrs28FpxEAABMGTU1L+GUgx6RIQxmCG/lQ97+bb7ILY43mez5v7vl9/P8/T3t7+j/ufDzAjMBAYCAwQGAgMBAb87riu6/UP3zfs7f+/d4KBKyIIDBAYCAwEBggMMof3YPb2u70TDFwRQWCAwEBgIDBAYJDxHszefrh3goErIggMEBgIDAQGCAwy3oPZ2w/3TjBwRQSBAQIDgYHAAIFBxnswe/vh3gkGroggMEBgIDAQGCAwyHgPZm8/3DvBwBURBAYIDAQGAgMEBhnvwezth3snGLgigsAAgYHAQGCAwCDjPZi9/XDvBANXRBAYIDAQGAgMEBhkvAeztx/unWDgiggCAwQGAgOBAQKDjPdg9vbDvRMMXBFBYIDAQGAgMEBgkPEezN5+uHeCgSsiCAwQGAgMBAYIDDLeg9nbD/dOMHBFBIEBAgOBgcAAgUHGezB7++HeCQauiCAwQGAgMBAYIDDIeA9mbz/cO8HAFREEBggMBAYCAwQGGe/B7O2HeycYuCKCwACBgcBAYIDAIOM9mL39cO8EA1dEEBggMBAYCAwQGGS8B7O3H+6dYOCKCAIDBAYCA4EBAoOM92D29sO9EwxcEUFggMBAYCAwQGCQ8R7M3n64d4KBKyIIDBAYCAwEBggMMt6D2dsP904wcEUEgQECA4GBwACBQcZ7MHv74d4JBq6IIDBAYCAwEBggMMh4D2ZvP9w7wcAVEQQGCAwEBgIDBAYZ78Hs7Yd7Jxi4IoLAAIGBwEBggMAg4z2Yvf1w7wQDV0QQGCAwEBgIDBAYZLwHs7cf7p1g4IoIAgMEBgIDgQECg4z3YPb2w70TDFwRQWCAwEBgIDBAYJDxHszefrh3goErIggMEBgIDAQGCAwy3oPZ2w/3TjBwRQSBAQIDgYHAAIFBxnswe/vh3gkGroggMEBgIDAQGCAwyHgPZm8/3DvBwBURBAYIDAQGAgMEBhnvwezth3snGLgigsAAgYHAQGCAwCDjPZi9/XDvBANXRBAYIDAQGAgMEBhkvAeztx/unWDgiggCAwQGAgOBAQKDjPdg9vbDvRMMXBFBYIDAQGAgMEBgkPEezN5+uHeCgSsiCAwQGAgMBAYIDDJfAQYAESDFWPMcwB4AAAAASUVORK5CYII=);">
                                    <!-- 目标元素隐藏 文本 -->
                                    <a-input v-model:value="dataModel[formItem.name]" style="display: none;" />
                                    <a-upload v-model:file-list="fileList" name="file" accept="image/*" :data="{
                                        folder: 1
                                    }" :action="imageConfig.uploadUrl" :headers="headers" :dataName="formItem.name"
                                        @change="handleChange">
                                        <a-image height="60px" :preview="false" @click="preview(formItem.name)"
                                            :src="`${imageConfig.baseImageUrl}${dataModel[formItem.name] ? dataModel[formItem.name] : 'xxx'}`"
                                            fallback="data:image/png;base64,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" />
                                    </a-upload>
                                </div>
                                <!--editor  -->
                                <div v-if="formItem.type == 'editor'">
                                    <QuillEditor :options="editorOption" v-model:content="dataModel[formItem.name]"
                                        contentType="html" style="height: 200px;" />
                                </div>
                                <!-- dateTime -->
                                <div v-if="formItem.type == 'dateTime'">
                                    <a-date-picker show-time placeholder="请选择时间" v-model:value="dateTimes[index]"
                                        style="width: 100%;" @change="(value, dateString) => {
                                            onDateTimeChange(value, dateString, index);
                                        }" @ok="(value) => {
    onDateTimeOk(value, index, formItem.name)
}" />
                                </div>
                            </a-form-item>
                        </a-col>
                    </a-row>
                </div>
            </div>
            <!-- 身份认证 -->
            <div>
                <a-divider orientation="left"><span style="color:#999999;font-size:15px">身份认证信息</span></a-divider>
                <div>
                    <!-- 一行一个 -->
                    <a-row v-for="(formItem, index) in formItemsNodes[1]" :key="index">
                        <a-col :span="22">
                            <a-form-item
                                v-if="formItem && (formItem.name === 'rztype' || formItem.name === 'cardzm' || formItem.name === 'cardfm')"
                                :name="formItem.name" :label="formItem.label" :rules="formItem.rules">
                                <!-- 判断控件类型 -->
                                <!-- input -->
                                <a-input v-if="formItem.type == 'input'" v-model:value="dataModel[formItem.name]"
                                    allow-clear autocomplete="off" :placeholder="formItem.placeholder"
                                    :defaultValue="formItem.defaultValue" />
                                <!-- textarea -->
                                <a-textarea v-if="formItem.type == 'textarea'" v-model:value="dataModel[formItem.name]"
                                    :rows="formItem.rows" :placeholder="formItem.defaultValue" />
                                <!-- select -->
                                <a-select v-if="formItem.type == 'select'" v-model:value="dataModel[formItem.name]">
                                    <a-select-option v-for="(selectItem, selectItemIndex) in formItem.selects"
                                        :key="'selectItem' + selectItemIndex + '' + index" :value="selectItem.value">
                                        {{ selectItem.name }}
                                    </a-select-option>
                                </a-select>
                                <!-- image -->
                                <div v-if="formItem.type == 'image'"
                                    style="padding: 10px;background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASAAAAEgCAIAAACb4TnXAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA0ZJREFUeNrs28FpxEAABMGTU1L+GUgx6RIQxmCG/lQ97+bb7ILY43mez5v7vl9/P8/T3t7+j/ufDzAjMBAYCAwQGAgMBAb87riu6/UP3zfs7f+/d4KBKyIIDBAYCAwEBggMMof3YPb2u70TDFwRQWCAwEBgIDBAYJDxHszefrh3goErIggMEBgIDAQGCAwy3oPZ2w/3TjBwRQSBAQIDgYHAAIFBxnswe/vh3gkGroggMEBgIDAQGCAwyHgPZm8/3DvBwBURBAYIDAQGAgMEBhnvwezth3snGLgigsAAgYHAQGCAwCDjPZi9/XDvBANXRBAYIDAQGAgMEBhkvAeztx/unWDgiggCAwQGAgOBAQKDjPdg9vbDvRMMXBFBYIDAQGAgMEBgkPEezN5+uHeCgSsiCAwQGAgMBAYIDDLeg9nbD/dOMHBFBIEBAgOBgcAAgUHGezB7++HeCQauiCAwQGAgMBAYIDDIeA9mbz/cO8HAFREEBggMBAYCAwQGGe/B7O2HeycYuCKCwACBgcBAYIDAIOM9mL39cO8EA1dEEBggMBAYCAwQGGS8B7O3H+6dYOCKCAIDBAYCA4EBAoOM92D29sO9EwxcEUFggMBAYCAwQGCQ8R7M3n64d4KBKyIIDBAYCAwEBggMMt6D2dsP904wcEUEgQECA4GBwACBQcZ7MHv74d4JBq6IIDBAYCAwEBggMMh4D2ZvP9w7wcAVEQQGCAwEBgIDBAYZ78Hs7Yd7Jxi4IoLAAIGBwEBggMAg4z2Yvf1w7wQDV0QQGCAwEBgIDBAYZLwHs7cf7p1g4IoIAgMEBgIDgQECg4z3YPb2w70TDFwRQWCAwEBgIDBAYJDxHszefrh3goErIggMEBgIDAQGCAwy3oPZ2w/3TjBwRQSBAQIDgYHAAIFBxnswe/vh3gkGroggMEBgIDAQGCAwyHgPZm8/3DvBwBURBAYIDAQGAgMEBhnvwezth3snGLgigsAAgYHAQGCAwCDjPZi9/XDvBANXRBAYIDAQGAgMEBhkvAeztx/unWDgiggCAwQGAgOBAQKDjPdg9vbDvRMMXBFBYIDAQGAgMEBgkPEezN5+uHeCgSsiCAwQGAgMBAYIDDJfAQYAESDFWPMcwB4AAAAASUVORK5CYII=);">
                                    <!-- 目标元素隐藏 文本 -->
                                    <a-input v-model:value="dataModel[formItem.name]" style="display: none;" />
                                    <a-upload v-model:file-list="fileList" name="file" accept="image/*" :data="{
                                        folder: 1
                                    }" :action="imageConfig.uploadUrl" :headers="headers" :dataName="formItem.name"
                                        @change="handleChange">
                                        <a-image height="60px" :preview="false" @click="preview(formItem.name)"
                                            :src="`${imageConfig.baseImageUrl}${dataModel[formItem.name] ? dataModel[formItem.name] : 'xxx'}`"
                                            fallback="data:image/png;base64,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" />
                                    </a-upload>
                                </div>
                                <!--editor  -->
                                <div v-if="formItem.type == 'editor'">
                                    <QuillEditor :options="editorOption" v-model:content="dataModel[formItem.name]"
                                        contentType="html" style="height: 200px;" />
                                </div>
                                <!-- dateTime -->
                                <div v-if="formItem.type == 'dateTime'">
                                    <a-date-picker show-time placeholder="请选择时间" v-model:value="dateTimes[index]"
                                        style="width: 100%;" @change="(value, dateString) => {
                                            onDateTimeChange(value, dateString, index);
                                        }" @ok="(value) => {
    onDateTimeOk(value, index, formItem.name)
}" />
                                </div>
                            </a-form-item>
                        </a-col>
                    </a-row>
                </div>
            </div>
            <!-- 用户信息 -->
            <div>
                <a-divider orientation="left"><span style="color:#999999;font-size:15px">用户信息</span></a-divider>
                <div>
                    <!-- 一行一个 -->
                    <a-row v-for="(formItem, index) in formItemsNodes[1]" :key="index">
                        <a-col :span="22">
                            <a-form-item
                                v-if="formItem && (formItem.name === 'username' || formItem.name === 'phone' || formItem.name === 'password' || formItem.name === 'txstate' || formItem.name === 'buyOn'|| formItem.name === 'invit')"
                                :name="formItem.name" :label="formItem.label" :rules="formItem.rules">
                                <!-- 判断控件类型 -->
                                <!-- input -->
                                <a-input v-if="formItem.type == 'input'" v-model:value="dataModel[formItem.name]"
                                    allow-clear autocomplete="off" :placeholder="formItem.placeholder"
                                    :defaultValue="formItem.defaultValue" />
                                <!-- textarea -->
                                <a-textarea v-if="formItem.type == 'textarea'" v-model:value="dataModel[formItem.name]"
                                    :rows="formItem.rows" :placeholder="formItem.defaultValue" />
                                <!-- select -->
                                <a-select v-if="formItem.type == 'select'" v-model:value="dataModel[formItem.name]">
                                    <a-select-option v-for="(selectItem, selectItemIndex) in formItem.selects"
                                        :key="'selectItem' + selectItemIndex + '' + index" :value="selectItem.value">
                                        {{ selectItem.name }}
                                    </a-select-option>
                                </a-select>
                                <!-- image -->
                                <div v-if="formItem.type == 'image'"
                                    style="padding: 10px;background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASAAAAEgCAIAAACb4TnXAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA0ZJREFUeNrs28FpxEAABMGTU1L+GUgx6RIQxmCG/lQ97+bb7ILY43mez5v7vl9/P8/T3t7+j/ufDzAjMBAYCAwQGAgMBAb87riu6/UP3zfs7f+/d4KBKyIIDBAYCAwEBggMMof3YPb2u70TDFwRQWCAwEBgIDBAYJDxHszefrh3goErIggMEBgIDAQGCAwy3oPZ2w/3TjBwRQSBAQIDgYHAAIFBxnswe/vh3gkGroggMEBgIDAQGCAwyHgPZm8/3DvBwBURBAYIDAQGAgMEBhnvwezth3snGLgigsAAgYHAQGCAwCDjPZi9/XDvBANXRBAYIDAQGAgMEBhkvAeztx/unWDgiggCAwQGAgOBAQKDjPdg9vbDvRMMXBFBYIDAQGAgMEBgkPEezN5+uHeCgSsiCAwQGAgMBAYIDDLeg9nbD/dOMHBFBIEBAgOBgcAAgUHGezB7++HeCQauiCAwQGAgMBAYIDDIeA9mbz/cO8HAFREEBggMBAYCAwQGGe/B7O2HeycYuCKCwACBgcBAYIDAIOM9mL39cO8EA1dEEBggMBAYCAwQGGS8B7O3H+6dYOCKCAIDBAYCA4EBAoOM92D29sO9EwxcEUFggMBAYCAwQGCQ8R7M3n64d4KBKyIIDBAYCAwEBggMMt6D2dsP904wcEUEgQECA4GBwACBQcZ7MHv74d4JBq6IIDBAYCAwEBggMMh4D2ZvP9w7wcAVEQQGCAwEBgIDBAYZ78Hs7Yd7Jxi4IoLAAIGBwEBggMAg4z2Yvf1w7wQDV0QQGCAwEBgIDBAYZLwHs7cf7p1g4IoIAgMEBgIDgQECg4z3YPb2w70TDFwRQWCAwEBgIDBAYJDxHszefrh3goErIggMEBgIDAQGCAwy3oPZ2w/3TjBwRQSBAQIDgYHAAIFBxnswe/vh3gkGroggMEBgIDAQGCAwyHgPZm8/3DvBwBURBAYIDAQGAgMEBhnvwezth3snGLgigsAAgYHAQGCAwCDjPZi9/XDvBANXRBAYIDAQGAgMEBhkvAeztx/unWDgiggCAwQGAgOBAQKDjPdg9vbDvRMMXBFBYIDAQGAgMEBgkPEezN5+uHeCgSsiCAwQGAgMBAYIDDJfAQYAESDFWPMcwB4AAAAASUVORK5CYII=);">
                                    <!-- 目标元素隐藏 文本 -->
                                    <a-input v-model:value="dataModel[formItem.name]" style="display: none;" />
                                    <a-upload v-model:file-list="fileList" name="file" accept="image/*" :data="{
                                        folder: 1
                                    }" :action="imageConfig.uploadUrl" :headers="headers" :dataName="formItem.name"
                                        @change="handleChange">
                                        <a-image height="60px" :preview="false" @click="preview(formItem.name)"
                                            :src="`${imageConfig.baseImageUrl}${dataModel[formItem.name] ? dataModel[formItem.name] : 'xxx'}`"
                                            fallback="data:image/png;base64,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" />
                                    </a-upload>
                                </div>
                                <!--editor  -->
                                <div v-if="formItem.type == 'editor'">
                                    <QuillEditor :options="editorOption" v-model:content="dataModel[formItem.name]"
                                        contentType="html" style="height: 200px;" />
                                </div>
                                <!-- dateTime -->
                                <div v-if="formItem.type == 'dateTime'">
                                    <a-date-picker show-time placeholder="请选择时间" v-model:value="dateTimes[index]"
                                        style="width: 100%;" @change="(value, dateString) => {
                                            onDateTimeChange(value, dateString, index);
                                        }" @ok="(value) => {
    onDateTimeOk(value, index, formItem.name)
}" />
                                </div>
                            </a-form-item>
                        </a-col>
                    </a-row>
                </div>
            </div>
        </a-form>
    </a-modal>
</template>
<script setup lang="ts">
import { message } from 'ant-design-vue';
import { UploadChangeParam } from 'ant-design-vue/es/upload/interface';
import { ref, shallowRef, computed, defineExpose, onMounted } from 'vue';
import { imageConfig } from "/@/config/app-config";
import { QuillEditor, Quill } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import dayjs, { Dayjs } from 'dayjs';
const props = defineProps(["isOpen", "type", "data", "name", "formItems", "isALlName"]);
const emit = defineEmits(["close", "submit"]);
const dataFormRef = shallowRef();
const dataModel = ref(props.data);
const labelCol = { style: { width: '100px' } };
const formItems = props.formItems;
const fileList = ref([]);
const fileNowName = ref("");
const dateTimes = ref([] as any[]);
const headers = {
    authorization: 'authorization-text',
};
const editorOption = {
    modules: {
        toolbar: true // 这里将工具栏隐藏
    }
}

const rztypeStrs = [
    '护照', '驾驶证', 'SSN', '身份ID '
];
//点击
const preview = (name) => {
    fileNowName.value = name;
}
const handleChange = (info: UploadChangeParam) => {
    console.info(info);
    if (info.file.status === 'done') {
        if (info.file.response && info.file.response.ok) {
            fileList.value = [];
            message.success(`${info.file.name} 上传成功`);
            const data = info.file.response;
            //替换原来图片
            dataModel.value[fileNowName.value] = data.data;
        } else {
            message.error(`上传失败`);
            fileList.value = [];
        }
    } else if (info.file.status === 'error') {
        message.error(`上传失败`);
        fileList.value = [];
    }
};
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
    const pairs = [];
    const lens = [];
    const len2 = [];
    for (let i = 0; i < formItems.length; i++) {
        const item = formItems[i];
        lens.push(item);

    }
    return [pairs, lens];
});
//日期时间选择
const onDateTimeChange = (value, dateString, formItemIndex) => {
    dateTimes.value[formItemIndex] = dayjs(dateString, 'YYYY-MM-DD HH:mm:ss');
};
//日期时间确认
const onDateTimeOk = (date, formItemIndex, name) => {
    dataModel.value[name] = dayjs(date).format('YYYY-MM-DD HH:mm:ss');
};
//初始化时间
const initTimes = () => {
    for (let index = 0; index < formItems.length; index++) {
        const formItem = formItems[index];
        if (formItem.type === "dateTime" && dataModel.value[formItem.name]) {
            dateTimes.value[index] = dayjs(dataModel.value[formItem.name], 'YYYY-MM-DD HH:mm:ss')
        }
    }
};
//这里需要暴露出去不然父组件调用不到这个方法
defineExpose({
    close
});
onMounted(() => {
    initTimes()
});
</script>
<style lang="less" scoped></style>
