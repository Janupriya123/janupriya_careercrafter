import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Navbar from "../../components/Navbar";
import JobService from "../../services/JobService";

function JobDetails() {
  const { jobId } = useParams();

  const [data, setData] = useState(null);
  const [error, setError] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    getJob();
  }, []);

  const getJob = async () => {
    try {
      const response = await JobService.getJobById(jobId);

      setData(response.data);
    } catch (error) {
      setError("Unable to load job details");
    }
  };

  if (data === null) {
    return (
      <>
        <Navbar />

        <div className="container mt-5">Loading...</div>
      </>
    );
  }

  return (
    <>
      <Navbar />

      <div className="container mt-4">
        <div className="card shadow">
          <div className="card-header bg-primary text-white">
            <h3>
              <i className="bi bi-briefcase-fill me-2"></i>

              {data.title}
            </h3>
          </div>

          <div className="card-body">
            <h5>
              <i className="bi bi-building me-2"></i>

              {data.companyName}
            </h5>

            <hr />

            <p>
              <b>Location :</b> {data.location}
            </p>

            <p>
              <b>Experience :</b> {data.experienceRequired} Years
            </p>

            <p>
              <b>Salary :</b> ₹ {data.salaryMin.toLocaleString("en-IN")} - ₹{" "}
              {data.salaryMax.toLocaleString("en-IN")}
            </p>

            <p>
              <b>Job Type :</b> {data.jobType}
            </p>

            <p>
              <b>Vacancies :</b> {data.vacancies}
            </p>

            <p>
              <b>Status :</b> {data.status}
            </p>

            <hr />

            <h5>Job Description</h5>

            <p>{data.description}</p>

            <h5>Job Description</h5>

            <div className="border rounded p-3 bg-light">
              {data.description}
            </div>

            <hr />

            <p>
              <b>Application Deadline :</b>

              {new Date(data.applicationDeadline).toLocaleDateString("en-IN", {
                day: "2-digit",
                month: "short",
                year: "numeric",
              })}
            </p>

            <button
              className="btn btn-success"
              onClick={() => navigate(`/apply-job/${data.jobId}`)}
            >
              <i className="bi bi-send me-2"></i>
              Apply Now
            </button>
          </div>
        </div>
      </div>
    </>
  );
}

export default JobDetails;
