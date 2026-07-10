import { useState, useEffect } from "react";
import Navbar from "../../components/Navbar";
import ResumeService from "../../services/ResumeService";

function Resume() {
  const [file, setFile] = useState(null);
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");
  const [resume, setResume] = useState(null);

  useEffect(() => {
    loadResume();
  }, []);

  const loadResume = async () => {
    try {
      const profileId = localStorage.getItem("profileId");

      const response = await ResumeService.getResumeByProfileId(profileId);

      if (response.data.length > 0) {
        setResume(response.data[0]);
      }
    } catch (error) {}
  };

  const uploadResume = async (selectedFile = file) => {
    if (!selectedFile) {
      setError("Please choose a PDF file");
      return;
    }

    try {
      const formData = new FormData();

      formData.append("profileId", localStorage.getItem("profileId"));
      formData.append("file", selectedFile);

      await ResumeService.uploadResume(formData);

      setSuccess("Resume Uploaded Successfully");
      setTimeout(() => {
  setSuccess("");
}, 3000);
      setError("");
      setFile(null);
loadResume();

    
    } catch (error) {
      setSuccess("");
      setError("Unable to upload resume");
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
      setError("Unable to download resume");
    }
  };
  const deleteResume = async (resumeId) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete your resume?",
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await ResumeService.deleteResume(resumeId);

      setResume(null);
      setSuccess("Resume deleted successfully");
      setTimeout(() => {
  setSuccess("");
}, 3000);
      setError("");
    } catch (error) {
      alert("Unable to delete resume");
    }
  };
  return (
    <>
      <Navbar />

      <div className="container mt-4">
        <div className="card shadow">
          <div className="card-header">
            <h3>
              <i className="bi bi-file-earmark-arrow-up me-2"></i>
              Resume
            </h3>
          </div>

          <div className="card-body">
            {resume === null && (
              <>
                <input
                  type="file"
                  className="form-control mb-3"
                  accept=".pdf"
                  onChange={(e) => setFile(e.target.files[0])}
                />
                <button
                  className="btn btn-primary"
                  onClick={() => uploadResume(file)}
                >
                  Upload Resume
                </button>

                <br />
                <br />
              </>
            )}

            {success && <div className="alert alert-success">{success}</div>}

            {error && <div className="alert alert-danger">{error}</div>}

            {resume && (
              <div className="card mt-4">
                <div className="card-body">
                  <h5>
                    <i className="bi bi-file-earmark-pdf-fill text-danger me-2"></i>
                    Uploaded Resume
                  </h5>

                  <hr />

                  <p>
                    <b>File Name :</b> {resume.fileName}
                  </p>
                  <p>
                    <b>Uploaded On :</b>

                    {new Date(resume.uploadedAt).toLocaleString("en-IN", {
                      day: "2-digit",
                      month: "short",
                      year: "numeric",
                      hour: "2-digit",
                      minute: "2-digit",
                      hour12: true,
                    })}
                  </p>
                  <button
                    className="btn btn-success me-2"
                    onClick={() =>
                      downloadResume(resume.resumeId, resume.fileName)
                    }
                  >
                    <i className="bi bi-download me-2"></i>
                    Download Resume
                  </button>

                  <label className="btn btn-warning me-2 mb-0">
                    <i className="bi bi-arrow-repeat me-2"></i>
                    Replace Resume
                    <input
                      type="file"
                      accept=".pdf"
                      hidden
                      onChange={(e) => {
                        const selectedFile = e.target.files[0];
                        setFile(selectedFile);
                        uploadResume(selectedFile);
                      }}
                    />
                  </label>
                  <button
                    className="btn btn-danger"
                    onClick={() => deleteResume(resume.resumeId)}
                  >
                    <i className="bi bi-trash me-2"></i>
                    Delete Resume
                  </button>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
}

export default Resume;
