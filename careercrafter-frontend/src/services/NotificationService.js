import http from "../http-common";

class NotificationService{

    createNotification(data){
        return http.post("/notification/create",data);
    }

    getNotifications(userId){
        return http.get(`/notification/user/${userId}`);
    }

    markAsRead(notificationId){
        return http.put(`/notification/read/${notificationId}`);
    }

}

export default new NotificationService();