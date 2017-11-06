package com.example.nicco.timemanagementapp.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.nicco.timemanagementapp.R;
import com.example.nicco.timemanagementapp.fragments.EditGoalDialogFragment;

import static com.example.nicco.timemanagementapp.fragments.EditGoalDialogFragment.FRAGMENT_TAG;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

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
            showGoalEditDialog ();
            }
        } );
    }

    private void showGoalEditDialog ()
    {
        FragmentTransaction fragmentTransaction = getFragmentManager ().beginTransaction ();
        Fragment previousFragment = getFragmentManager ()
                .findFragmentByTag ( FRAGMENT_TAG );

        if ( previousFragment != null )
        {
            fragmentTransaction.remove ( previousFragment );
        }
        fragmentTransaction.addToBackStack ( null );

        EditGoalDialogFragment editGoalDialogFragment = new EditGoalDialogFragment ();

        editGoalDialogFragment.show (
                fragmentTransaction,
                FRAGMENT_TAG
        );
    }
}
