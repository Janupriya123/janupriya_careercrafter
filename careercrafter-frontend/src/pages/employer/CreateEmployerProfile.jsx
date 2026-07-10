import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import EmployerService from "../../services/EmployerService";
import Navbar from "../../components/Navbar";

function CreateEmployerProfile() {
  const navigate = useNavigate();

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const initialValues = {
    userId: localStorage.getItem("userId"),
    companyName: "",
    website: "",
    industry: "",
    companySize: "",
    companyDescription: "",
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

  const onSubmit = async (values) => {
    setError("");
    setSuccess("");

    try {
      const response = await EmployerService.addEmployer(values);

      localStorage.setItem("employerId", response.data.employerId);

      setSuccess("Employer Profile Created Successfully");

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
        setError("Unable to create profile");
      }
    }
  };

  return (
    <>
      <style>{`
body{
    background:#f5f7fa;
}

.employer-card{
    border:none;
    border-radius:20px;
    overflow:hidden;
}

.left-panel{
    background:#2f80ed;
    color:white;
    display:flex;
    flex-direction:column;
    justify-content:center;
    align-items:center;
    padding:40px;
    min-height:720px;
}

.logo-circle{
    width:100px;
    height:100px;
    border-radius:50%;
    background:white;
    display:flex;
    justify-content:center;
    align-items:center;
    margin-bottom:20px;
}

.logo-circle i{
    font-size:50px;
    color:#2f80ed;
}

.right-panel{
    padding:40px;
}

.form-control,
.form-select{
    border-radius:12px;
}

.btn-save{
    background:#2f80ed;
    color:white;
    border:none;
    border-radius:12px;
    padding:12px;
    font-weight:600;
}

.btn-save:hover{
    background:#1565c0;
    color:white;
}
`}</style>

      <Navbar />

      <div className="container py-5">
        <div className="row justify-content-center">
          <div className="col-lg-11">
            <div className="card shadow-lg employer-card">
              <div className="row g-0">
                <div className="col-md-4 left-panel">
                  <div className="logo-circle">
                    <i className="bi bi-building-fill"></i>
                  </div>

                  <h2 className="fw-bold">CareerCrafter</h2>

                  <p className="text-center mt-3">
                    Create your company profile to start posting jobs and hiring
                    talented candidates.
                  </p>

                  <i
                    className="bi bi-buildings-fill"
                    style={{ fontSize: "120px" }}
                  ></i>
                </div>

                <div className="col-md-8 right-panel">
                  <h2 className="text-center fw-bold mb-4">
                    Create Employer Profile
                  </h2>

                  <Formik
                    initialValues={initialValues}
                    validationSchema={validationSchema}
                    onSubmit={onSubmit}
                  >
                    <Form>
                      <div className="mb-3">
                        <label className="form-label">Company Name</label>

                        <Field
                          name="companyName"
                          className="form-control"
                          placeholder="Enter company name"
                        />

                        <ErrorMessage
                          name="companyName"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      <div className="mb-3">
                        <label className="form-label">Website</label>

                        <Field
                          name="website"
                          className="form-control"
                          placeholder="https://company.com"
                        />

                        <ErrorMessage
                          name="website"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      <div className="mb-3">
                        <label className="form-label">Industry</label>

                        <Field
                          name="industry"
                          className="form-control"
                          placeholder="Software, Banking, Healthcare..."
                        />

                        <ErrorMessage
                          name="industry"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      <div className="mb-3">
                        <label className="form-label">Company Size</label>

                        <Field
                          as="select"
                          name="companySize"
                          className="form-select"
                        >
                          <option value="">Select Company Size</option>

                          <option value="1-10">1-10 Employees</option>

                          <option value="11-50">11-50 Employees</option>

                          <option value="51-100">51-100 Employees</option>

                          <option value="100-500">100-500 Employees</option>

                          <option value="500+">500+ Employees</option>
                        </Field>

                        <ErrorMessage
                          name="companySize"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      <div className="mb-3">
                        <label className="form-label">
                          Company Description
                        </label>

                        <Field
                          as="textarea"
                          rows="5"
                          name="companyDescription"
                          className="form-control"
                          placeholder="Describe your company..."
                        />

                        <ErrorMessage
                          name="companyDescription"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      {error && (
                        <div className="alert alert-danger">{error}</div>
                      )}

                      {success && (
                        <div className="alert alert-success">{success}</div>
                      )}

                      <button type="submit" className="btn btn-save w-100">
                        <i className="bi bi-check-circle-fill me-2"></i>
                        Save Profile
                      </button>
                    </Form>
                  </Formik>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default CreateEmployerProfile;
