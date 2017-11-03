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
                Intent intent = new Intent (
                        MainActivity.this,
                        GoalManagerActivity.class
                );

                startActivity ( intent );
            }
        } );
    }
}
