import Navbar from "../../components/Navbar";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import JobService from "../../services/JobService";
import ApplicationService from "../../services/ApplicationService";

function Dashboard() {

    const navigate = useNavigate();

    const [totalJobs, setTotalJobs] = useState(0);
    const [openJobs, setOpenJobs] = useState(0);
    const [totalApplications, setTotalApplications] = useState(0);

    useEffect(() => {
        loadDashboard();
    }, []);

    const loadDashboard = async () => {

        try{

            const employerId = localStorage.getItem("employerId");

            const jobResponse = await JobService.getJobsByEmployerId(employerId);

            const jobs = jobResponse.data;

            setTotalJobs(jobs.length);

            const open = jobs.filter(job => job.status === "OPEN");

            setOpenJobs(open.length);

            const applicationResponse = await ApplicationService.getAllApplications();

            let count = 0;

            applicationResponse.data.forEach(application => {

                if(jobs.some(job => job.jobId === application.jobId)){
                    count++;
                }

            });

            setTotalApplications(count);

        }
        catch(error){

            console.log(error);

        }

    };

    return(

        <>

        <style>{`

body{
    background:#f4f7fb;
}

.welcome-card{
    background:white;
    border:none;
    border-radius:18px;
}

.stat-card{
    border:none;
    border-radius:18px;
    color:white;
    transition:.3s;
}

.stat-card:hover{
    transform:translateY(-5px);
}

.dashboard-card{
    border:none;
    border-radius:18px;
    transition:.3s;
    cursor:pointer;
}

.dashboard-card:hover{
    transform:translateY(-8px);
    box-shadow:0 12px 25px rgba(0,0,0,.15);
}

.dashboard-btn{
    border-radius:30px;
    padding:10px 28px;
}

.dashboard-card i{
    transition:.3s;
}

.dashboard-card:hover i{
    transform:scale(1.15);
}

        `}</style>

        <Navbar/>

        <div className="container py-4">

            {/* Welcome */}

            <div className="card welcome-card shadow-sm mb-4">

                <div className="card-body">

                    <h2 className="fw-bold">

                        <i className="bi bi-building me-2 text-primary"></i>

                        Employer Dashboard

                    </h2>

                    <p className="text-muted mb-0">

                        Welcome back,

                        <strong> {localStorage.getItem("fullName")}</strong>

                    </p>

                </div>

            </div>

            {/* Statistics */}

            <div className="row mb-4">

                <div className="col-md-4 mb-3">

                    <div
                    className="card stat-card shadow"
                    style={{background:"#2F80ED"}}
                    >

                        <div className="card-body text-center">

                            <i className="bi bi-briefcase-fill display-5"></i>

                            <h5 className="mt-3">

                                Total Jobs

                            </h5>

                            <h2>{totalJobs}</h2>

                        </div>

                    </div>

                </div>

                <div className="col-md-4 mb-3">

                    <div
                    className="card stat-card shadow"
                    style={{background:"#27AE60"}}
                    >

                        <div className="card-body text-center">

                            <i className="bi bi-check-circle-fill display-5"></i>

                            <h5 className="mt-3">

                                Open Jobs

                            </h5>

                            <h2>{openJobs}</h2>

                        </div>

                    </div>

                </div>

                <div className="col-md-4 mb-3">

                    <div
                    className="card stat-card shadow"
                    style={{background:"#F39C12"}}
                    >

                        <div className="card-body text-center">

                            <i className="bi bi-file-earmark-text-fill display-5"></i>

                            <h5 className="mt-3">

                                Applications

                            </h5>

                            <h2>{totalApplications}</h2>

                        </div>

                    </div>

                </div>

            </div>

            {/* Dashboard Cards */}

            <div className="row">

                <div className="col-md-4 mb-4">

                    <div
                    className="card dashboard-card shadow h-100"
                    onClick={()=>navigate("/add-job")}
                    >

                        <div className="card-body text-center py-5">

                            <i className="bi bi-plus-circle-fill display-2 text-primary"></i>

                            <h4 className="mt-4">

                                Add Job

                            </h4>

                            <p className="text-muted">

                                Publish a new job opportunity.

                            </p>

                            <button
                            className="btn btn-primary dashboard-btn"
                            onClick={(e)=>{

                                e.stopPropagation();

                                navigate("/add-job");

                            }}
                            >

                                Add Job

                            </button>

                        </div>

                    </div>

                </div>

                <div className="col-md-4 mb-4">

                    <div
                    className="card dashboard-card shadow h-100"
                    onClick={()=>navigate("/my-jobs")}
                    >

                        <div className="card-body text-center py-5">

                            <i className="bi bi-briefcase-fill display-2 text-success"></i>

                            <h4 className="mt-4">

                                Manage Jobs

                            </h4>

                            <p className="text-muted">

                                View, edit and delete posted jobs.

                            </p>

                            <button
                            className="btn btn-success dashboard-btn"
                            onClick={(e)=>{

                                e.stopPropagation();

                                navigate("/my-jobs");

                            }}
                            >

                                My Jobs

                            </button>

                        </div>

                    </div>

                </div>

                <div className="col-md-4 mb-4">

                    <div
                    className="card dashboard-card shadow h-100"
                    onClick={()=>navigate("/update-employer")}
                    >

                        <div className="card-body text-center py-5">

                            <i className="bi bi-building-fill-gear display-2 text-info"></i>

                            <h4 className="mt-4">

                                Company Profile

                            </h4>

                            <p className="text-muted">

                                Update your company details.

                            </p>

                            <button
                            className="btn btn-info text-white dashboard-btn"
                            onClick={(e)=>{

                                e.stopPropagation();

                                navigate("/update-employer");

                            }}
                            >

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