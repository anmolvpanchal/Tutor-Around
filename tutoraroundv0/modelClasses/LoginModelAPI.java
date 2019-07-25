package com.example.panchal.tutoraroundv0.modelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginModelAPI implements Parcelable {
     String id,fname,lname,email,type;

    protected LoginModelAPI(Parcel in) {
        id = in.readString();
        fname = in.readString();
        lname = in.readString();
        email = in.readString();
        type = in.readString();
    }

    public static final Creator<LoginModelAPI> CREATOR = new Creator<LoginModelAPI>() {
        @Override
        public LoginModelAPI createFromParcel(Parcel in) {
            return new LoginModelAPI(in);
        }

        @Override
        public LoginModelAPI[] newArray(int size) {
            return new LoginModelAPI[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LoginModelAPI(String id, String fname, String lname, String email, String type) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(fname);
        parcel.writeString(lname);
        parcel.writeString(email);
        parcel.writeString(type);
    }
}
