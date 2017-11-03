package com.example.nicco.timemanagementapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.nicco.timemanagementapp.R;

/* Author: Niccolo Pucci
 * IAT 359
 */

public class MainActivity extends AppCompatActivity
{
    private Button toGoalManagerButton;
    private Button toDailyChecklistButton;
    private Button toAnalyticsButton;
    private Button toRetrospectiveButton;

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

        toAnalyticsButton = ( Button ) findViewById ( R.id.toAnalyticsButton );
        toAnalyticsButton.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View v )
            {
                startActivity ( new Intent (
                        MainActivity.this,
                        AnalyticsActivity.class
                ) );
            }
        } );

        toRetrospectiveButton = ( Button ) findViewById ( R.id.toRetrospectiveButton );
        toRetrospectiveButton.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View v )
            {
                startActivity ( new Intent (
                        MainActivity.this,
                        RetrospectiveActivity.class
                ) );
            }
        } );

    }
}
