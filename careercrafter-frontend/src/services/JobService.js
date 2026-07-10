import http from "../http-common";

class JobService {
  getAllJobs() {
    return http.get("/job/all");
  }

  getJobById(jobId) {
    return http.get(`/job/${jobId}`);
  }

  addJob(data) {
    return http.post("/job/add", data);
  }

  updateJob(jobId, data) {
    return http.put(`/job/update/${jobId}`, data);
  }

  deleteJob(jobId) {
    return http.delete(`/job/delete/${jobId}`);
  }

  getJobsByEmployerId(employerId) {
    return http.get(`/job/employer/${employerId}`);
  }

  searchByTitle(title) {
    return http.get(`/job/search/title/${title}`);
  }

  searchByLocation(location) {
    return http.get(`/job/search/location/${location}`);
  }

  searchByJobType(jobType) {
    return http.get(`/job/search/type/${jobType}`);
  }

  searchByExperience(experience) {
    return http.get(`/job/search/experience/${experience}`);
  }

  getRecommendedJobs(profileId) {
    return http.get(`/job/recommend/${profileId}`);
  }
}

export default new JobService();
