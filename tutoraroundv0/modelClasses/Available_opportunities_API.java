package com.example.panchal.tutoraroundv0.modelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Available_opportunities_API implements Parcelable {

    String id, date, time, description, course_name,price,course_fk;

    protected Available_opportunities_API(Parcel in) {
        id = in.readString();
        date = in.readString();
        time = in.readString();
        description = in.readString();
        course_name = in.readString();
        price = in.readString();
        course_fk = in.readString();
    }

    public static final Creator<Available_opportunities_API> CREATOR = new Creator<Available_opportunities_API>() {
        @Override
        public Available_opportunities_API createFromParcel(Parcel in) {
            return new Available_opportunities_API(in);
        }

        @Override
        public Available_opportunities_API[] newArray(int size) {
            return new Available_opportunities_API[size];
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCourse_fk() {
        return course_fk;
    }

    public void setCourse_fk(String course_fk) {
        this.course_fk = course_fk;
    }

    public Available_opportunities_API(String id, String date, String time, String description, String course_name, String price, String course_fk) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.description = description;
        this.course_name = course_name;
        this.price = price;
        this.course_fk = course_fk;
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
        parcel.writeString(price);
        parcel.writeString(course_fk);
    }
}
