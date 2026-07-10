import { useEffect, useState } from "react";
import Navbar from "../../components/Navbar";
import ApplicationService from "../../services/ApplicationService";

function MyApplications() {
  const [applications, setApplications] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    getApplications();
  }, []);

  const getApplications = async () => {
    try {
      const profileId = localStorage.getItem("profileId");

      const response =
        await ApplicationService.getApplicationsByJobSeekerId(profileId);

      setApplications(response.data);
    } catch (error) {
      setError("Unable to load applications");
    }
  };

  return (
    <>
      <Navbar />

      <div className="container mt-4">
        <h2 className="mb-4">
          <i className="bi bi-file-earmark-text me-2"></i>
          My Applications
        </h2>

        {error && <div className="alert alert-danger">{error}</div>}

        <table className="table table-bordered table-hover">
          <thead className="table-dark">
            <tr>
              <th>Application ID</th>
              <th>Job Title</th>
              <th>Company</th>
              <th>Location</th>
              <th>Applied Date</th>
              <th>Status</th>
              <th>Cover Letter</th>
            </tr>
          </thead>

          <tbody>
            {applications.length === 0 ? (
              <tr>
                <td colSpan="5" className="text-center">
                  No Applications Found
                </td>
              </tr>
            ) : (
              applications.map((application) => (
                <tr key={application.applicationId}>
                  <td>{application.applicationId}</td>

                  <td>{application.jobTitle}</td>
                  <td>{application.companyName}</td>
                  <td>{application.location}</td>

                  <td>
                    {new Date(application.appliedDate).toLocaleString("en-IN", {
                      day: "2-digit",
                      month: "short",
                      year: "numeric",
                      hour: "2-digit",
                      minute: "2-digit",
                      hour12: true,
                    })}
                  </td>

                  <td>
                    {application.status === "Applied" && (
                      <span className="badge bg-warning text-dark">
                        Applied
                      </span>
                    )}

                    {application.status === "Approved" && (
                      <span className="badge bg-success">Approved</span>
                    )}

                    {application.status === "Rejected" && (
                      <span className="badge bg-danger">Rejected</span>
                    )}
                  </td>

                  <td>{application.coverLetter}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </>
  );
}

export default MyApplications;
