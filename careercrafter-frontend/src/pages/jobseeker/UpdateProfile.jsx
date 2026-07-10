import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import Navbar from "../../components/Navbar";

import ProfileService from "../../services/ProfileService";

function UpdateProfile() {
  const navigate = useNavigate();

  const [data, setData] = useState(null);
  const [error, setError] = useState("");
  const[success,setSuccess]=useState("");

  useEffect(() => {
    loadProfile();
  }, []);

  const loadProfile = async () => {
    try {
      const profileId = localStorage.getItem("profileId");
      const response = await ProfileService.getProfileById(profileId);

      console.log(profileId);

      setData(response.data);
    } catch (error) {
      setError("Unable to load profile");
    }
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
      await ProfileService.updateProfile(values.profileId, values);
      setSuccess("Profile Updated Successfully");
      setTimeout(() => {
        navigate("/jobseeker");
      }, 1500);
    } catch (error) {
      setError("Unable to update profile");
    }
  };

  if (data === null) {
    return (
      <>
        <Navbar />
        <div className="container mt-5 text-center">Loading...</div>
      </>
    );
  }

  return (
    <>
      <Navbar />

      <div className="container mt-4">
        <div className="card shadow">
          <div className="card-header text-center">
            <h3>Update Profile</h3>
          </div>

          <div className="card-body">
            <Formik
              initialValues={data}
              validationSchema={validationSchema}
              enableReinitialize={true}
              onSubmit={onSubmit}
            >
              <Form>
                <div className="mb-3">
                  <label>Gender</label>
                  <Field name="gender" className="form-control" />
                  <ErrorMessage
                    name="gender"
                    component="div"
                    className="text-danger"
                  />
                </div>

                <div className="mb-3">
                  <label>Address</label>
                  <Field name="address" className="form-control" />
                  <ErrorMessage
                    name="address"
                    component="div"
                    className="text-danger"
                  />
                </div>

                <div className="mb-3">
                  <label>Education</label>
                  <Field name="education" className="form-control" />
                  <ErrorMessage
                    name="education"
                    component="div"
                    className="text-danger"
                  />
                </div>

                <div className="mb-3">
                  <label>Skills</label>
                  <Field name="skills" className="form-control" />
                  <ErrorMessage
                    name="skills"
                    component="div"
                    className="text-danger"
                  />
                </div>

                <div className="mb-3">
                  <label>Experience</label>
                  <Field
                    type="number"
                    name="experienceYears"
                    className="form-control"
                     onKeyDown={(e) => {
    if (["e", "E", "+", "-"].includes(e.key)) {
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

                <div className="mb-3">
                  <label>Current Company</label>
                  <Field name="currentCompany" className="form-control" />
                </div>

                <div className="mb-3">
                  <label>Current Salary</label>
                  <Field
                    type="number"
                    name="currentSalary"
                    className="form-control"
                     onKeyDown={(e) => {
    if (["e", "E", "+", "-"].includes(e.key)) {
      e.preventDefault();
    }
  }}
                  />
                </div>

                <div className="mb-3">
                  <label>Expected Salary</label>
                  <Field
                    type="number"
                    name="expectedSalary"
                    className="form-control"
                     onKeyDown={(e) => {
    if (["e", "E", "+", "-"].includes(e.key)) {
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

                <div className="mb-3">
                  <label>Profile Summary</label>
                  <Field
                    as="textarea"
                    rows="4"
                    name="profileSummary"
                    className="form-control"
                  />
                  <ErrorMessage
                    name="profileSummary"
                    component="div"
                    className="text-danger"
                  />
                </div>

                {error && <div className="text-danger mb-3">{error}</div>}

{success && <div className="text-success mb-3">{success}</div>}

<button type="submit" className="btn btn-primary w-100">
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

export default UpdateProfile;
