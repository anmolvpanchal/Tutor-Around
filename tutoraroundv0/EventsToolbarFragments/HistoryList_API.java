package com.example.panchal.tutoraroundv0.EventsToolbarFragments;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryList_API implements Parcelable {

    String course_name,fname,id,date,time,duration,price,description,rating,ccode;

    protected HistoryList_API(Parcel in) {
        course_name = in.readString();
        fname = in.readString();
        id = in.readString();
        date = in.readString();
        time = in.readString();
        duration = in.readString();
        price = in.readString();
        description = in.readString();
        rating = in.readString();
        ccode = in.readString();
    }

    public static final Creator<HistoryList_API> CREATOR = new Creator<HistoryList_API>() {
        @Override
        public HistoryList_API createFromParcel(Parcel in) {
            return new HistoryList_API(in);
        }

        @Override
        public HistoryList_API[] newArray(int size) {
            return new HistoryList_API[size];
        }
    };

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public HistoryList_API(String course_name, String fname, String id, String date, String time, String duration, String price, String description, String rating, String ccode) {
        this.course_name = course_name;
        this.fname = fname;
        this.id = id;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.price = price;
        this.description = description;
        this.rating = rating;
        this.ccode = ccode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(course_name);
        parcel.writeString(fname);
        parcel.writeString(id);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeString(duration);
        parcel.writeString(price);
        parcel.writeString(description);
        parcel.writeString(rating);
        parcel.writeString(ccode);
    }
}
