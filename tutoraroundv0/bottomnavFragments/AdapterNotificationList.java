package com.example.panchal.tutoraroundv0.bottomnavFragments;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.modelClasses.NotificationList_API;

import java.util.ArrayList;



public class AdapterNotificationList extends RecyclerView.Adapter <AdapterNotificationList.MyViewHolder> {

    private Context activity;
    ArrayList<NotificationList_API> notification_list;

    public AdapterNotificationList(Activity activity, ArrayList<NotificationList_API> notification_list) {
        this.activity = activity;
        this.notification_list = notification_list;
    }

    public TextView notificationtype,message;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);

            notificationtype = view.findViewById(R.id.notification_cell_heading);
            message = view.findViewById(R.id.notification_cell_description);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_fragment_cell, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        NotificationList_API obj = notification_list.get(position);

        notificationtype.setText(obj.getNotification_type());
        message.setText(obj.getMessage());

    }


    @Override
    public int getItemCount() {
        return notification_list.size();
    }
}
