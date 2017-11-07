package com.example.nicco.timemanagementapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.nicco.timemanagementapp.R;
import com.example.nicco.timemanagementapp.adapters.TaskRecyclerViewAdapter;
import com.example.nicco.timemanagementapp.utilities.Database;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

public class DailyChecklistActivity extends AppCompatActivity
{
    private RecyclerView taskRecyclerView;
    private TaskRecyclerViewAdapter taskRecyclerViewAdapter;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_daily_checklist );

        taskRecyclerViewAdapter = new TaskRecyclerViewAdapter (
                this,
                new Database( this ).getUrgentTasksCursor ()
        );
        taskRecyclerView = ( RecyclerView ) findViewById ( R.id.taskRecyclerView );
        taskRecyclerView.setAdapter ( taskRecyclerViewAdapter );
    }
}
