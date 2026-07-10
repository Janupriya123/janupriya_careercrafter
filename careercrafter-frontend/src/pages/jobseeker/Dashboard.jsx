import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import Navbar from "../../components/Navbar";
import JobService from "../../services/JobService";
import ApplicationService from "../../services/ApplicationService";

function Dashboard() {

    const navigate = useNavigate();

    const [totalJobs, setTotalJobs] = useState(0);
    const [totalApplications, setTotalApplications] = useState(0);

    useEffect(() => {

        loadJobs();
        loadApplications();

    }, []);

    const loadJobs = () => {

        JobService.getAllJobs()
            .then((response) => {

                setTotalJobs(response.data.length);

            })
            .catch((error) => {

                console.log(error);

            });

    };

    const loadApplications = () => {

        const profileId = localStorage.getItem("profileId");

        ApplicationService.getApplicationsByJobSeekerId(profileId)
            .then((response) => {

                setTotalApplications(response.data.length);

            })
            .catch((error) => {

                console.log(error);

            });

    };

    return (

        <>
            <Navbar />

            <div className="container mt-4">

                <div className="card shadow-sm mb-4">

                    <div className="card-body">

                        <h2>
                            <i className="bi bi-person-workspace me-2 text-primary"></i>
                            Job Seeker Dashboard
                        </h2>

                        <p className="text-muted mb-0">
                            Welcome,
                            <strong> {localStorage.getItem("fullName")}</strong>
                        </p>

                    </div>

                </div>

                <div className="row mb-4">

                    <div className="col-md-6">

                        <div className="card text-white bg-primary shadow">

                            <div className="card-body text-center">

                                <i className="bi bi-search display-4"></i>

                                <h5 className="mt-3">
                                    Available Jobs
                                </h5>

                                <h2>{totalJobs}</h2>

                            </div>

                        </div>

                    </div>

                    <div className="col-md-6">

                        <div className="card text-white bg-success shadow">

                            <div className="card-body text-center">

                                <i className="bi bi-file-earmark-check display-4"></i>

                                <h5 className="mt-3">
                                    My Applications
                                </h5>

                                <h2>{totalApplications}</h2>

                            </div>

                        </div>

                    </div>

                </div>

                <div className="row">

                    <div className="col-md-4 mb-4">

                        <div
                            className="card shadow h-100"
                            style={{ cursor: "pointer" }}
                            onClick={() => navigate("/search-jobs")}
                        >

                            <div className="card-body text-center">

                                <i className="bi bi-search display-3 text-primary"></i>

                                <h4 className="mt-3">
                                    Search Jobs
                                </h4>

                                <p>
                                    Search and apply for jobs.
                                </p>

                                <button
                                    className="btn btn-primary"
                                    onClick={(e) => {

                                        e.stopPropagation();

                                        navigate("/search-jobs");

                                    }}
                                >

                                    <i className="bi bi-search me-2"></i>

                                    Search Jobs

                                </button>

                            </div>

                        </div>

                    </div>

                    <div className="col-md-4 mb-4">

                        <div
                            className="card shadow h-100"
                            style={{ cursor: "pointer" }}
                            onClick={() => navigate("/my-applications")}
                        >

                            <div className="card-body text-center">

                                <i className="bi bi-file-earmark-text display-3 text-success"></i>

                                <h4 className="mt-3">
                                    My Applications
                                </h4>

                                <p>
                                    View your applications.
                                </p>

                                <button
                                    className="btn btn-success"
                                    onClick={(e) => {

                                        e.stopPropagation();

                                        navigate("/my-applications");

                                    }}
                                >

                                    <i className="bi bi-file-earmark-text me-2"></i>

                                    My Applications

                                </button>

                            </div>

                        </div>

                    </div>

                    <div className="col-md-4 mb-4">

                        <div
                            className="card shadow h-100"
                            style={{ cursor: "pointer" }}
                            onClick={() => navigate("/update-profile")}
                        >

                            <div className="card-body text-center">

                                <i className="bi bi-person-fill-gear display-3 text-info"></i>

                                <h4 className="mt-3">
                                    Update Profile
                                </h4>

                                <p>
                                    Update your profile.
                                </p>

                                <button
                                    className="btn btn-info text-white"
                                    onClick={(e) => {

                                        e.stopPropagation();

                                        navigate("/update-profile");

                                    }}
                                >

                                    <i className="bi bi-pencil-square me-2"></i>

                                    Update Profile

                                </button>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </>

    );

}

export default Dashboard;