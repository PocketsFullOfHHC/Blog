import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
// 引入组件库的图标库
import * as Icons from '@ant-design/icons-vue';
import 'ant-design-vue/dist/reset.css';

createApp(App).use(store).use(router).use(Antd).mount('#app');
