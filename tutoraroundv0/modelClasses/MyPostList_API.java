package com.example.panchal.tutoraroundv0.modelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class MyPostList_API implements Parcelable {

    String id, date,time,description,course_name;

    public MyPostList_API(Parcel in) {
        id = in.readString();
        date = in.readString();
        time = in.readString();
        description = in.readString();
        course_name = in.readString();
    }

    public static final Creator<MyPostList_API> CREATOR = new Creator<MyPostList_API>() {
        @Override
        public MyPostList_API createFromParcel(Parcel in) {
            return new MyPostList_API(in);
        }

        @Override
        public MyPostList_API[] newArray(int size) {
            return new MyPostList_API[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public MyPostList_API(String id, String date, String time, String description, String course_name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.description = description;
        this.course_name = course_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeString(description);
        parcel.writeString(course_name);
    }
}
