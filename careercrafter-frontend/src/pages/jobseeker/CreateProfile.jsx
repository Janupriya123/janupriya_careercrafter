import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import ProfileService from "../../services/ProfileService";
import Navbar from "../../components/Navbar";

function CreateProfile() {
  const navigate = useNavigate();

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const initialValues = {
    userId: localStorage.getItem("userId"),
    gender: "",
    address: "",
    education: "",
    skills: "",
    experienceYears: "",
    currentCompany: "",
    currentSalary: "",
    expectedSalary: "",
    profileSummary: "",
  };

    const validationSchema = Yup.object({
     gender: Yup.string()
       .required("Gender is required")
       .matches(/^[A-Za-z ]+$/, "Gender can contain only letters"),
   
     address: Yup.string()
       .required("Address is required")
       .matches(/[A-Za-z]/, "Address must contain letters"),
   
      education: Yup.string()
       .required("Education is required")
       .matches(
         /^(?=.*[A-Za-z])[A-Za-z0-9.,()\- ]+$/,
         "Education must contain at least one letter"
       ),
   
     skills: Yup.string()
       .required("Skills are required")
       .matches(/^[A-Za-z, ]+$/, "Skills can contain only letters and commas"),
   
       experienceYears: Yup.number()
       .typeError("Experience must be a number")
       .required("Experience is required")
       .min(0, "Experience cannot be negative")
       .max(50, "Experience cannot exceed 50 years"),
   
      currentCompany: Yup.string()
       .trim()
       .required("Current company is required")
       .matches(
         /^(?=.*[A-Za-z])[A-Za-z0-9&.,()'\/ -]+$/,
         "Company name must contain at least one letter"
       ),
   
     currentSalary: Yup.number()
       .typeError("Current salary must be a number")
       .required("Current salary is required")
       .moreThan(0, "Current salary must be greater than 0")
       .max(100000000, "Current salary is too high"),
   
     expectedSalary: Yup.number()
       .typeError("Expected salary must be a number")
       .required("Expected salary is required")
       .moreThan(0, "Expected salary must be greater than 0")
       .test(
         "greater-than-current",
         "Expected salary must be greater than current salary",
         function (value) {
           const { currentSalary } = this.parent;
           if (!value || !currentSalary) return true;
           return value > currentSalary;
         }
       ),
   
     profileSummary: Yup.string()
       .required("Profile summary is required")
       .matches(/[A-Za-z]/, "Profile summary must contain letters"),
   });

  const onSubmit = async (values) => {
    setError("");
    setSuccess("");

    try {
      const response = await ProfileService.createProfile(values);

      localStorage.setItem("profileId", response.data.profileId);

      setSuccess("Profile Created Successfully");

      setTimeout(() => {
        navigate("/jobseeker");
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

.profile-card{
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
    min-height:820px;
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
            <div className="card shadow-lg profile-card">
              <div className="row g-0">
                <div className="col-md-4 left-panel">
                  <div className="logo-circle">
                    <i className="bi bi-person-badge-fill"></i>
                  </div>

                  <h2 className="fw-bold">CareerCrafter</h2>

                  <p className="text-center mt-3">
                    Complete your profile to start applying for your dream jobs.
                  </p>

                  <i
                    className="bi bi-person-workspace"
                    style={{ fontSize: "120px" }}
                  ></i>
                </div>

                <div className="col-md-8 right-panel">
                  <h2 className="text-center fw-bold mb-4">
                    Create Job Seeker Profile
                  </h2>

                  <Formik
                    initialValues={initialValues}
                    validationSchema={validationSchema}
                    validateOnChange={true}
                    validateOnBlur={true}
                    onSubmit={onSubmit}
                  >
                    <Form>
                      <div className="mb-3">
                        <label>Gender</label>

                        <Field
                          as="select"
                          name="gender"
                          className="form-select"
                        >
                          <option value="">Select Gender</option>
                          <option value="Male">Male</option>
                          <option value="Female">Female</option>
                        </Field>

                        <ErrorMessage
                          name="gender"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      <div className="mb-3">
                        <label>Address</label>

                        <Field
                          name="address"
                          className="form-control"
                          placeholder="Enter Address"
                        />

                        <ErrorMessage
                          name="address"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      <div className="mb-3">
                        <label>Education</label>

                        <Field
                          name="education"
                          className="form-control"
                          placeholder="BE Computer Science"
                        />

                        <ErrorMessage
                          name="education"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      <div className="mb-3">
                        <label>Skills</label>

                        <Field
                          name="skills"
                          className="form-control"
                          placeholder="Java, Spring Boot, React"
                        />

                        <ErrorMessage
                          name="skills"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      <div className="row">
                        <div className="col-md-6">
                          <label>Experience (Years)</label>

                          <Field
                            type="number"
                            name="experienceYears"
                            className="form-control"
                            onKeyDown={(e) => {
                              if (
                                e.key === "-" ||
                                e.key === "e" ||
                                e.key === "E"
                              ) {
                                e.preventDefault();
                              }
                            }}
                          />

                          <ErrorMessage
                            name="experienceYears"
                            component="div"
                            className="text-danger"
                          />
                        </div>

                        <div className="col-md-6">
                          <label>Current Company</label>

                          <Field
                            name="currentCompany"
                            className="form-control"
                            placeholder="TCS / Infosys / Fresher"
                          />

                          <ErrorMessage
                            name="currentCompany"
                            component="div"
                            className="text-danger"
                          />
                        </div>
                      </div>

                      <br />

                      <div className="row">
                        <div className="col-md-6">
                          <label>Current Salary</label>

                          <Field
                            type="number"
                            name="currentSalary"
                            className="form-control"
                            onKeyDown={(e) => {
                              if (
                                e.key === "-" ||
                                e.key === "e" ||
                                e.key === "E"
                              ) {
                                e.preventDefault();
                              }
                            }}
                          />

                          <ErrorMessage
                            name="currentSalary"
                            component="div"
                            className="text-danger"
                          />
                        </div>

                        <div className="col-md-6">
                          <label>Expected Salary</label>

                          <Field
                            type="number"
                            name="expectedSalary"
                            className="form-control"
                            onKeyDown={(e) => {
                              if (
                                e.key === "-" ||
                                e.key === "e" ||
                                e.key === "E"
                              ) {
                                e.preventDefault();
                              }
                            }}
                          />

                          <ErrorMessage
                            name="expectedSalary"
                            component="div"
                            className="text-danger"
                          />
                        </div>
                      </div>

                      <br />

                      <div className="mb-3">
                        <label>Profile Summary</label>

                        <Field
                          as="textarea"
                          rows="5"
                          name="profileSummary"
                          className="form-control"
                          placeholder="Write a short summary about yourself..."
                        />

                        <ErrorMessage
                          name="profileSummary"
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

export default CreateProfile;
