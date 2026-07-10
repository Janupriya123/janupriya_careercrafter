import { Link, useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  const role = localStorage.getItem("role");
  const fullName = localStorage.getItem("fullName");

  const logout = () => {
    localStorage.clear();
    navigate("/login", { replace: true });
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">
        <Link
  className="navbar-brand"
  to={role === "EMPLOYER" ? "/employer" : "/jobseeker"}
>
  CareerCrafter
</Link>

        <div className="collapse navbar-collapse">
          <ul className="navbar-nav me-auto">
            {role === "EMPLOYER" && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/employer">
                    Home
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to="/notifications" className="nav-link">
                    🔔 Notifications             
                  </Link>
                </li>

                <li className="nav-item">
                  <Link className="nav-link" to="/add-job">
                    Add Job
                  </Link>
                </li>
                                 
                <li className="nav-item">
                  <Link className="nav-link" to="/my-jobs">
                    My Jobs
                  </Link>
                </li>
              </>
            )}

            {role === "JOBSEEKER" && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/jobseeker">
                    Home
                  </Link>
                </li>
                <li className="nav-item">
  <Link className="nav-link" to="/notifications">
    🔔 Notifications
  </Link>
</li>

                <li className="nav-item">
                  <Link className="nav-link" to="/search-jobs">
                    Search Jobs
                  </Link>
                </li>

                <li className="nav-item">
                  <Link className="nav-link" to="/my-applications">
                    Applications
                  </Link>
                </li>

                <li className="nav-item">
                  <Link className="nav-link" to="/resume">
                    Resume
                  </Link>
                </li>
              </>
            )}
          </ul>

          <span className="navbar-text text-white me-3">
            Welcome {fullName}
          </span>

          <button className="btn btn-danger" onClick={logout}>
            Logout
          </button>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;

