import axios from 'axios';

// Create a custom axios instance
const api = axios.create({
    baseURL: import.meta.env.VITE_SERVER_URL, // Your backend URL
});

// Interceptor to add JWT token to Authorization header
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token && config.headers) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    response => response,
    error => {
        if (error.response && error.response.status === 401) {
            if (error.response.data.message === "Session has expired. Please log in again.") {
                localStorage.removeItem('token');
                window.location.href = '/';
                alert('Your session has expired. Please log in again.');
            }
        }
        return Promise.reject(error);
    }
);

export default api;