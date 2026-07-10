import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import AuthService from "../services/AuthService";

function ResetPassword() {
  const location = useLocation();
  const navigate = useNavigate();

  const email = location.state.email;

  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const resetPassword = async () => {
    setMessage("");
    setError("");

    if (newPassword !== confirmPassword) {
      setError("Passwords do not match");

      return;
    }

    try {
      await AuthService.resetPassword({
        email,
        newPassword,
      });

      setMessage("Password Reset Successfully");

      setTimeout(() => {
        navigate("/login");
      }, 1500);
    } catch (error) {
      if (error.response) {
        setError(error.response.data);
      } else {
        setError("Unable to reset password");
      }
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-5">
          <div className="card shadow">
            <div className="card-header text-center">
              <h3>Reset Password</h3>
            </div>

            <div className="card-body">
              {message && <div className="alert alert-success">{message}</div>}

              {error && <div className="alert alert-danger">{error}</div>}

              <label>New Password</label>

              <input
                type="password"
                className="form-control mb-3"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
              />

              <label>Confirm Password</label>

              <input
                type="password"
                className="form-control mb-3"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />

              <button className="btn btn-success w-100" onClick={resetPassword}>
                Reset Password
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ResetPassword;
