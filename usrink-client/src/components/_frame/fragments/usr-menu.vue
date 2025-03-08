<script setup>
import usrMenuHeader from "@/components/_frame/fragments/usr-menu-header.vue";
import {computed, ref, watch} from "vue";
import {useCollapseStateStore} from "@/stores/_frame/collapseStateStore";
import {useRoutesStore} from "@/stores/_frame/routesStore";
import {useNavStore} from '@/stores/_frame/navStore'
import {useRouter} from "vue-router";

const router = useRouter()
const routesStore = useRoutesStore()
const navStore = useNavStore()
const collapseStateStore = useCollapseStateStore()

// 菜单折叠状态
const collapseState = computed(() => collapseStateStore.collapseState)
// 是否使用手动宽度
const useManualWidth = ref(false)
// 监听折叠状态，如果折叠状态改变，则取消手动宽度
watch(collapseState, () => useManualWidth.value = false)
// 手动调节的宽度
const manualWidth = ref(0);

// computed
const width = computed(() => collapseStateStore.collapseState ? '64' : '220');

// Menu菜单列表
const menus = computed(() => routesStore.menus)
// 菜单当前激活项
const activeIndex = computed(() => navStore.routeInfo.id)

// 菜单路由跳转
const goTo = (path) => router.push(path)

// 接收子组件传递的数据
const handleMessage = (message) => {
    manualWidth.value = message;
    useManualWidth.value = true;
};
</script>

<template>
    <section class="usr_menu" :style="{width : (useManualWidth ? manualWidth:width) + 'px'}">
        <usr-menu-header @message-sent="handleMessage"/>
        <div class="usr_menu_panel">
            <el-scrollbar>
                <!-- 树形菜单，只支持三级菜单，够用！ -->
                <el-menu
                    class="el_menu_override"
                    :default-active="activeIndex"
                    :default-openeds="['1']"
                    :collapse="collapseState"
                    background-color="#191a23"
                    text-color="#d9d8d8">
                    <template
                        v-for="menu in menus"
                        :key="menu.id">
                        <el-sub-menu
                            v-if="menu.children"
                            :index="menu.id">
                            <template #title>
                                <el-icon>
                                    <component :is="menu.icon"/>
                                </el-icon>
                                <span>{{ menu.label }}</span>
                            </template>
                            <div
                                v-for="son in menu.children"
                                :key="son.id">
                                <el-sub-menu
                                    v-if="son.children"
                                    :index="son.id">
                                    <template #title>
                                        <el-icon>
                                            <component :is="son.icon"/>
                                        </el-icon>
                                        <span>{{ son.label }}</span>
                                    </template>
                                    <el-menu-item
                                        v-for="sun in son.children"
                                        @click="goTo(sun.path)"
                                        :index="sun.id"
                                        :key="sun.id">
                                        <el-icon>
                                            <component :is="sun.icon"/>
                                        </el-icon>
                                        <template #title>
                                            {{ sun.label }}
                                        </template>
                                    </el-menu-item>
                                </el-sub-menu>
                                <el-menu-item
                                    @click="goTo(son.path)"
                                    :index="son.id"
                                    v-else>
                                    <el-icon>
                                        <component :is="son.icon"/>
                                    </el-icon>
                                    <template #title>
                                        {{ son.label }}
                                    </template>
                                </el-menu-item>
                            </div>
                        </el-sub-menu>
                        <el-menu-item
                            @click="goTo(menu.path)"
                            :index="menu.id"
                            v-else>
                            <el-icon>
                                <component :is="menu.icon"/>
                            </el-icon>
                            <template #title>
                                {{ menu.label }}
                            </template>
                        </el-menu-item>
                    </template>
                </el-menu>
            </el-scrollbar>
        </div>
    </section>
</template>

<style scoped>
.usr_menu {
    height: 100%;
    flex-shrink: 0; /* 该项目不会缩小 */
    transition: width .5s;
    display: flex;
    flex-direction: column;
}

.usr_menu_panel {
    background-color: #191a23;
    transition: height .3s;
    height: 100%;
    flex: 1; /* 该项目会扩展以填充多余空间 */
    min-height: 0; /* 防止挤到容器外 */
}

.el_menu_override {
    border-right: solid 1px #191a23;
}
</style>