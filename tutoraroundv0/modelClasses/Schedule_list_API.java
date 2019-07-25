package com.example.panchal.tutoraroundv0.modelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedule_list_API implements Parcelable {

    String course_name;
    String date;
    String time;
    String fname;
    String rating;
    String id;

    protected Schedule_list_API(Parcel in) {
        course_name = in.readString();
        date = in.readString();
        time = in.readString();
        fname = in.readString();
        rating = in.readString();
        id = in.readString();
    }

    public static final Creator<Schedule_list_API> CREATOR = new Creator<Schedule_list_API>() {
        @Override
        public Schedule_list_API createFromParcel(Parcel in) {
            return new Schedule_list_API(in);
        }

        @Override
        public Schedule_list_API[] newArray(int size) {
            return new Schedule_list_API[size];
        }
    };

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Schedule_list_API(String course_name, String date, String time, String fname, String rating, String id) {
        this.course_name = course_name;
        this.date = date;
        this.time = time;
        this.fname = fname;
        this.rating = rating;
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(course_name);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeString(fname);
        parcel.writeString(rating);
        parcel.writeString(id);
    }
}
