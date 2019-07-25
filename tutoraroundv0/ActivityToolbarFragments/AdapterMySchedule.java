package com.example.panchal.tutoraroundv0.ActivityToolbarFragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.panchal.tutoraroundv0.activity.PerticularPostDetails;
import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.modelClasses.Schedule_list_API;

import java.util.ArrayList;


class AdapterMySchedule extends RecyclerView.Adapter<AdapterMySchedule.MyViewHolder> {

    private Context activity;
    ArrayList<Schedule_list_API> MySchedule;

    public AdapterMySchedule(Context activity, ArrayList<Schedule_list_API> subjects) {
        this.activity = activity;
        this.MySchedule = subjects;
    }

    public TextView Subjectname, Date, Time, Personsname, Personsrating, actualdate, actualtime;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);

            Subjectname = view.findViewById(R.id.myschedule_subjectname);
            Date = view.findViewById(R.id.myschedule_date);
            actualdate = view.findViewById(R.id.myschedule_actual_date);
            Time = view.findViewById(R.id.myschedule_time);
            actualtime = view.findViewById(R.id.myschedule_actual_time);
            Personsrating = view.findViewById(R.id.myschedule_personrating);
            Personsname = view.findViewById(R.id.myschedule_personname);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_schedule_fragment_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Schedule_list_API obj = MySchedule.get(position);
        actualdate.setText(obj.getDate());
        actualtime.setText(obj.getTime());
        Subjectname.setText(obj.getCourse_name());
        Personsname.setText(obj.getFname());
        String rating_proper = String.format("%.2f", Float.parseFloat(obj.getRating()));
        Personsrating.setText(rating_proper);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.startActivity(new Intent(activity, PerticularPostDetails.class).putExtra("id", MySchedule.get(position).getId()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return MySchedule.size();
    }


}
