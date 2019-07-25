package com.example.panchal.tutoraroundv0.modelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Course_list_API implements Parcelable {

    String id,course_name,status;

    protected Course_list_API(Parcel in) {
        id = in.readString();
        course_name = in.readString();
        status = in.readString();
    }

    public static final Creator<Course_list_API> CREATOR = new Creator<Course_list_API>() {
        @Override
        public Course_list_API createFromParcel(Parcel in) {
            return new Course_list_API(in);
        }

        @Override
        public Course_list_API[] newArray(int size) {
            return new Course_list_API[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Course_list_API(String id, String course_name, String status) {
        this.id = id;
        this.course_name = course_name;
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(course_name);
        parcel.writeString(status);
    }

    @Override
    public String toString() {
        return course_name;
    }
}
