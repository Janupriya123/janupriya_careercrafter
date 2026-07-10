import { useEffect, useState } from "react";
import Navbar from "./Navbar";
import NotificationService from "../services/NotificationService";

function Notifications() {

  const [notifications, setNotifications] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    loadNotifications();
  }, []);

  const loadNotifications = async () => {

    try {

      const userId = localStorage.getItem("userId");

      const response =
        await NotificationService.getNotifications(userId);

      setNotifications(response.data);

    } catch (err) {

      setError("Unable to load notifications");

    }

  };

  const markAsRead = async (notificationId) => {

    try {

      await NotificationService.markAsRead(notificationId);

      loadNotifications();

    } catch (err) {}

  };

  return (
    <>
      <Navbar />

      <div className="container mt-4">

        <h2 className="mb-4">
          <i className="bi bi-bell-fill text-warning me-2"></i>
          Notifications
        </h2>

        {error &&
          <div className="alert alert-danger">
            {error}
          </div>
        }

        {notifications.length === 0 ? (

          <div className="alert alert-info">
            No Notifications
          </div>

        ) : (

          notifications.map((notification) => (

            <div
              key={notification.notificationId}
              className={`card shadow-sm mb-3 ${
                notification.read
                  ? ""
                  : "border-primary"
              }`}
            >

              <div className="card-body">

                <div className="d-flex justify-content-between">

                  <div>

                    <h5 className="mb-2">
                      {notification.title}
                    </h5>

                    <p className="mb-2">
                      {notification.message}
                    </p>

                    <small className="text-muted">
                      {new Date(
                        notification.createdAt
                      ).toLocaleString("en-IN", {
                        day: "2-digit",
                        month: "short",
                        year: "numeric",
                        hour: "2-digit",
                        minute: "2-digit",
                        hour12: true,
                      })}
                    </small>

                  </div>

                  {!notification.read && (

                    <button
                      className="btn btn-sm btn-outline-primary"
                      onClick={() =>
                        markAsRead(notification.notificationId)
                      }
                    >
                      Mark as Read
                    </button>

                  )}

                </div>

              </div>

            </div>

          ))

        )}

      </div>
    </>
  );
}

export default Notifications;