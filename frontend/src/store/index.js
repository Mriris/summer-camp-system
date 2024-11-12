// store/index.js
import { createStore } from 'vuex';

export default createStore({
    state: {
        username: '', // 存储用户名
    },
    mutations: {
        setUsername(state, username) {
            state.username = username;
        },
    },
    actions: {
        login({ commit }, username) {
            // 假设在登录成功后调用此方法
            commit('setUsername', username);
        },
    },
});
