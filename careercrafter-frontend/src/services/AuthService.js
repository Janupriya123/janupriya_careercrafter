import http from "../http-common";

class AuthService {
  register(data) {
    return http.post("/user/create", data);
  }

  login(data) {
    return http.post("/user/login", data);
  }

  getUserById(id) {
    return http.get(`/user/getById/${id}`);
  }

  getAllUsers() {
    return http.get("/user/getAll");
  }

  updateUser(id, data) {
    return http.put(`/user/update/${id}`, data);
  }

  deleteUser(id) {
    return http.delete(`/user/delete/${id}`);
  }
  getEmployerByUserId(userId) {
    return http.get(`/employer/user/${userId}`);
  }

  getProfileByUserId(userId) {
    return http.get(`/profile/user/${userId}`);
  }
  forgotPassword(data) {
    return http.post("/user/forgot-password", data);
  }

  verifyOtp(data) {
    return http.post("/user/verify-otp", data);
  }

  resetPassword(data) {
    return http.post("/user/reset-password", data);
  }
}

export default new AuthService();
