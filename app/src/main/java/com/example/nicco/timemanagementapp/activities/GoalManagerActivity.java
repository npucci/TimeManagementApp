package com.example.nicco.timemanagementapp.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.nicco.timemanagementapp.R;
import com.example.nicco.timemanagementapp.adapters.GoalRecyclerViewAdapter;
import com.example.nicco.timemanagementapp.fragments.AddGoalDialogFragment;
import com.example.nicco.timemanagementapp.interfaces.ChangeListener;
import com.example.nicco.timemanagementapp.utilities.Database;
import com.example.nicco.timemanagementapp.utilities.DatabaseValues;

import static com.example.nicco.timemanagementapp.fragments.AddGoalDialogFragment.FRAGMENT_TAG;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

public class GoalManagerActivity extends AppCompatActivity implements ChangeListener
{
    private RecyclerView goalRecyclerView;
    private GoalRecyclerViewAdapter goalRecyclerViewAdapter;
    private Button createNewGoalbutton;

    private static final  String wheregGoalIsComplete = "WHERE " +
            DatabaseValues.Column.GOAL_COMPLETION_DATE_TIME + " IS NOT NULL";

    private static final  String wheregGoalIsOnGoing = "WHERE " +
            DatabaseValues.Column.GOAL_COMPLETION_DATE_TIME + " IS NULL";

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_goal_manager );

        goalRecyclerViewAdapter = new GoalRecyclerViewAdapter (
                this,
                new Database ( this ).getCursor (
                        new DatabaseValues.Table [] { DatabaseValues.Table.GOAL },
                        null
                )
        );
        goalRecyclerView = ( RecyclerView ) findViewById ( R.id.goalRecyclerView );
        goalRecyclerView.setAdapter ( goalRecyclerViewAdapter );

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

    @Override
    protected void onResume ()
    {
        super.onResume ();
        notifyActionChange ( true );
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

        AddGoalDialogFragment addGoalDialogFragment = AddGoalDialogFragment.newInstance ( this );
        addGoalDialogFragment.show (
                fragmentTransaction,
                FRAGMENT_TAG
        );
    }

    @Override
    public void notifyActionChange ( boolean change )
    {
        if ( !change )
        {
            return;
        }

        goalRecyclerViewAdapter = new GoalRecyclerViewAdapter (
                this,
                new Database ( this ).getCursor (
                        new DatabaseValues.Table [] { DatabaseValues.Table.GOAL },
                        null
                )
        );
        goalRecyclerView.setAdapter ( goalRecyclerViewAdapter );
        goalRecyclerView.getAdapter ().notifyDataSetChanged ();
    }
}
