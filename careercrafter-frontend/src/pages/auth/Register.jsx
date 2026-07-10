import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import AuthService from "../../services/AuthService";

function Register() {
  const navigate = useNavigate();
  const [registerError, setRegisterError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const initialValues = {
    fullName: "",
    email: "",
    password: "",
    phone: "",
    role: "",
    active: true,
  };

  const validationSchema = Yup.object({
    fullName: Yup.string()
      .required("Full name is required")
      .matches(/^[A-Za-z]+([ '-][A-Za-z]+)*$/, "Enter a valid full name"),

    email: Yup.string()
      .email("Invalid Email")
      .required("Email is required")
      .matches(
        /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/,
        "Enter a valid email address",
      ),

    password: Yup.string()
      .min(6, "Password must be at least 6 characters")
      .required("Password is required"),

    phone: Yup.string()
      .matches(/^[0-9]{10}$/, "Phone number must contain 10 digits")
      .required("Phone number is required"),

    role: Yup.string().required("Please select a role"),
  });

  const onSubmit = async (values) => {
    setRegisterError("");
    setSuccessMessage("");

    try {
      await AuthService.register(values);
      setSuccessMessage("Registration Successful");
      setTimeout(() => {
        navigate("/login");
      }, 1500);
    } catch (error) {
      if (error.response) {
        if (typeof error.response.data === "string") {
          setRegisterError(error.response.data);
        } else {
          const errors = Object.values(error.response.data);
          setRegisterError(errors.join(", "));
        }
      } else {
        setRegisterError("Registration Failed");
      }
    }
  };

  return (
    <>
      <style>{`
body{
    background:#f5f7fa;
}

.register-card{
    border:none;
    border-radius:20px;
    overflow:hidden;
}

.register-left{
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
    background:white;
    border-radius:50%;
    display:flex;
    align-items:center;
    justify-content:center;
    margin-bottom:20px;
}

.logo-circle i{
    font-size:50px;
    color:#2f80ed;
}

.register-right{
    padding:40px;
}

.form-control,
.form-select{
    border-radius:12px;
    height:48px;
}

.input-group .btn{
    border-radius:0 12px 12px 0;
}

.btn-register{
    background:#2f80ed;
    color:white;
    border:none;
    border-radius:12px;
    height:48px;
    font-weight:600;
}

.btn-register:hover{
    background:#1565c0;
    color:white;
}

a{
    text-decoration:none;
}

a:hover{
    text-decoration:underline;
}
`}</style>

      <div className="container py-5">
        <div className="row justify-content-center">
          <div className="col-lg-11">
            <div className="card shadow-lg register-card">
              <div className="row g-0">
                {/* Left Side */}

                <div className="col-md-5 register-left">
                  <div className="logo-circle">
                    <i className="bi bi-briefcase-fill"></i>
                  </div>

                  <h2 className="fw-bold">CareerCrafter</h2>

                  <p className="text-center mt-3">
                    Create your account and start your career journey.
                  </p>

                  <i
                    className="bi bi-person-plus-fill"
                    style={{ fontSize: "120px" }}
                  ></i>
                </div>

                {/* Right Side */}

                <div className="col-md-7 register-right">
                  <h2 className="text-center fw-bold mb-4">Create Account</h2>

                  <Formik
                    initialValues={initialValues}
                    validationSchema={validationSchema}
                    validateOnChange={true}
                    validateOnBlur={true}
                    onSubmit={onSubmit}
                  >
                    <Form>
                      <div className="mb-3">
                        <label className="form-label">Full Name</label>

                        <Field
                          type="text"
                          name="fullName"
                          className="form-control"
                          placeholder="Enter your full name"
                        />

                        <ErrorMessage
                          name="fullName"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      <div className="mb-3">
                        <label className="form-label">Email</label>

                        <Field
                          type="email"
                          name="email"
                          className="form-control"
                          placeholder="Enter your email"
                        />

                        <ErrorMessage
                          name="email"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      <div className="mb-3">
                        <label className="form-label">Password</label>

                        <div className="input-group">
                          <Field
                            type={showPassword ? "text" : "password"}
                            name="password"
                            className="form-control"
                            placeholder="Enter password"
                          />

                          <button
                            type="button"
                            className="btn btn-outline-secondary"
                            onClick={() => setShowPassword(!showPassword)}
                          >
                            <i
                              className={`bi ${
                                showPassword ? "bi-eye" : "bi-eye-slash"
                              }`}
                            />
                          </button>
                        </div>

                        <ErrorMessage
                          name="password"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      <div className="mb-3">
                        <label className="form-label">Phone Number</label>

                        <Field
                          type="text"
                          name="phone"
                          className="form-control"
                          placeholder="Enter phone number"
                          maxLength={10}
                          onInput={(e) => {
                            e.target.value = e.target.value.replace(/\D/g, "");
                          }}
                        />

                        <ErrorMessage
                          name="phone"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      <div className="mb-3">
                        <label className="form-label">Role</label>

                        <Field as="select" name="role" className="form-select">
                          <option value="">Select Role</option>
                          <option value="EMPLOYER">Employer</option>
                          <option value="JOBSEEKER">Job Seeker</option>
                        </Field>

                        <ErrorMessage
                          name="role"
                          component="div"
                          className="text-danger"
                        />
                      </div>

                      {registerError && (
                        <div className="alert alert-danger">
                          {registerError}
                        </div>
                      )}

                      {successMessage && (
                        <div className="alert alert-success">
                          {successMessage}
                        </div>
                      )}

                      <button type="submit" className="btn btn-register w-100">
                        <i className="bi bi-person-check-fill me-2"></i>
                        Create Account
                      </button>
                    </Form>
                  </Formik>

                  <div className="text-center mt-4">
                    Already have an account?
                    <Link to="/login" className="ms-2 fw-bold">
                      Login
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Register;
