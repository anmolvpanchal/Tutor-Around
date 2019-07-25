package com.example.panchal.tutoraroundv0.bottomnavFragments;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.panchal.tutoraroundv0.R;

import com.example.panchal.tutoraroundv0.modelClasses.TutorsSubjectList;

import java.util.ArrayList;

public class AdapterTutorsSubjectList extends RecyclerView.Adapter<AdapterTutorsSubjectList.MyViewHolder> {

    Activity activity;
    ArrayList<TutorsSubjectList> tutorsSubjectList;
    TextView subjectname,subjectcode;

    public AdapterTutorsSubjectList(Activity activity, ArrayList<TutorsSubjectList> tutorsSubjectList) {

        this.activity = activity;
        this.tutorsSubjectList = tutorsSubjectList ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);

            subjectname = view.findViewById(R.id.tutor_subject_list_name);
            subjectcode = view.findViewById(R.id.tutor_subject_list_code);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tutors_subject_list_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        TutorsSubjectList obj = tutorsSubjectList.get(position);

        subjectname.setText(obj.getCourse_name());
        subjectcode.setText(obj.getCourse_code());

    }


    @Override
    public int getItemCount() {
        return tutorsSubjectList.size();
    }
}
