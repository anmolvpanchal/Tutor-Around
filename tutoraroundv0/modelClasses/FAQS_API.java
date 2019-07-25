package com.example.panchal.tutoraroundv0.modelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class FAQS_API implements Parcelable {
    String id,question,answer,type;

    protected FAQS_API(Parcel in) {
        id = in.readString();
        question = in.readString();
        answer = in.readString();
        type = in.readString();
    }

    public static final Creator<FAQS_API> CREATOR = new Creator<FAQS_API>() {
        @Override
        public FAQS_API createFromParcel(Parcel in) {
            return new FAQS_API(in);
        }

        @Override
        public FAQS_API[] newArray(int size) {
            return new FAQS_API[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FAQS_API(String id, String question, String answer, String type) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(question);
        parcel.writeString(answer);
        parcel.writeString(type);
    }
}
