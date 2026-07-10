import axios from "axios";

const http=axios.create({
 baseURL:"http://18.61.69.8:8080",
    headers:{
        "Content-Type":"application/json"
    }
});

http.interceptors.request.use(
    (config)=>{

        const token=localStorage.getItem("token");

        const publicUrls=[
            "/user/login",
            "/user/create",
            "/user/forgot-password",
            "/user/verify-otp",
            "/user/reset-password"
        ];

        const isPublic=publicUrls.some(url=>config.url.includes(url));

        if(token && !isPublic){

            config.headers.Authorization=`Bearer ${token}`;

        }

        return config;

    },
    (error)=>Promise.reject(error)
);

export default http;