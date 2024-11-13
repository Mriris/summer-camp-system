// store/index.js
import { createStore } from 'vuex';

export default createStore({
    state: {
        username: localStorage.getItem('username') || null, // 从 localStorage 获取用户名
    },
    mutations: {
        setUsername(state, username) {
            state.username = username;
            if (username) {
                localStorage.setItem('username', username); // 存储用户名到 localStorage
            } else {
                localStorage.removeItem('username'); // 如果为空，则从 localStorage 移除
            }
        },
    },
    actions: {
        login({ commit }, username) {
            commit('setUsername', username);
        },
        logout({ commit }) {
            commit('setUsername', null);
        },
    },
    getters: {
        getUsername: (state) => state.username,
    },
});
