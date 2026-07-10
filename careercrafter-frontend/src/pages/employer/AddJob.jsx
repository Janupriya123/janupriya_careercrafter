import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import Navbar from "../../components/Navbar";
import JobService from "../../services/JobService";

function AddJob() {
  const navigate = useNavigate();

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const initialValues = {
    employerId: localStorage.getItem("employerId"),
    title: "",
    description: "",
    location: "",
    experienceRequired: "",
    salaryMin: "",
    salaryMax: "",
    jobType: "",
    vacancies: "",
    applicationDeadline: "",
    status: "OPEN",
  };
  const today = new Date();

  const maxDate = new Date();
  maxDate.setFullYear(today.getFullYear() + 1);
  const validationSchema = Yup.object({
    title: Yup.string()
      .required("Title is required")
      .matches(/[A-Za-z]/, "Job title must contain letters"),

    description: Yup.string().required("Description is required"),

    location: Yup.string()
      .required("Location is required")
      .matches(/[A-Za-z]/, "Location must contain letters"),

    experienceRequired: Yup.number()
      .required("Experience is required")
      .min(0, "Experience cannot be negative")
      .max(50, "Experience cannot exceed 50 years"),
    salaryMin: Yup.number()
      .typeError("Minimum salary must be a number")
      .integer("Minimum salary must be a whole number")
      .required("Minimum salary is required")
      .min(10000, "Minimum salary should be at least 10000")
      .max(100000000, "Salary is too high"),

    salaryMax: Yup.number()
      .typeError("Maximum salary must be a number")
      .integer("Maximum salary must be a whole number")
      .required("Maximum salary is required")
      .moreThan(
        Yup.ref("salaryMin"),
        "Maximum salary must be greater than minimum salary",
      )
      .max(100000000, "Salary is too high"),

    jobType: Yup.string().required("Job Type is required"),

    vacancies: Yup.number()
      .required("Vacancies are required")
      .min(1, "Minimum 1 vacancy required")
      .max(1000, "Vacancies cannot exceed 1000"),

    applicationDeadline: Yup.date()
      .required("Application deadline is required")
      .min(today, "Application deadline must be today or later")
      .max(
        maxDate,
        "Application deadline cannot be more than 1 year from today",
      ),
  });

  const onSubmit = async (values) => {
    setError("");
    setSuccess("");

    try {
      await JobService.addJob(values);

      setSuccess("Job Added Successfully");

      setTimeout(() => {
        navigate("/my-jobs");
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
        setError("Unable to add job");
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
              <i className="bi bi-plus-circle me-2"></i>
              Add Job
            </h3>
          </div>

          <div className="card-body">
            <Formik
              initialValues={initialValues}
              validationSchema={validationSchema}
              validateOnChange={true}
              validateOnBlur={true}
              onSubmit={onSubmit}
            >
              <Form>
                <div className="mb-3">
                  <label>Job Title</label>

                  <Field name="title" className="form-control" />

                  <ErrorMessage
                    name="title"
                    component="div"
                    className="text-danger"
                  />
                </div>

                <div className="mb-3">
                  <label>Description</label>

                  <Field
                    as="textarea"
                    rows="4"
                    name="description"
                    className="form-control"
                  />

                  <ErrorMessage
                    name="description"
                    component="div"
                    className="text-danger"
                  />
                </div>

                <div className="mb-3">
                  <label>Location</label>

                  <Field name="location" className="form-control" />

                  <ErrorMessage
                    name="location"
                    component="div"
                    className="text-danger"
                  />
                </div>

                <div className="row">
                  <div className="col-md-6 mb-3">
                    <label>Experience</label>

                    <Field
                      type="number"
                      name="experienceRequired"
                      className="form-control"
                      className="form-control"
                      onKeyDown={(e) => {
                        if (e.key === "-" || e.key === "e") {
                          e.preventDefault();
                        }
                      }}
                    />

                    <ErrorMessage
                      name="experienceRequired"
                      component="div"
                      className="text-danger"
                    />
                  </div>

                  <div className="col-md-6 mb-3">
                    <label>Vacancies</label>

                    <Field
                      type="number"
                      name="vacancies"
                      className="form-control"
                      className="form-control"
                      onKeyDown={(e) => {
                        if (e.key === "-" || e.key === "e") {
                          e.preventDefault();
                        }
                      }}
                    />

                    <ErrorMessage
                      name="vacancies"
                      component="div"
                      className="text-danger"
                    />
                  </div>
                </div>

                <div className="row">
                  <div className="col-md-6 mb-3">
                    <label>Minimum Salary</label>

                    <Field
                      type="number"
                      name="salaryMin"
                      className="form-control"
                      className="form-control"
                      onKeyDown={(e) => {
                        if (e.key === "-" || e.key === "e") {
                          e.preventDefault();
                        }
                      }}
                    />

                    <ErrorMessage
                      name="salaryMin"
                      component="div"
                      className="text-danger"
                    />
                  </div>

                  <div className="col-md-6 mb-3">
                    <label>Maximum Salary</label>

                    <Field
                      type="number"
                      name="salaryMax"
                      className="form-control"
                      className="form-control"
                      onKeyDown={(e) => {
                        if (e.key === "-" || e.key === "e") {
                          e.preventDefault();
                        }
                      }}
                    />

                    <ErrorMessage
                      name="salaryMax"
                      component="div"
                      className="text-danger"
                    />
                  </div>
                </div>

                <div className="mb-3">
                  <label>Job Type</label>

                  <Field as="select" name="jobType" className="form-select">
                    <option value="">Select Job Type</option>
                    <option value="Full Time">Full Time</option>
                    <option value="Part Time">Part Time</option>
                    <option value="Internship">Internship</option>
                  </Field>

                  <ErrorMessage
                    name="jobType"
                    component="div"
                    className="text-danger"
                  />
                </div>

                <div className="mb-3">
                  <label>Application Deadline</label>

                  <Field
                    type="date"
                    name="applicationDeadline"
                    className="form-control"
                    min={today.toISOString().split("T")[0]}
                    max={maxDate.toISOString().split("T")[0]}
                  />

                  <ErrorMessage
                    name="applicationDeadline"
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
                  Add Job
                </button>
              </Form>
            </Formik>
          </div>
        </div>
      </div>
    </>
  );
}

export default AddJob;
