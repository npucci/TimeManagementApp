package com.example.nicco.timemanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/* Author: Niccolo Pucci
 * IAT 359
 */

public class MainActivity extends AppCompatActivity
{
    private Button toGoalManagerButton;
    private Button toDailyChecklistButton;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        toGoalManagerButton = ( Button ) findViewById ( R.id.toGoalManagerButton );
        toGoalManagerButton.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View v )
            {
               startActivity ( new Intent (
                       MainActivity.this,
                       GoalManagerActivity.class
               ) );
            }
        } );

        toDailyChecklistButton = ( Button ) findViewById ( R.id.toDailyChecklistButton );
        toDailyChecklistButton.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View v )
            {
                startActivity ( new Intent (
                        MainActivity.this,
                        DailyChecklistActivity.class
                ) );
            }
        } );

    }
}
