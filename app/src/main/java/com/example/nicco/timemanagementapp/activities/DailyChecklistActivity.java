package com.example.nicco.timemanagementapp.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.nicco.timemanagementapp.R;
import com.example.nicco.timemanagementapp.adapters.TaskRecyclerViewAdapter;
import com.example.nicco.timemanagementapp.fragments.UpdateTaskDialogFragment;
import com.example.nicco.timemanagementapp.interfaces.ChangeListener;
import com.example.nicco.timemanagementapp.interfaces.OnTaskClickListener;
import com.example.nicco.timemanagementapp.utilities.Database;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

public class DailyChecklistActivity
        extends AppCompatActivity implements ChangeListener, OnTaskClickListener {
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
        taskRecyclerViewAdapter.setOnTaskClickListener ( this );
        taskRecyclerView = ( RecyclerView ) findViewById ( R.id.taskRecyclerView );
        taskRecyclerView.setAdapter ( taskRecyclerViewAdapter );
    }

    private void showTaskUpdateDialog ( String taskID )
    {
        Log.v ( "PUCCI", "here");
        FragmentTransaction fragmentTransaction = getFragmentManager ().beginTransaction ();
        Fragment previousFragment = getFragmentManager ()
                .findFragmentByTag ( UpdateTaskDialogFragment.FRAGMENT_TAG );

        if ( previousFragment != null )
        {
            fragmentTransaction.remove ( previousFragment );
        }
        fragmentTransaction.addToBackStack ( null );

        UpdateTaskDialogFragment updateTaskDialogFragment = UpdateTaskDialogFragment.newInstance (
                taskID,
                this
        );
        updateTaskDialogFragment.show (
                fragmentTransaction,
                UpdateTaskDialogFragment.FRAGMENT_TAG
        );
    }


    @Override
    public void onTaskClick ( String taskID ) {
        showTaskUpdateDialog ( taskID );
    }

    @Override
    public void notifyActionChange ( boolean change )
    {
        if ( !change )
        {
            return;
        }

        taskRecyclerViewAdapter = new TaskRecyclerViewAdapter (
                this,
                new Database( this ).getUrgentTasksCursor ()
        );
        taskRecyclerViewAdapter.setOnTaskClickListener ( this );
        taskRecyclerView.setAdapter ( taskRecyclerViewAdapter );
        taskRecyclerView.getAdapter ().notifyDataSetChanged ();
    }
}
