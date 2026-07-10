import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {Formik,Form,Field,ErrorMessage} from "formik";
import * as Yup from "yup";
import AuthService from "../services/AuthService";

function ForgotPassword() {
  const navigate = useNavigate();

  const [message,setMessage]=useState("");
  const [error,setError]=useState("");

  const initialValues={
    email: "",
  };

  const validationSchema=Yup.object({
    email: Yup.string()
      .required("Email is required")
      .email("Enter a valid email address")
      .matches(
        /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/,
        "Enter a valid email address"
      ),
  });

  const onSubmit=async (values) => {
    setMessage("");
    setError("");

    try {
      await AuthService.forgotPassword(values);

      setMessage("OTP sent successfully");

      setTimeout(()=>{
        navigate("/verify-otp", {
          state: { email: values.email },
        });
      }, 1500);
    } catch (error) {
      if (error.response) {
        if (typeof error.response.data==="string") {
          setError(error.response.data);
        } else {
          setError(Object.values(error.response.data).join(", "));
        }
      } else {
        setError("Unable to send OTP");
      }
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-5">
          <div className="card shadow">
            <div className="card-header text-center">
              <h3>Forgot Password</h3>
            </div>

            <div className="card-body">
              <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                validateOnBlur={true}
                validateOnChange={true}
                onSubmit={onSubmit}
              >
                <Form>
                  <div className="mb-3">
                    <label>Email</label>

                    <Field
                      type="email"
                      name="email"
                      className="form-control"
                    />

                    <ErrorMessage
                      name="email"
                      component="div"
                      className="text-danger"
                    />
                  </div>

                  {message && (
                    <div className="alert alert-success">
                      {message}
                    </div>
                  )}

                  {error && (
                    <div className="alert alert-danger">
                      {error}
                    </div>
                  )}

                  <button
                    type="submit"
                    className="btn btn-primary w-100"
                  >
                    Send OTP
                  </button>
                </Form>
              </Formik>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ForgotPassword;
