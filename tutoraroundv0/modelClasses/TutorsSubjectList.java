package com.example.panchal.tutoraroundv0.modelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class TutorsSubjectList implements Parcelable {

    String course_id,course_code,course_name;

    protected TutorsSubjectList(Parcel in) {
        course_id = in.readString();
        course_code = in.readString();
        course_name = in.readString();
    }

    public static final Creator<TutorsSubjectList> CREATOR = new Creator<TutorsSubjectList>() {
        @Override
        public TutorsSubjectList createFromParcel(Parcel in) {
            return new TutorsSubjectList(in);
        }

        @Override
        public TutorsSubjectList[] newArray(int size) {
            return new TutorsSubjectList[size];
        }
    };

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public TutorsSubjectList(String course_id, String course_code, String course_name) {
        this.course_id = course_id;
        this.course_code = course_code;
        this.course_name = course_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(course_id);
        parcel.writeString(course_code);
        parcel.writeString(course_name);
    }
}
