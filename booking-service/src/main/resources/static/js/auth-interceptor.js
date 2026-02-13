// auth-interceptor.js - UPDATED for Hybrid Architecture
const originalFetch = window.fetch;

window.fetch = async (...args) => {
    let [resource, config] = args;
    
    // 1. Get credentials from multiple possible storage formats
    const userStr = localStorage.getItem('user');
    const directToken = localStorage.getItem('token');
    
    let activeToken = directToken;

    if (userStr) {
        try {
            const user = JSON.parse(userStr);
            if (user.token) activeToken = user.token;
        } catch(e) { console.error("Interceptor: User object corrupt"); }
    }
    
    // 2. Attach token if it exists
    if (activeToken) {
        config = config || {};
        config.headers = config.headers || {};
        config.headers['Authorization'] = 'Bearer ' + activeToken;
    }
    
    try {
        const response = await originalFetch(resource, config);

        // 3. Global Error Handling - Redirect to Movie Service Port 8081
		if (response.status === 401) {
		    console.warn("Session invalid. Redirecting to Home...");
		    
		    // Remove the 'clear()' so you don't lose the username on the navbar
		    // localStorage.clear(); 

		    // Redirect to Home instead of Login
		    window.location.href = 'http://localhost:8082/home.html';
		}

        return response;
    } catch (error) {
        return Promise.reject(error);
    }
};