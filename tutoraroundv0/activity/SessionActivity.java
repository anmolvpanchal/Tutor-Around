package com.example.panchal.tutoraroundv0.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.panchal.tutoraroundv0.R;

public class SessionActivity extends AppCompatActivity {

    TextView timer;
    Button noshow,start_session,end_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        timer = findViewById(R.id.sesion_timer_text);
        noshow = findViewById(R.id.sesion_noshow);
        start_session = findViewById(R.id.sesion_start_sessio);
        end_session = findViewById(R.id.sesion_complete_sessio);







    }
}
