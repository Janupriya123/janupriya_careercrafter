import http from "../http-common";

class EmployerService {
  addEmployer(data) {
    return http.post("/employer/add", data);
  }

  getEmployerById(employerId) {
    return http.get(`/employer/${employerId}`);
  }

  getEmployerByUserId(userId) {
    return http.get(`/employer/user/${userId}`);
  }

  updateEmployer(employerId, data) {
    return http.put(`/employer/update/${employerId}`, data);
  }

  getAllEmployers() {
    return http.get("/employer/all");
  }

  deleteEmployer(employerId) {
    return http.delete(`/employer/delete/${employerId}`);
  }
}

export default new EmployerService();
