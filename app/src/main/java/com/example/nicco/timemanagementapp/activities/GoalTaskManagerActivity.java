package com.example.nicco.timemanagementapp.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nicco.timemanagementapp.R;
import com.example.nicco.timemanagementapp.adapters.TaskRecyclerViewAdapter;
import com.example.nicco.timemanagementapp.fragments.AddTaskDialogFragment;
import com.example.nicco.timemanagementapp.fragments.EditTaskDialogFragment;
import com.example.nicco.timemanagementapp.fragments.UpdateTaskDialogFragment;
import com.example.nicco.timemanagementapp.interfaces.ChangeListener;
import com.example.nicco.timemanagementapp.interfaces.OnTaskClickListener;
import com.example.nicco.timemanagementapp.utilities.Database;
import com.example.nicco.timemanagementapp.utilities.DatabaseValues;

public class GoalTaskManagerActivity
        extends AppCompatActivity implements ChangeListener, OnTaskClickListener {
    String goalKey;
    private TextView goalKeyTextView;
    private TextView goalTitleTextView;
    private TextView goalDescriptionTextView;
    private TextView categoryTypeTextView;
    private TextView goalCreationDateTimeTextView;
    private TextView goalCompletionDateTimeTextView;

    private RecyclerView taskRecyclerView;

    private Button createNewTaskButton;

    private TaskRecyclerViewAdapter taskRecyclerViewAdapter;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_goal_task_manager);

        goalKey = getIntent ().getStringExtra (
                DatabaseValues.Column._ID.toString () );

        goalKeyTextView = ( TextView ) findViewById ( R.id.goalKeyTextView );
        goalKeyTextView.setText ( goalKey );

        goalTitleTextView = ( TextView ) findViewById ( R.id.goalTitleTextView );
        goalTitleTextView.setText ( "Goal: " + getIntent ().getStringExtra (
                DatabaseValues.Column.GOAL_TITLE.toString () ) );

        goalDescriptionTextView = ( TextView ) findViewById ( R.id.goalDescriptionTextView );
        goalDescriptionTextView.setText ( "Description: " + getIntent ().getStringExtra (
                DatabaseValues.Column.GOAL_DESCRIPTION.toString () ) );

        categoryTypeTextView = ( TextView ) findViewById ( R.id.categoryTypeTextView );
        categoryTypeTextView.setText ( "Category: " + getIntent ().getStringExtra (
                DatabaseValues.Column.GOAL_CATEGORY_TYPE.toString () ) );

        goalCreationDateTimeTextView = ( TextView ) findViewById ( R.id.goalCreationDateTimeTextView );
        goalCreationDateTimeTextView.setText ( "Created: " + getIntent ().getStringExtra (
                DatabaseValues.Column.GOAL_CREATION_DATE_TIME.toString () ) );

        goalCompletionDateTimeTextView = ( TextView ) findViewById ( R.id.goalCompletionDateTimeTextView );
        String completedDateTime = getIntent ().getStringExtra (
                DatabaseValues.Column.GOAL_COMPLETION_DATE_TIME.toString () );
        if ( completedDateTime.isEmpty () )
        {
            completedDateTime = "Status: Ongoing";

        }
        else
        {
            completedDateTime = "Completed: " + completedDateTime;
        }
        goalCompletionDateTimeTextView.setText ( completedDateTime );

        taskRecyclerViewAdapter = new TaskRecyclerViewAdapter (
                this,
                new Database( this ).getCursor (
                        new DatabaseValues.Table [] { DatabaseValues.Table.TASK },
                        "WHERE " + DatabaseValues.Column.GOAL_ID + " == " +
                                goalKeyTextView.getText ().toString ()
                )
        );
        taskRecyclerViewAdapter.setOnTaskClickListener ( this );
        taskRecyclerView = ( RecyclerView ) findViewById ( R.id.taskRecyclerView );
        taskRecyclerView.setAdapter ( taskRecyclerViewAdapter );

        createNewTaskButton = ( Button ) findViewById ( R.id.createNewTaskButton );
        createNewTaskButton.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View v )
            {
                showTaskEditDialog ();
            }
        } );
    }

    private void showTaskEditDialog ()
    {
        FragmentTransaction fragmentTransaction = getFragmentManager ().beginTransaction ();
        Fragment previousFragment = getFragmentManager ()
                .findFragmentByTag ( AddTaskDialogFragment.FRAGMENT_TAG );

        if ( previousFragment != null )
        {
            fragmentTransaction.remove ( previousFragment );
        }
        fragmentTransaction.addToBackStack ( null );

        AddTaskDialogFragment addTaskDialogFragment = AddTaskDialogFragment.newInstance (
                getIntent ().getStringExtra (
                        DatabaseValues.Column._ID.toString () ),
                this
        );
        addTaskDialogFragment.show (
                fragmentTransaction,
                AddTaskDialogFragment.FRAGMENT_TAG
        );
    }

    private void showEditTaskDialogFragment ( String taskID )
    {
        FragmentTransaction fragmentTransaction = getFragmentManager ().beginTransaction ();
        Fragment previousFragment = getFragmentManager ()
                .findFragmentByTag ( EditTaskDialogFragment.FRAGMENT_TAG );

        if ( previousFragment != null )
        {
            fragmentTransaction.remove ( previousFragment );
        }
        fragmentTransaction.addToBackStack ( null );

        EditTaskDialogFragment editTaskDialogFragment = EditTaskDialogFragment.newInstance (
                getIntent ().getStringExtra (
                        DatabaseValues.Column._ID.toString () ),
                this
        );
        editTaskDialogFragment.show (
                fragmentTransaction,
                UpdateTaskDialogFragment.FRAGMENT_TAG
        );
    }

    @Override
    public void notifyActionChange ( boolean change ) {
        if (!change) {
            return;
        }

        taskRecyclerViewAdapter = new TaskRecyclerViewAdapter(
                this,
                new Database(this).getCursor(
                        new DatabaseValues.Table[]{DatabaseValues.Table.TASK},
                        "WHERE " + DatabaseValues.Column.GOAL_ID + " == " +
                                goalKeyTextView.getText().toString()
                )
        );
        taskRecyclerViewAdapter.setOnTaskClickListener(this);
        taskRecyclerView.setAdapter(taskRecyclerViewAdapter);
        taskRecyclerView.getAdapter().notifyDataSetChanged();

        Database db = new Database(this);
        Cursor cursor = db.getGoal(goalKeyTextView.getText().toString());
        cursor.moveToNext ();
        String dateTimeCompletion = cursor.getString(
                cursor.getColumnIndex(DatabaseValues.Column.GOAL_COMPLETION_DATE_TIME.toString()));

        if (dateTimeCompletion == null) {
            dateTimeCompletion = "Status: Ongoing";
        }

        else {
            dateTimeCompletion = "Completed: " + dateTimeCompletion;

        }
        goalCompletionDateTimeTextView.setText ( dateTimeCompletion );
    }
    @Override
    public void onTaskClick ( String taskID )
    {
        showEditTaskDialogFragment ( taskID );
    }
}
