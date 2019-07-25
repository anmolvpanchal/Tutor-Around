package com.example.panchal.tutoraroundv0.activity;


import android.os.Parcel;
import android.os.Parcelable;

public class UniversityModel implements Parcelable {
    String id, universityName, emailDomain;

    public UniversityModel(String id, String universityName, String emailDomain) {
        this.id = id;
        this.universityName = universityName;
        this.emailDomain = emailDomain;
    }

    protected UniversityModel(Parcel in) {
        id = in.readString();
        universityName = in.readString();
        emailDomain = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(universityName);
        dest.writeString(emailDomain);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UniversityModel> CREATOR = new Creator<UniversityModel>() {
        @Override
        public UniversityModel createFromParcel(Parcel in) {
            return new UniversityModel(in);
        }

        @Override
        public UniversityModel[] newArray(int size) {
            return new UniversityModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getEmailDomain() {
        return emailDomain;
    }

    public void setEmailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
    }

    @Override
    public String toString() {
        return universityName;
    }
}
