package com.example.nicco.timemanagementapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.nicco.timemanagementapp.R;
import com.example.nicco.timemanagementapp.utilities.Database;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

public class CreateNewGoalActivity extends AppCompatActivity
{
    private Spinner categorySpinner;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_create_new_goal );



        categorySpinner = ( Spinner ) findViewById ( R.id.categorySpinner );
        Database db = new Database ( this );
        ArrayAdapter < String > arrayAdapter = new ArrayAdapter < String > (
                this,
                android.R.layout.simple_spinner_item,
                db.getAllCategories ()
        );
        categorySpinner.setAdapter ( arrayAdapter );
    }
}
