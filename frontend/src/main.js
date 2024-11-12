import { createApp } from 'vue';
import App from './App.vue';
import store from './store'; // 引入 Vuex Store
import router from './router';
import '../public/assets/css/main.css';


const app = createApp(App);
app.use(router);
app.use(store); // 注册 Store
app.mount('#app');
