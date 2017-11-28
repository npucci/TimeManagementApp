package com.example.nicco.timemanagementapp.fragments;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nicco.timemanagementapp.R;
import com.example.nicco.timemanagementapp.interfaces.ChangeListener;
import com.example.nicco.timemanagementapp.interfaces.NullChangeListener;
import com.example.nicco.timemanagementapp.utilities.Database;
import com.example.nicco.timemanagementapp.utilities.DatabaseValues;
import com.example.nicco.timemanagementapp.utilities.UtilityMethods;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

public class AddTaskDialogFragment extends DialogFragment
{
    public static final String FRAGMENT_TAG = "editTaskDialog";

    private String goalID;

    private ChangeListener changeListener;

    private EditText taskTitleEditText;

    private TextView taskEstimatedCostTextView;

    private SeekBar taskEstimatedPointSeekBar;

    private DatePicker taskDeadlineDatePicker;
    private TimePicker taskDeadlineTimePicker;

    private Button saveButton;

    public static AddTaskDialogFragment newInstance (String goalID, ChangeListener changeListener )
    {
        AddTaskDialogFragment editGoalDialogFragment = new AddTaskDialogFragment();
        if ( changeListener == null )
        {
            changeListener = new NullChangeListener ();
        }
        editGoalDialogFragment.setChangeListener ( changeListener );
        editGoalDialogFragment.setGoalID ( goalID );
        return editGoalDialogFragment;
    }

    @Override
    public void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView (
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        final View view =  inflater.inflate (
                R.layout.dialog_fragment_add_task,
                container,
                false
        );

        taskTitleEditText = ( EditText ) view.findViewById ( R.id.taskTitleEditText );

        taskEstimatedCostTextView = ( TextView ) view.findViewById ( R.id.taskEstimatedCostTextView );

        taskEstimatedPointSeekBar = ( SeekBar ) view.findViewById ( R.id.taskEstimatedPointSeekBar );
        taskEstimatedPointSeekBar.setOnSeekBarChangeListener(  new SeekBar.OnSeekBarChangeListener ()
        {
            @Override
            public void onProgressChanged (
                    SeekBar seekBar,
                    int progress,
                    boolean fromUser
            ) {
                taskEstimatedCostTextView.setText (
                        getResources ().getString ( R.string.task_estimated_cost )
                                + " " + progress );
            }

            @Override
            public void onStartTrackingTouch ( SeekBar seekBar )
            {

            }

            @Override
            public void onStopTrackingTouch ( SeekBar seekBar )
            {

            }
        } );

        taskEstimatedCostTextView.setText (
                getResources ().getString ( R.string.task_estimated_cost )
                        + " " + taskEstimatedPointSeekBar.getProgress () );

        taskDeadlineDatePicker = ( DatePicker ) view.findViewById ( R.id.taskDeadlineDatePicker );
        taskDeadlineTimePicker = ( TimePicker ) view.findViewById ( R.id.taskDeadlineTimePicker );

        Database db = new Database ( view.getContext () );

        saveButton = ( Button ) view.findViewById ( R.id.saveButton );
        saveButton.setOnClickListener ( new View.OnClickListener()
        {
            @Override
            public void onClick ( View v )
            {
                String taskTitle = taskTitleEditText.getText ().toString ();

                boolean hasNeededInput =
                        taskTitle != null &&
                                !taskTitle.isEmpty ();

                if ( !hasNeededInput )
                {
                    Toast.makeText (
                            view.getContext (),
                            "Please fill in the required fields",
                            Toast.LENGTH_LONG
                    ).show ();
                    return;
                }

                Database database = new Database ( view.getContext () );

                ContentValues contentValues = new ContentValues ();
                contentValues.put (
                        DatabaseValues.Column.TASK_TITLE.toString (),
                        taskTitle
                );
                contentValues.put (
                        DatabaseValues.Column.TASK_ESTIMATED_COST.toString (),
                        "" + taskEstimatedPointSeekBar.getProgress ()
                );
                contentValues.put (
                        DatabaseValues.Column.TASK_DUE_DATE.toString (),
                        "" + UtilityMethods.getDateTime (
                                taskDeadlineDatePicker,
                                taskDeadlineTimePicker
                        )
                );
                contentValues.put (
                        DatabaseValues.Column.TASK_COMPLETION_PERCENTAGE.toString (),
                        "" + 0
                );
                contentValues.put (
                        DatabaseValues.Column.GOAL_ID.toString (),
                        "" + goalID
                );


                Long insertedTaskID = database.insertNewTask ( contentValues );

                Log.v (
                        "PUCCI",
                        "insertedTaskID = " + insertedTaskID
                );

                ContentValues initialProgress = new ContentValues ();
                initialProgress.put (
                        DatabaseValues.Column.PROGRESS_HOURS_SPENT.toString (),
                        0
                );
                initialProgress.put (
                        DatabaseValues.Column.TASK_ID.toString (),
                        insertedTaskID
                );
                initialProgress.put (
                        DatabaseValues.Column.PROGRESS_DATE.toString (),
                        UtilityMethods.getDate ( new DatePicker ( getActivity () ) )
                );

                Long insertedProgressID = database.insertNewProgress ( initialProgress );

                Log.v (
                        "PUCCI",
                        "insertedProgressID = " + insertedProgressID
                );

                changeListener.notifyActionChange ( true );
                dismiss ();
            }
        } );

        return view;
    }

    private void setGoalID ( String goalID )
    {
        this.goalID = goalID;
    }

    private void setChangeListener ( ChangeListener changeListener )
    {
        this.changeListener = changeListener;
    }
}
