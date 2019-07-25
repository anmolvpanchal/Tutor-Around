package com.example.panchal.tutoraroundv0.modelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Pending_opportunities_API implements Parcelable {
    String id,date,time,description,course_name,offer_price;

    protected Pending_opportunities_API(Parcel in) {
        id = in.readString();
        date = in.readString();
        time = in.readString();
        description = in.readString();
        course_name = in.readString();
        offer_price = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(description);
        dest.writeString(course_name);
        dest.writeString(offer_price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Pending_opportunities_API> CREATOR = new Creator<Pending_opportunities_API>() {
        @Override
        public Pending_opportunities_API createFromParcel(Parcel in) {
            return new Pending_opportunities_API(in);
        }

        @Override
        public Pending_opportunities_API[] newArray(int size) {
            return new Pending_opportunities_API[size];
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

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    public Pending_opportunities_API(String id, String date, String time, String description, String course_name, String offer_price) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.description = description;
        this.course_name = course_name;
        this.offer_price = offer_price;
    }
}
