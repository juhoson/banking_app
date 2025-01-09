import axios from 'axios';

export const api = axios.create({
    baseURL: '/api',  // This will use the proxy setting from package.json
    headers: {
        'Content-Type': 'application/json',
    },
});

// Add response interceptor for error handling
api.interceptors.response.use(
    response => response,
    error => {
        // Handle errors here (e.g., redirect to login if 401)
        return Promise.reject(error);
    }
);