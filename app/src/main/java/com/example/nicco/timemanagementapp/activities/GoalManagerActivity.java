package com.example.nicco.timemanagementapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.nicco.timemanagementapp.R;

public class GoalManagerActivity extends AppCompatActivity
{
    private Button createNewGoalbutton;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_goal_manager );

        createNewGoalbutton = ( Button ) findViewById ( R.id.createNewGoalbutton );
        createNewGoalbutton.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View v )
            {
                startActivity ( new Intent(
                        GoalManagerActivity.this,
                        CreateNewGoalActivity.class
                ) );
            }
        } );

    }
}
