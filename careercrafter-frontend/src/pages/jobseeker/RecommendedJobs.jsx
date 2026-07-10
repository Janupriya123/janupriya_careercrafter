import { useEffect, useState } from "react";
import Navbar from "../../components/Navbar";
import JobService from "../../services/JobService";
import UserService from "../../services/UserService";
import { useNavigate } from "react-router-dom";

function RecommendedJobs() {
  const [jobs, setJobs] = useState([]);
  const [error, setError] = useState("");

  const navigate = useNavigate();
  useEffect(() => {
    loadRecommendations();
  }, []);
  const loadRecommendations = async () => {
    try {
      const user = JSON.parse(localStorage.getItem("user"));

      const profile = await UserService.getProfileByUserId(user.userId);

      const response = await JobService.getRecommendedJobs(
        profile.data.profileId,
      );

      setJobs(response.data);
    } catch (error) {
      setError("Unable to load recommended jobs");
    }
  };
}
