package com.example.panchal.tutoraroundv0.modelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationList_API implements Parcelable {

    String id, user_id, notification_type, message;

    protected NotificationList_API(Parcel in) {
        id = in.readString();
        user_id = in.readString();
        notification_type = in.readString();
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user_id);
        dest.writeString(notification_type);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationList_API> CREATOR = new Creator<NotificationList_API>() {
        @Override
        public NotificationList_API createFromParcel(Parcel in) {
            return new NotificationList_API(in);
        }

        @Override
        public NotificationList_API[] newArray(int size) {
            return new NotificationList_API[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationList_API(String id, String user_id, String notification_type, String message) {
        this.id = id;
        this.user_id = user_id;
        this.notification_type = notification_type;
        this.message = message;
    }
}
