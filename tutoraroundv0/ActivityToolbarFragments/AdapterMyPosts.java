package com.example.panchal.tutoraroundv0.ActivityToolbarFragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.activity.PerticularPostDetails;
import com.example.panchal.tutoraroundv0.modelClasses.MyPostList_API;

import java.util.ArrayList;

class AdapterMyPosts extends RecyclerView.Adapter<AdapterMyPosts.MyViewHolder> {


    private Context activity;
    ArrayList<MyPostList_API> myPostList;
    private static String TAG = "AdapterMyPosts";


    public AdapterMyPosts(Context activity, ArrayList<MyPostList_API> subjects) {
        this.activity = activity;
        this.myPostList = subjects;
    }

    public TextView Subjectname, Date, Time, description, SubjectActualDescription, actualdate, actualtime;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);

            description = view.findViewById(R.id.mypost_description);
            Subjectname = view.findViewById(R.id.mypost_subjectname);
            Date = view.findViewById(R.id.mypost_date);
            Time = view.findViewById(R.id.mypost_time);
            SubjectActualDescription = view.findViewById(R.id.mypost_actual_description);
            actualdate = view.findViewById(R.id.mypost_actual_date);
            actualtime = view.findViewById(R.id.mypost_actual_time);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_posts_fragment_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        MyPostList_API obj = myPostList.get(position);
        Subjectname.setText(obj.getCourse_name());
        actualdate.setText(obj.getDate());
        actualtime.setText(obj.getTime());
        SubjectActualDescription.setText(obj.getDescription());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.startActivity(new Intent(activity, PerticularPostDetails.class).putExtra("id", myPostList.get(position).getId()));

            }
        });


    }

    @Override
    public int getItemCount() {
        return myPostList.size();
    }


}
