// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Register from '../views/Register.vue';
import Login from '../views/Login.vue';
import CampApplication from "@/views/CampApplication.vue";
import Application from "@/views/Application.vue";

const routes = [
    { path: '/', name: 'Home', component: Home },
    { path: '/register', name: 'Register', component: Register },
    { path: '/login', name: 'Login', component: Login },
    {
        path: "/CampApplication",
        name: "CampApplication",
        component: CampApplication,
    },
    {
        path: "/Application",
        name: "Application",
        component: Application,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
