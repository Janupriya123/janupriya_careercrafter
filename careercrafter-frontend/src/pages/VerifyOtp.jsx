import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import AuthService from "../services/AuthService";

function VerifyOtp() {
  const location = useLocation();
  const navigate = useNavigate();

  const email = location.state.email;

  const [otp, setOtp] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const verifyOtp = async () => {
    setMessage("");
    setError("");

    try {
      await AuthService.verifyOtp({
        email,
        otp,
      });

      setMessage("OTP Verified Successfully");

      setTimeout(() => {
        navigate("/reset-password", {
          state: { email },
        });
      }, 1500);
    } catch (error) {
      if (error.response) {
        setError(error.response.data);
      } else {
        setError("Invalid OTP");
      }
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-5">
          <div className="card shadow">
            <div className="card-header text-center">
              <h3>Verify OTP</h3>
            </div>

            <div className="card-body">
              {message && <div className="alert alert-success">{message}</div>}

              {error && <div className="alert alert-danger">{error}</div>}

              <label>OTP</label>

              <input
                type="text"
                className="form-control mb-3"
                value={otp}
                onChange={(e) => setOtp(e.target.value)}
              />

              <button className="btn btn-primary w-100" onClick={verifyOtp}>
                Verify OTP
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default VerifyOtp;
