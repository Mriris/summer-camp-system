// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Register from '../views/Register.vue';
import Login from '../views/Login.vue';
import CampApplication from "@/views/CampApplication.vue";
import Application from "@/views/Application.vue";
import EntryReview from "@/views/EntryReview.vue";
import StudentDetails from "@/views/StudentDetails.vue";
import StudentScoring from "@/views/StudentScoring.vue";
import CampOverview from "@/views/CampOverview.vue";
import AwardSelection from "@/views/AwardSelection.vue";
import CampSummary from "@/views/CampSummary.vue";
import GradeQuery from "@/views/GradeQuery.vue"; // 新增导入 EntryReview.vue

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
    {
        path: "/EntryReview", // 新增路由路径
        name: "EntryReview", // 路由名称
        component: EntryReview, // 关联 EntryReview 组件
    },
    {
        path: '/student-details/:id',
        name: 'StudentDetails',
        component: StudentDetails,
    },
    {
        path: '/StudentScoring',
        name: 'StudentScoring',
        component: StudentScoring,
    },
    {
        path: '/CampOverview',
        name: 'CampOverview',
        component: CampOverview,
    },
    {
        path: '/AwardSelection',
        name: 'AwardSelection',
        component: AwardSelection,
    },
    {
        path: '/CampSummary',
        name: 'CampSummary',
        component: CampSummary,
    },
    {
        path: '/GradeQuery',
        name: 'GradeQuery',
        component: GradeQuery,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
