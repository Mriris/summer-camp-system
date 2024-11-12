// store/index.js
import { createStore } from 'vuex';

export default createStore({
    state: {
        username: null, // 用于存储登录后的用户名
    },
    mutations: {
        setUsername(state, username) {
            state.username = username;
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
