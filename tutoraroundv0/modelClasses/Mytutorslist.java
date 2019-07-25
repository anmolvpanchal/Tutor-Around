package com.example.panchal.tutoraroundv0.modelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Mytutorslist implements Parcelable {
   String fname,lname,offer_price,rating,id,user_fk;

    protected Mytutorslist(Parcel in) {
        fname = in.readString();
        lname = in.readString();
        offer_price = in.readString();
        rating = in.readString();
        id = in.readString();
        user_fk = in.readString();
    }

    public static final Creator<Mytutorslist> CREATOR = new Creator<Mytutorslist>() {
        @Override
        public Mytutorslist createFromParcel(Parcel in) {
            return new Mytutorslist(in);
        }

        @Override
        public Mytutorslist[] newArray(int size) {
            return new Mytutorslist[size];
        }
    };

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
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

    public String getUser_fk() {
        return user_fk;
    }

    public void setUser_fk(String user_fk) {
        this.user_fk = user_fk;
    }

    public Mytutorslist(String fname, String lname, String offer_price, String rating, String id, String user_fk) {
        this.fname = fname;
        this.lname = lname;
        this.offer_price = offer_price;
        this.rating = rating;
        this.id = id;
        this.user_fk = user_fk;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fname);
        parcel.writeString(lname);
        parcel.writeString(offer_price);
        parcel.writeString(rating);
        parcel.writeString(id);
        parcel.writeString(user_fk);
    }
}
