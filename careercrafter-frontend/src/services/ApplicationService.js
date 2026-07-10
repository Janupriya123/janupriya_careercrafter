import http from "../http-common";

class ApplicationService {
  createApplication(data) {
    return http.post("/application/create", data);
  }

  getApplication(applicationId) {
    return http.get(`/application/getById/${applicationId}`);
  }

  getAllApplications() {
    return http.get("/application/getall");
  }

  getApplicationsByJobId(jobId) {
    return http.get(`/application/getByJobId/${jobId}`);
  }

  getApplicationsByJobSeekerId(profileId) {
    return http.get(`/application/getByjsid/${profileId}`);
  }

  updateStatus(applicationId, status) {
    return http.put(`/application/update/${applicationId}/${status}`);
  }

  deleteApplication(applicationId) {
    return http.delete(`/application/delete/${applicationId}`);
  }
}

export default new ApplicationService();
