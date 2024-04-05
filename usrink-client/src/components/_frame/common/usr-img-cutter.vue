<script setup>
/**
 * 图片裁剪组件
 * 文档地址：https://www.npmjs.com/package/vue-img-cutter
 */
import ImgCutter from 'vue-img-cutter'
import {ref} from "vue";

// 定义emits，定义组件的事件
const emit = defineEmits(['onConfirm', 'onCancel'])

// 定义props，接收父组件传递的参数
const props = defineProps({
    index: {
        type: String,
        default: '自定义参数，同结果一起返回'
    },
    boxHeight: {
        type: Number,
        default: 458
    },
    boxWidth: {
        type: Number,
        default: 375
    },
    cutHeight: {
        type: Number,
        default: 250
    },
    cutWidth: {
        type: Number,
        default: 250
    }
})


// 裁剪图片预览
const imgCutterPreview = ref('')

/**
 * 裁剪完成，返回裁剪后的图片
 * @param data
 */
const cutDown = (data) => {
    emit('onConfirm', data)
    imgCutterPreview.value = ""
}

/**
 * 打印裁剪后的图片
 * @param data
 */
const printImg = (data) => {
    imgCutterPreview.value = data.dataURL
}

/**
 * 清空裁剪图片
 */
const onClearAll = () => {
    imgCutterPreview.value = ''
    emit('onCancel')
}

</script>

<template>
    <div class="cutter_panel">
        <div class="usr_img_cutter">
            <ImgCutter
                ref="imgCutterModal"
                label="选择本地图片"
                fileType="jpeg"
                :crossOrigin="true"
                crossOriginHeader="*"
                rate=""
                :tool="false"
                toolBgc="none"
                :isModal="false"
                :showChooseBtn="true"
                :lockScroll="true"
                :boxWidth="props.boxWidth"
                :boxHeight="props.boxHeight"
                :cutWidth="props.cutWidth"
                :cutHeight="props.cutHeight"
                :sizeChange="false"
                :moveAble="true"
                :imgMove="true"
                :originalGraph="false"
                WatermarkText=""
                WatermarkTextFont=""
                WatermarkTextColor="#00ff00"
                :WatermarkTextX="0.95"
                :WatermarkTextY="0.95"
                :smallToUpload="false"
                :saveCutPosition="false"
                :scaleAble="true"
                :previewMode="true"
                :quality="1"
                accept="image/gif, image/jpeg ,image/png"
                :toolBoxOverflow="true"
                :index="props.index"
                @cutDown="cutDown"
                @onPrintImg="printImg"
                @onClearAll="onClearAll">
            </ImgCutter>
            <div class="preview_class">
                <el-image :src="imgCutterPreview">
                    <template #error>
                        <el-text type="info" :size="'large'">预览</el-text>
                    </template>
                </el-image>
            </div>
        </div>
    </div>
</template>

<style scoped>
.usr_img_cutter {
    display: flex;
    flex-wrap: nowrap;
    column-gap: 20px;
}

:deep(.usr_img_cutter) .copyright {
    display: none !important;
}

:deep(.usr_img_cutter) .i-dialog-footer {
    margin-bottom: 0 !important;
}

.preview_class {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f6f8f9;
}
</style>