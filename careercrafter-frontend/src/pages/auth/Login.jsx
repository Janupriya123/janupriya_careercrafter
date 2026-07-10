import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import AuthService from "../../services/AuthService";

function Login() {
  const navigate = useNavigate();
  const [loginError, setLoginError] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const initialValues = {
    email: "",
    password: "",
  };
  const validationSchema = Yup.object({
    email: Yup.string()
      .email("Invalid Email")
      .required("Email is required")
      .matches(
        /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/,
        "Enter a valid email address",
      ),
    password: Yup.string().required("Password is required"),
  });

  const onSubmit = async (values) => {
    setLoginError("");
    try {
      const response = await AuthService.login(values);

      console.log(response.data);
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("userId", response.data.userId);
      localStorage.setItem("role", response.data.role);
      localStorage.setItem("fullName", response.data.fullName);
      if (response.data.role === "EMPLOYER") {
        try {
          const employerResponse = await AuthService.getEmployerByUserId(
            response.data.userId,
          );

          localStorage.setItem("employerId", employerResponse.data.employerId);

          navigate("/employer");
        } catch (error) {
          if (error.response && error.response.status === 404) {
            navigate("/create-employer");
          } else {
            setLoginError("Unable to load employer profile");
          }
        }
      } else {
        try {
          const profileResponse = await AuthService.getProfileByUserId(
            response.data.userId,
          );
          console.log(profileResponse.data);

          localStorage.setItem("profileId", profileResponse.data.profileId);

          navigate("/jobseeker");
        } catch (error) {
          if (error.response && error.response.status === 404) {
            navigate("/create-profile");
          } else {
            setLoginError("Unable to load profile");
          }
        }
      }
    } catch (error) {
      localStorage.clear();

      if (error.response) {
        setLoginError(error.response.data);
      } else {
        setLoginError("Unable to login");
      }
    }
  };

  return (
    <>
      <style>{`
 

.login-card{
    border:none;
    border-radius:20px;
    overflow:hidden;
}

.login-left{
   
    color:white;
    display:flex;
    flex-direction:column;
    justify-content:center;
    align-items:center;
    padding:40px;
    min-height:550px;
}

.login-right{
    padding:40px;
}

.logo-circle{
    width:100px;
    height:100px;
    border-radius:50%;
    background:white;
    display:flex;
    align-items:center;
    justify-content:center;
    margin-bottom:20px;
}

.logo-circle i{
    font-size:50px;
    color:#6a11cb;
}

.form-control{
    border-radius:12px;
    height:48px;
}

.input-group .btn{
    border-radius:0 12px 12px 0;
}

.btn-login{
    background:linear-gradient(90deg,#6a11cb,#2575fc);
    color:white;
    border:none;
    border-radius:12px;
    height:48px;
    font-weight:bold;
    transition:.3s;
}
    .btn-login{
    background:#4f8df7;
}

.btn-login:hover{
    background:#3d7be6;
}

.btn-login:hover{
    transform:translateY(-2px);
}
body{
    background: linear-gradient(135deg,#eef5ff,#d9e9ff);
}
    .login-left{
    background: linear-gradient(135deg,#4f8df7,#6aa8ff);
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
          <div className="col-lg-10">
            <div className="card shadow-lg login-card">
              <div className="row g-0">
                {/* Left Side */}

                <div className="col-md-5 login-left">
                  <div className="logo-circle">
                    <i className="bi bi-briefcase-fill"></i>
                  </div>

                  <h2 className="fw-bold">CareerCrafter</h2>

                  <p className="text-center mt-3">
                    Find your dream job and build your future with us.
                  </p>

                  <i
                    className="bi bi-person-workspace"
                    style={{ fontSize: "130px" }}
                  ></i>
                </div>

                {/* Right Side */}

                <div className="col-md-7 login-right">
                  <h2 className="text-center fw-bold mb-4">Login</h2>

                  <Formik
                    initialValues={initialValues}
                    validationSchema={validationSchema}
                    onSubmit={onSubmit}
                  >
                    {() => (
                      <Form>
                        <div className="mb-3">
                          <label className="form-label">Email Address</label>

                          <Field
                            type="email"
                            name="email"
                            className="form-control"
                            placeholder="Enter your email"
                            onFocus={() => setLoginError("")}
                          />

                          <ErrorMessage
                            name="email"
                            component="div"
                            className="text-danger mt-1"
                          />
                        </div>

                        <div className="mb-3">
                          <label className="form-label">Password</label>

                          <div className="input-group">
                            <Field
                              type={showPassword ? "text" : "password"}
                              name="password"
                              className="form-control"
                              placeholder="Enter your password"
                              onFocus={() => setLoginError("")}
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
                            className="text-danger mt-1"
                          />
                        </div>

                        {loginError && (
                          <div className="alert alert-danger">{loginError}</div>
                        )}

                        <button type="submit" className="btn btn-login w-100">
                          <i className="bi bi-box-arrow-in-right me-2"></i>
                          Login
                        </button>

                        <div className="text-end mt-3">
                          <Link to="/forgot-password">Forgot Password?</Link>
                        </div>

                        <div className="text-center mt-4">
                          Don't have an account?
                          <Link to="/register" className="ms-2 fw-bold">
                            Register
                          </Link>
                        </div>
                      </Form>
                    )}
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

export default Login;
