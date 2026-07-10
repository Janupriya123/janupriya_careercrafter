import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../../components/Navbar";
import JobService from "../../services/JobService";

function SearchJobs() {
  const [jobs, setJobs] = useState([]);
  const [error, setError] = useState("");
  const [title, setTitle] = useState("");
  const navigate = useNavigate();
  const [searchType, setSearchType] = useState("title");
  const [recommendedJobs, setRecommendedJobs] = useState([]);
  useEffect(() => {
    getJobs();

    getRecommendedJobs();
  }, []);

  const getJobs = async () => {
    try {
      const response = await JobService.getAllJobs();

      setJobs(response.data);
    } catch (error) {
      setError("Unable to load jobs");
    }
  };
  const getRecommendedJobs = async () => {
    try {
      const profileId = localStorage.getItem("profileId");

      const response = await JobService.getRecommendedJobs(profileId);

      setRecommendedJobs(response.data);
    } catch (error) {
      console.log(error);
    }
  };
  const searchJobs = async (searchValue) => {
    if (searchValue.trim() === "") {
      getJobs();
      return;
    }

    try {
      let response;

      if (searchType === "title") {
        response = await JobService.searchByTitle(searchValue);
      } else if (searchType === "location") {
        response = await JobService.searchByLocation(searchValue);
      } else if (searchType === "experience") {
        response = await JobService.searchByExperience(searchValue);
      } else if (searchType === "jobType") {
        response = await JobService.searchByJobType(searchValue);
      }

      setJobs(response.data);
    } catch (error) {
      setError("Unable to search jobs");
    }
  };

  const resetSearch = () => {
    setTitle("");
    setSearchType("title");
    getJobs();
  };
  const getPlaceholder = () => {
    if (searchType === "title") {
      return "Enter Job Title";
    }

    if (searchType === "location") {
      return "Enter Location";
    }

    if (searchType === "experience") {
      return "Enter Years of Experience";
    }

    return "Enter Job Type";
  };
  const displayJobs =
    title.trim() === ""
      ? jobs.filter(
          (job) => !recommendedJobs.some((r) => r.jobId === job.jobId),
        )
      : jobs;
  return (
    <>
      <Navbar />

      <div className="container mt-4">
        <h2 className="text-center mb-4">
          <i className="bi bi-search me-2"></i>
          Available Jobs
        </h2>

        {error && <div className="alert alert-danger">{error}</div>}

        {/* Search Section */}

        <div className="card shadow-sm mb-4">
          <div className="card-body">
            <div className="row align-items-end">
              <div className="col-md-3">
                <label className="form-label fw-bold">Search By</label>

                <select
                  className="form-select"
                  value={searchType}
                  onChange={(e) => setSearchType(e.target.value)}
                >
                  <option value="title">Job Title</option>
                  <option value="location">Location</option>
                  <option value="experience">Experience</option>
                  <option value="jobType">Job Type</option>
                </select>
              </div>

              <div className="col-md-5">
                <label className="form-label fw-bold">Search</label>

                <input
                  type="text"
                  className="form-control"
                  placeholder={getPlaceholder()}
                  value={title}
                  onChange={(e) => {
                    const value = e.target.value;

                    setTitle(value);

                    searchJobs(value);
                  }}
                />
              </div>

              <div className="col-md-2">
                <button
                  className="btn btn-primary w-100"
                  onClick={() => searchJobs(title)}
                >
                  <i className="bi bi-search me-2"></i>
                  Search
                </button>
              </div>

              <div className="col-md-2">
                <button
                  className="btn btn-secondary w-100"
                  onClick={resetSearch}
                >
                  Reset
                </button>
              </div>
            </div>
          </div>
        </div>

        {title.trim() === "" && recommendedJobs.length > 0 && (
          <>
            <h3 className="text-success mb-3">
              <i className="bi bi-stars me-2"></i>
              Recommended For You
            </h3>

            <div className="row">
              {recommendedJobs.map((job) => (
                <div className="col-md-6 mb-4" key={job.jobId}>
                  <div className="card border-success shadow">
                    <div className="card-body">
                      <h5>
                        <i className="bi bi-briefcase-fill text-success me-2"></i>

                        {job.title}
                      </h5>

                      <p>
                        <b>Company :</b> {job.companyName}
                      </p>

                      <p>
                        <b>Location :</b> {job.location}
                      </p>

                      <p>
                        <b>Experience :</b> {job.experienceRequired} Years
                      </p>

                      <p>
                        <b>Salary :</b>₹ {job.salaryMin.toLocaleString("en-IN")}{" "}
                        - ₹ {job.salaryMax.toLocaleString("en-IN")}
                      </p>

                      <button
                        className="btn btn-success"
                        onClick={() => navigate(`/apply-job/${job.jobId}`)}
                      >
                        Apply
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>

            <hr className="mb-4" />
          </>
        )}
        <h3 className="mb-3">All Available Jobs</h3>

        <div className="row">
          {jobs.length === 0 ? (
            <h4 className="text-center">No Jobs Available</h4>
          ) : (
            displayJobs
              .filter((job) => job.status.toUpperCase() === "OPEN")
              .map((job) => (
                <div className="col-md-6 mb-4" key={job.jobId}>
                  <div className="card shadow h-100">
                    <div className="card-body">
                      <h4>
                        <i className="bi bi-briefcase-fill me-2 text-primary"></i>

                        {job.title}
                      </h4>

                      <p className="text-secondary">
                        <i className="bi bi-building me-2"></i>

                        {job.companyName}
                      </p>

                      <hr />

                      <p>
                        <b>Location :</b> {job.location}
                      </p>

                      <p>
                        <b>Experience :</b> {job.experienceRequired} Years
                      </p>

                      <p>
                        <b>Salary :</b>₹ {job.salaryMin.toLocaleString("en-IN")}{" "}
                        - ₹ {job.salaryMax.toLocaleString("en-IN")}
                      </p>

                      <p>
                        <b>Job Type :</b>{" "}
                        <span className="badge bg-primary">{job.jobType}</span>
                      </p>

                      <p>
                        <b>Vacancies :</b> {job.vacancies}
                      </p>

                      <p>
                        <b>Status :</b>{" "}
                        <span className="badge bg-success">{job.status}</span>
                      </p>

                      <div className="d-flex gap-2">
                        <button
                          className="btn btn-primary"
                          onClick={() => navigate(`/job-details/${job.jobId}`)}
                        >
                          <i className="bi bi-eye me-2"></i>
                          View Details
                        </button>

                        <button
                          className="btn btn-success"
                          onClick={() => navigate(`/apply-job/${job.jobId}`)}
                        >
                          <i className="bi bi-send me-2"></i>
                          Apply
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              ))
          )}
        </div>
      </div>
    </>
  );
}

export default SearchJobs;
