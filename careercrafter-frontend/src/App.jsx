import { Routes, Route } from "react-router-dom";

import Login from "./pages/auth/Login";
import Register from "./pages/auth/Register";
import AddJob from "./pages/employer/AddJob";
import EmployerDashboard from "./pages/employer/Dashboard";
import JobSeekerDashboard from "./pages/jobseeker/Dashboard";
import JobList from "./pages/employer/JobList";
import CreateEmployerProfile from "./pages/employer/CreateEmployerProfile";
import ProtectedRoute from "./components/ProtectedRoute";
import CreateProfile from "./pages/jobseeker/CreateProfile";
import UpdateJob from "./pages/employer/UpdateJob";
import UpdateEmployer from "./pages/employer/UpdateEmployer";
import ViewApplications from "./pages/employer/ViewApplications";
import SearchJobs from "./pages/jobseeker/SearchJobs";
import ApplyJob from "./pages/jobseeker/ApplyJob";
import MyApplications from "./pages/jobseeker/MyApplications";
import UpdateProfile from "./pages/jobseeker/UpdateProfile";
import Resume from "./pages/jobseeker/Resume";
import JobDetails from "./pages/jobseeker/JobDetails";
import ForgotPassword from "./pages/ForgotPassword";
import VerifyOtp from "./pages/VerifyOtp";
import ResetPassword from "./pages/ResetPassword";
import Notifications from "./components/Notifications";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route
        path="/employer"
        element={
          <ProtectedRoute allowedRole="EMPLOYER">
            <EmployerDashboard />
          </ProtectedRoute>
        }
      />
      <Route path="/forgot-password" element={<ForgotPassword />} />
      <Route
        path="/update-profile"
        element={
          <ProtectedRoute allowedRole="JOBSEEKER">
            <UpdateProfile />
          </ProtectedRoute>
        }
      />{" "}
      <Route
        path="/resume"
        element={
          <ProtectedRoute allowedRole="JOBSEEKER">
            <Resume />
          </ProtectedRoute>
        }
      />
      <Route
        path="/notifications"
        element={
          <ProtectedRoute allowedRole={["EMPLOYER", "JOBSEEKER"]}>
            <Notifications />
          </ProtectedRoute>
        }
      />
      <Route path="/reset-password" element={<ResetPassword />} />
      <Route
        path="/apply-job/:jobId"
        element={
          <ProtectedRoute allowedRole="JOBSEEKER">
            <ApplyJob />
          </ProtectedRoute>
        }
      />
      <Route
        path="/search-jobs"
        element={
          <ProtectedRoute allowedRole="JOBSEEKER">
            <SearchJobs />
          </ProtectedRoute>
        }
      />
      <Route path="/verify-otp" element={<VerifyOtp />} />
      <Route
        path="/update-employer"
        element={
          <ProtectedRoute allowedRole="EMPLOYER">
            <UpdateEmployer />
          </ProtectedRoute>
        }
      />
      <Route
        path="/job-details/:jobId"
        element={
          <ProtectedRoute allowedRole="JOBSEEKER">
            <JobDetails />
          </ProtectedRoute>
        }
      />
      <Route
        path="/my-applications"
        element={
          <ProtectedRoute allowedRole="JOBSEEKER">
            <MyApplications />
          </ProtectedRoute>
        }
      />
      <Route
        path="/update-job/:jobId"
        element={
          <ProtectedRoute allowedRole="EMPLOYER">
            <UpdateJob />
          </ProtectedRoute>
        }
      />
      <Route
        path="/applications"
        element={
          <ProtectedRoute allowedRole="EMPLOYER">
            <ViewApplications />
          </ProtectedRoute>
        }
      />
      <Route
        path="/my-jobs"
        element={
          <ProtectedRoute allowedRole="EMPLOYER">
            <JobList />
          </ProtectedRoute>
        }
      />
      <Route
        path="/add-job"
        element={
          <ProtectedRoute allowedRole="EMPLOYER">
            <AddJob />
          </ProtectedRoute>
        }
      />
      <Route
        path="/jobseeker"
        element={
          <ProtectedRoute allowedRole="JOBSEEKER">
            <JobSeekerDashboard />
          </ProtectedRoute>
        }
      />
      <Route
        path="/create-profile"
        element={
          <ProtectedRoute allowedRole="JOBSEEKER">
            <CreateProfile />
          </ProtectedRoute>
        }
      />
      <Route
        path="/create-employer"
        element={
          <ProtectedRoute allowedRole="EMPLOYER">
            <CreateEmployerProfile />
          </ProtectedRoute>
        }
      />
      <Route
        path="/applications/:jobId"
        element={
          <ProtectedRoute allowedRole="EMPLOYER">
            <ViewApplications />
          </ProtectedRoute>
        }
      />
    </Routes>
  );
}

export default App;
