package com.example.nicco.timemanagementapp.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicco.timemanagementapp.R;
import com.example.nicco.timemanagementapp.fragments.EditGoalDialogFragment;
import com.example.nicco.timemanagementapp.utilities.Database;
import com.example.nicco.timemanagementapp.utilities.DatabaseValues;

import static com.example.nicco.timemanagementapp.fragments.EditGoalDialogFragment.FRAGMENT_TAG;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

public class GoalManagerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private RecyclerView goalRecyclerView;
    private Button createNewGoalbutton;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_goal_manager );

        Database database = new Database ( this );

        GoalRecyclerViewAdapter goalRecyclerViewAdapter = new GoalRecyclerViewAdapter (
                this,
                null,
                database.getCursor ( DatabaseValues.Table.GOAL ) );
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

    @Override
    public void onItemClick (
            AdapterView parent,
            View view,
            int position,
            long id
    ) {
        LinearLayout clickedRow = ( LinearLayout ) view;
        TextView goalKeyTextView = (TextView) view.findViewById ( R.id.goalKeyTextView );
        TextView goalTitleTextView = ( TextView ) view.findViewById ( R.id.goalTitleTextView );
        TextView goalDescriptionTextView = ( TextView ) view.findViewById ( R.id.goalDescriptionTextView );
        TextView categoryTypeTextView = ( TextView ) view.findViewById ( R.id.categoryTypeTextView );
        TextView goalCreationDateTimeTextView = ( TextView ) view.findViewById ( R.id.goalCreationDateTimeTextView );
        TextView goalCompletionDateTimeTextView = ( TextView ) view.findViewById ( R.id.goalCompletionDateTimeTextView );

        Toast.makeText(
                this,
                "row " + ( 1 + position ) +
                        ":  " + goalKeyTextView.getText () +
                        " " + goalTitleTextView.getText () +
                        " " + categoryTypeTextView.getText () +
                        " " + goalCreationDateTimeTextView.getText (),
                Toast.LENGTH_LONG
        ).show ();
    }
}
