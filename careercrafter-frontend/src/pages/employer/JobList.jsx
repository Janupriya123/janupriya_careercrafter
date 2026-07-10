import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../../components/Navbar";
import JobService from "../../services/JobService";

function JobList() {
  const [jobs, setJobs] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    getJobs();
  }, []);

  const getJobs = async () => {
    try {
      const employerId = localStorage.getItem("employerId");

      const response = await JobService.getJobsByEmployerId(employerId);

      setJobs(response.data);
    } catch (error) {
      setError("Unable to load jobs");
    }
  };
  const deleteJob = async (jobId) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this job?",
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await JobService.deleteJob(jobId);

      alert("Job deleted successfully");

      getJobs();
    } catch (error) {
      if (error.response && error.response.data) {
        alert(error.response.data);
      } else {
        alert("Unable to delete job");
      }
    }
  };

  return (
    <>
      <Navbar />

      <div className="container mt-4">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h3>
            <i className="bi bi-briefcase-fill me-2"></i>
            My Jobs
          </h3>

          <button
            className="btn btn-primary"
            onClick={() => navigate("/add-job")}
          >
            <i className="bi bi-plus-circle me-2"></i>
            Add Job
          </button>
        </div>

        {error && <div className="alert alert-danger">{error}</div>}

        <table className="table table-bordered table-hover">
          <thead className="table-dark">
            <tr>
              <th>Job ID</th>
              <th>Title</th>
              <th>Location</th>
              <th>Experience</th>
              <th>Salary</th>
              <th>Type</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {jobs.length === 0 ? (
              <tr>
                <td colSpan="8" className="text-center">
                  No Jobs Found
                </td>
              </tr>
            ) : (
              jobs.map((job) => (
                <tr key={job.jobId}>
                  <td>{job.jobId}</td>

                  <td>{job.title}</td>

                  <td>{job.location}</td>

                  <td>{job.experienceRequired} Years</td>

                  <td>
                    ₹ {job.salaryMin} - ₹ {job.salaryMax}
                  </td>

                  <td>{job.jobType}</td>

                  <td>{job.status}</td>

                  <td>
                    <button
                      className="btn btn-warning btn-sm me-2"
                      title="Edit Job"
                      onClick={() => navigate(`/update-job/${job.jobId}`)}
                    >
                      <i className="bi bi-pencil-square"></i>
                    </button>

                    <button
                      className="btn btn-danger btn-sm me-2"
                      title="Delete Job"
                      onClick={() => deleteJob(job.jobId)}
                    >
                      <i className="bi bi-trash"></i>
                    </button>

                    <button
                      className="btn btn-info btn-sm"
                      title="View Applications"
                      onClick={() => navigate(`/applications/${job.jobId}`)}
                    >
                      <i className="bi bi-file-earmark-text"></i>
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </>
  );
}

export default JobList;
