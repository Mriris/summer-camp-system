// src/axiosInstance.js
import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8081/api', // 设置为你的后端地址
    headers: {
        'Content-Type': 'application/json',
    },
});

export default axiosInstance;
