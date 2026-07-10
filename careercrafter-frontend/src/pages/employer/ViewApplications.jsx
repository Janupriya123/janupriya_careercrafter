import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Navbar from "../../components/Navbar";
import ApplicationService from "../../services/ApplicationService";
import ResumeService from "../../services/ResumeService";

function ViewApplications() {
  const {jobId}=useParams();
  const [applications, setApplications] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    getApplications();
  }, []);

  const getApplications = async () => {
    try {
      const response = await ApplicationService.getApplicationsByJobId(jobId);

      setApplications(response.data);
    } catch (error) {
      setError("Unable to load applications");
    }
  };

  const updateStatus = async (applicationId, status) => {
    try {
      await ApplicationService.updateStatus(applicationId, status);

      getApplications();
    } catch (error) {
      setError("Unable to update status");
    }
  };
  const downloadResume = async (resumeId, fileName) => {
    try {
      const response = await ResumeService.downloadResume(resumeId);

      const url = window.URL.createObjectURL(new Blob([response.data]));

      const link = document.createElement("a");

      link.href = url;
      link.setAttribute("download", fileName);

      document.body.appendChild(link);

      link.click();

      link.remove();
    } catch (error) {
      alert("Unable to download resume");
    }
  };
   return (
  <>
    <Navbar />

    <div className="container mt-4">
      <h3 className="mb-4">
        <i className="bi bi-file-earmark-text me-2"></i>
        Job Applications
      </h3>

      {error && <div className="alert alert-danger">{error}</div>}

      <div className="table-responsive">
        <table className="table table-bordered table-hover align-middle">
          <thead className="table-dark text-center">
            <tr>
              <th>Application ID</th>
              <th>Applicant</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Education</th>
              <th>Experience</th>
              <th>Skills</th>
              <th>Applied Date</th>
              <th>Status</th>
              <th style={{ width: "110px" }}>Action</th>
              <th style={{ width: "130px" }}>Resume</th>
            </tr>
          </thead>

          <tbody>
            {applications.length === 0 ? (
              <tr>
                <td colSpan="11" className="text-center">
                  No Applications Found
                </td>
              </tr>
            ) : (
              applications.map((application) => (
                <tr key={application.applicationId}>
                  <td>{application.applicationId}</td>

                  <td>{application.jobSeekerName}</td>

                  <td>{application.email}</td>

                  <td>{application.phoneNumber}</td>

                  <td>{application.education}</td>

                  <td>{application.experienceYears} Years</td>

                  <td>{application.skills}</td>

                  <td>
                    {new Date(application.appliedDate).toLocaleString(
                      "en-IN",
                      {
                        day: "2-digit",
                        month: "short",
                        year: "numeric",
                        hour: "2-digit",
                        minute: "2-digit",
                        hour12: true,
                      }
                    )}
                  </td>

                  <td className="text-center">
                    <span
                      className={`badge rounded-pill px-3 py-2 ${
                        application.status === "Approved"
                          ? "bg-success"
                          : application.status === "Rejected"
                          ? "bg-danger"
                          : "bg-warning text-dark"
                      }`}
                    >
                      {application.status}
                    </span>
                  </td>

                  <td className="text-center">
                    <div
                      className="btn-group"
                      role="group"
                      aria-label="Actions"
                    >
                      <button
                        className={`btn btn-sm ${
                          application.status === "Approved"
                            ? "btn-success"
                            : "btn-outline-success"
                        }`}
                        title="Approve"
                        onClick={() =>
                          updateStatus(
                            application.applicationId,
                            "Approved"
                          )
                        }
                        disabled={application.status === "Approved"}
                      >
                        <i className="bi bi-check-lg"></i>
                      </button>

                      <button
                        className={`btn btn-sm ${
                          application.status === "Rejected"
                            ? "btn-danger"
                            : "btn-outline-danger"
                        }`}
                        title="Reject"
                        onClick={() =>
                          updateStatus(
                            application.applicationId,
                            "Rejected"
                          )
                        }
                        disabled={application.status === "Rejected"}
                      >
                        <i className="bi bi-x-lg"></i>
                      </button>
                    </div>
                  </td>

                  <td className="text-center">
                    {application.resumeId ? (
                      <button
                        className="btn btn-info btn-sm"
                        onClick={() =>
                          downloadResume(
                            application.resumeId,
                            application.resumeFileName
                          )
                        }
                      >
                        <i className="bi bi-download me-1"></i>
                        Resume
                      </button>
                    ) : (
                      <span className="text-danger">No Resume</span>
                    )}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  </>
);
}
export default ViewApplications;
