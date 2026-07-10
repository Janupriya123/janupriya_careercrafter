import http from "../http-common";

class ProfileService {
  createProfile(data) {
    return http.post("/profile/add", data);
  }

  getAllProfiles() {
    return http.get("/profile/all");
  }

  getProfileById(profileId) {
    return http.get(`/profile/${profileId}`);
  }

  updateProfile(profileId, data) {
    return http.put(`/profile/update/${profileId}`, data);
  }

  deleteProfile(profileId) {
    return http.delete(`/profile/delete/${profileId}`);
  }

  getProfileByUserId(userId) {
    return http.get(`/profile/user/${userId}`);
  }

  searchBySkills(skills) {
    return http.get(`/profile/skills/${skills}`);
  }
}

export default new ProfileService();
