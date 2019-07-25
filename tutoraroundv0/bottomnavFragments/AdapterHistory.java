package com.example.panchal.tutoraroundv0.bottomnavFragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.panchal.tutoraroundv0.EventsToolbarFragments.HistoryList_API;
import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.activity.PerticularPostDetails;

import java.util.ArrayList;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.MyViewHolder> {

    private Context activity;
    ArrayList<HistoryList_API> history_list;

    public AdapterHistory(Context activity, ArrayList<HistoryList_API> subjects) {
        this.activity = activity;
        this.history_list = subjects;
    }

    public TextView Subjectname, Subjectcode, Description, Date, Time, Duration, Price, Tutorsname,rating;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);

            Subjectname = view.findViewById(R.id.subjectname);
            Subjectcode = view.findViewById(R.id.subjectcode);
            Description = view.findViewById(R.id.description);
            Date = view.findViewById(R.id.actualdate);
            Time = view.findViewById(R.id.actualtime);
            Duration = view.findViewById(R.id.actualduration);
            Price = view.findViewById(R.id.actualcost);
            Tutorsname = view.findViewById(R.id.tutorsname);
            rating = view.findViewById(R.id.actual_ratings);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_frag_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HistoryList_API obj = history_list.get(position);
        Subjectname.setText(obj.getCourse_name());
        Subjectcode.setText(obj.getCcode());
        Description.setText(obj.getDescription());
        Date.setText(obj.getDate());
        Time.setText(obj.getTime());
        Duration.setText(obj.getDuration());
        Price.setText(obj.getPrice());
        Tutorsname.setText(obj.getFname());
        rating.setText(obj.getRating());
    }


    @Override
    public int getItemCount() {
        return history_list.size();
    }

}
