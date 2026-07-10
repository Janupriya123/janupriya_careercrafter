import http from "../http-common";

class ResumeService {
  uploadResume(formData) {
    return http.post("/resume/upload", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  }

  getResumeByProfileId(profileId) {
    return http.get(`/resume/profile/${profileId}`);
  }

  deleteResume(resumeId) {
    return http.delete(`/resume/delete/${resumeId}`);
  }
  downloadResume(resumeId) {
    return http.get(`/resume/download/${resumeId}`, {
      responseType: "blob",
    });
  }
}

export default new ResumeService();
