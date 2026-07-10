import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import Navbar from "../../components/Navbar";
import EmployerService from "../../services/EmployerService";

function UpdateEmployer() {
  const navigate = useNavigate();

  const [employer, setEmployer] = useState(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const employerId = localStorage.getItem("employerId");

  useEffect(() => {
    getEmployer();
  }, []);

  const getEmployer = async () => {
    try {
      const response = await EmployerService.getEmployerById(employerId);
    
      setEmployer(response.data);
    } catch (error) {
      setError("Unable to load employer details");
    }
  };

  const validationSchema = Yup.object({
    companyName: Yup.string()
      .trim()
      .required("Company name is required")
      .matches(
        /^(?=.*[A-Za-z])[A-Za-z0-9&.,()'\/ -]+$/,
        "Company name must contain at least one letter",
      ),

    website: Yup.string()
  .required("Website is required")
  .matches(
    /^(https?:\/\/)?(www\.)?[a-zA-Z0-9-]+(\.[a-zA-Z]{2,})+$/,
    "Enter a valid website"
  ),
    industry: Yup.string()
      .required("Industry is required")
      .matches(/^[A-Za-z ]+$/, "Industry can contain only letters"),

    companySize: Yup.string().required("Company size is required"),

    companyDescription: Yup.string()
      .required("Company description is required")
      .matches(/[A-Za-z]/, "Description must contain letters"),
  });

  const updateEmployer = async (values) => {
    setError("");
    setSuccess("");

    try {
      await EmployerService.updateEmployer(employerId, values);

      setSuccess("Profile Updated Successfully");

      setTimeout(() => {
        navigate("/employer");
      }, 1500);
    } catch (error) {
      if (error.response) {
        if (typeof error.response.data === "string") {
          setError(error.response.data);
        } else {
          const errors = Object.values(error.response.data);
          setError(errors.join(", "));
        }
      } else {
        setError("Unable to update profile");
      }
    }
  };
   
  if (employer == null) {
    return (
      <>
        <Navbar />
        <div className="container mt-5">
          <h3>Loading...</h3>
        </div>
      </>
    );
  }

  return (
    <>
      <Navbar />

      <div className="container mt-4">
        <div className="card shadow">
          <div className="card-header text-center">
            <h3>
              <i className="bi bi-building-gear me-2"></i>
              Update Company Profile
            </h3>
          </div>

          <div className="card-body">
            <Formik
              initialValues={employer}
              enableReinitialize={true}
              validationSchema={validationSchema}
              onSubmit={updateEmployer}
            >
              <Form>
                <div className="mb-3">
                  <label>Company Name</label>

                  <Field name="companyName" className="form-control" />

                  <ErrorMessage
                    name="companyName"
                    component="div"
                    className="text-danger"
                  />
                </div>

                <div className="mb-3">
                  <label>Website</label>

                  <Field name="website" className="form-control" />

                  <ErrorMessage
                    name="website"
                    component="div"
                    className="text-danger"
                  />
                </div>

                <div className="mb-3">
                  <label>Industry</label>

                  <Field name="industry" className="form-control" />

                  <ErrorMessage
                    name="industry"
                    component="div"
                    className="text-danger"
                  />
                </div>

                <div className="mb-3">
                  <label>Company Size</label>

                  <Field as="select" name="companySize" className="form-select">
                    <option value="1-10">1-10</option>
                    <option value="11-50">11-50</option>
                    <option value="51-100">51-100</option>
                    <option value="100-500">100-500</option>
                    <option value="500+">500+</option>
                  </Field>

                  <ErrorMessage
                    name="companySize"
                    component="div"
                    className="text-danger"
                  />
                </div>

                <div className="mb-3">
                  <label>Company Description</label>

                  <Field
                    as="textarea"
                    rows="4"
                    name="companyDescription"
                    className="form-control"
                  />

                  <ErrorMessage
                    name="companyDescription"
                    component="div"
                    className="text-danger"
                  />
                </div>

                {error && <div className="text-danger mb-3">{error}</div>}

                {success && (
                  <div className="alert alert-success">{success}</div>
                )}

                <button type="submit" className="btn btn-primary w-100">
                  <i className="bi bi-check-circle me-2"></i>
                  Update Profile
                </button>
              </Form>
            </Formik>
          </div>
        </div>
      </div>
    </>
  );
}

export default UpdateEmployer;
