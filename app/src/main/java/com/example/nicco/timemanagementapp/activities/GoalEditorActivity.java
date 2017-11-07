package com.example.nicco.timemanagementapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.nicco.timemanagementapp.R;
import com.example.nicco.timemanagementapp.adapters.TaskRecyclerViewAdapter;
import com.example.nicco.timemanagementapp.utilities.Database;
import com.example.nicco.timemanagementapp.utilities.DatabaseValues;

public class GoalEditorActivity extends AppCompatActivity
{

    private TextView goalKeyTextView;
    private TextView goalTitleTextView;
    private TextView goalDescriptionTextView;
    private TextView categoryTypeTextView;
    private TextView goalCreationDateTimeTextView;
    private TextView goalCompletionDateTimeTextView;

    private RecyclerView taskRecyclerView;

    private TaskRecyclerViewAdapter taskRecyclerViewAdapter;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_goal_editor );

        Log.v (
                "PUCCI",
                "HERE"
        );

        goalKeyTextView = ( TextView ) findViewById ( R.id.goalKeyTextView );
        goalKeyTextView.setText ( getIntent ().getStringExtra (
                DatabaseValues.Column._ID.toString () ) );

        goalTitleTextView = ( TextView ) findViewById ( R.id.goalTitleTextView );
        goalTitleTextView.setText ( "Title: " + getIntent ().getStringExtra (
                DatabaseValues.Column.GOAL_TITLE.toString () ) );

        goalDescriptionTextView = ( TextView ) findViewById ( R.id.goalDescriptionTextView );
        goalDescriptionTextView.setText ( "Description: " + getIntent ().getStringExtra (
                DatabaseValues.Column.GOAL_DESCRIPTION.toString () ) );

        categoryTypeTextView = ( TextView ) findViewById ( R.id.categoryTypeTextView );
        categoryTypeTextView.setText ( "Category: " + getIntent ().getStringExtra (
                DatabaseValues.Column.CATEGORY_TYPE.toString () ) );

        goalCreationDateTimeTextView = ( TextView ) findViewById ( R.id.goalCreationDateTimeTextView );
        goalCreationDateTimeTextView.setText ( "Created: " + getIntent ().getStringExtra (
                DatabaseValues.Column.GOAL_CREATION_DATE_TIME.toString () ) );

        goalCompletionDateTimeTextView = ( TextView ) findViewById ( R.id.goalCompletionDateTimeTextView );
        String completedDate = getIntent ().getStringExtra (
                DatabaseValues.Column.GOAL_COMPLETION_DATE_TIME.toString () );
        if ( completedDate.isEmpty () )
        {
            completedDate = "Status: Ongoing";

        }
        else
        {
            completedDate = "Completed: " + completedDate;
        }
        goalCompletionDateTimeTextView.setText ( completedDate );



        taskRecyclerViewAdapter = new TaskRecyclerViewAdapter (
                this,
                new Database( this ).getCursor (
                        new DatabaseValues.Table [] { DatabaseValues.Table.TASK },
                        null
                )
        );
        taskRecyclerView = ( RecyclerView ) findViewById ( R.id.taskRecyclerView );
        taskRecyclerView.setAdapter ( taskRecyclerViewAdapter );
    }
}
