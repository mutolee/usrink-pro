import {createPinia} from 'pinia'
import piniaPersist from 'pinia-plugin-persist'

/**
 * 创建一个Pinia实例
 * <br/>
 * Pinia文档地址：https://pinia.vuejs.org/zh/introduction.html
 */
const store = createPinia()
// 持久化插件
store.use(piniaPersist)

export default store