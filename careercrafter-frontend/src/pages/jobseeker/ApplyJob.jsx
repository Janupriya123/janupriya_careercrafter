import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import Navbar from "../../components/Navbar";
import ApplicationService from "../../services/ApplicationService";

function ApplyJob() {
  const { jobId } = useParams();

  const navigate = useNavigate();

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const initialValues = {
    jobId: jobId,
    jobSeekerId: localStorage.getItem("profileId"),
    coverLetter: "",
    status: "Applied",
  };

  const validationSchema = Yup.object({
    coverLetter: Yup.string().required("Cover Letter is required"),
  });
  console.log("Submit clicked");

  const onSubmit = async (values) => {
    setError("");
    setSuccess("");

    try {
      console.log("Calling API...");
      await ApplicationService.createApplication(values);
      console.log("API Success");

      setSuccess("Application Submitted Successfully");

      setTimeout(() => {
        navigate("/my-applications");
      }, 1500);
    } catch (error) {
      console.log(error);
      console.log("API Failed");

      if (error.response) {
        if (typeof error.response.data === "string") {
          setError(error.response.data);
        } else {
          const errors = Object.values(error.response.data);
          setError(errors.join(", "));
        }
      } else {
        setError("Unable to submit application");
      }
    }
  };

  return (
    <>
      <Navbar />

      <div className="container mt-4">
        <div className="card shadow">
          <div className="card-header text-center">
            <h3>
              <i className="bi bi-send me-2"></i>
              Apply Job
            </h3>
          </div>

          <div className="card-body">
            <Formik
              initialValues={initialValues}
              validationSchema={validationSchema}
              onSubmit={onSubmit}
            >
              <Form>
                <div className="mb-3">
                  <label>Cover Letter</label>

                  <Field
                    as="textarea"
                    rows="6"
                    name="coverLetter"
                    className="form-control"
                    placeholder="Write your cover letter..."
                  />

                  <ErrorMessage
                    name="coverLetter"
                    component="div"
                    className="text-danger"
                  />
                </div>

                {error && <div className="text-danger mb-3">{error}</div>}

                {success && (
                  <div className="alert alert-success">{success}</div>
                )}

                <button type="submit" className="btn btn-success w-100">
                  <i className="bi bi-send me-2"></i>
                  Submit Application
                </button>
              </Form>
            </Formik>
          </div>
        </div>
      </div>
    </>
  );
}

export default ApplyJob;
