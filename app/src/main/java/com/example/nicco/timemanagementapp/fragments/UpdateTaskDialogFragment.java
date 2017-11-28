package com.example.nicco.timemanagementapp.fragments;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nicco.timemanagementapp.R;
import com.example.nicco.timemanagementapp.interfaces.ChangeListener;
import com.example.nicco.timemanagementapp.interfaces.NullChangeListener;
import com.example.nicco.timemanagementapp.utilities.Database;
import com.example.nicco.timemanagementapp.utilities.DatabaseValues;
import com.example.nicco.timemanagementapp.utilities.UtilityMethods;

import java.util.ArrayList;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

public class UpdateTaskDialogFragment extends DialogFragment
{
    public static final String FRAGMENT_TAG = "updateTaskDialog";

    private String taskID;
    private int fullHoursSpent = 0;
    private TextView taskTitleTextView;
    private TextView hoursSpentTextView;
    private TextView percentageCompleteTextView;
    private TextView taskCompleteTextView;
    private Spinner hoursSpentSpinner;
    private SeekBar percentageCompletePointSeekBar;
    private Button saveButton;
    private ChangeListener changeListener;

    public static UpdateTaskDialogFragment newInstance (
            String taskID,
            ChangeListener changeListener
    ) {
        UpdateTaskDialogFragment updateTaskDialogFragment = new UpdateTaskDialogFragment();
        if ( changeListener == null )
        {
            changeListener = new NullChangeListener ();
        }
        updateTaskDialogFragment.setChangeListener ( changeListener );
        updateTaskDialogFragment.setTaskID ( taskID );
        return updateTaskDialogFragment;
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
                R.layout.dialog_fragment_update_task,
                container,
                false
        );

        Database db = new Database ( view.getContext () );
        Cursor incidentCursor = db.getCursor (
                new DatabaseValues.Table[] { DatabaseValues.Table.TASK },
                "WHERE " + DatabaseValues.Column._ID.toString () + " = " + taskID
        );
        incidentCursor.moveToFirst ();

        taskTitleTextView = ( TextView ) view.findViewById ( R.id.taskTitleTextView );
        taskTitleTextView.setText ( incidentCursor.getString (
                incidentCursor.getColumnIndex ( DatabaseValues.Column.TASK_TITLE.toString () )
        ) );

        try {
            fullHoursSpent = Integer.parseInt ( incidentCursor.getString (
                    incidentCursor.getColumnIndex ( DatabaseValues.Column.TASK_TITLE.toString () )
            ) );
        }
        catch ( Exception e )
        {
            fullHoursSpent = -1;
        }

        hoursSpentTextView = ( TextView ) view.findViewById ( R.id.hoursSpentTextView );
        hoursSpentSpinner = ( Spinner ) view.findViewById ( R.id.hoursSpentSpinner );
        ArrayList < String > hoursSpinnerArrayList = new ArrayList ();
        for ( int i = 1 ; i < 25 ; i++ )
        {
            hoursSpinnerArrayList.add ( "" + i );
        }
        hoursSpentSpinner.setAdapter ( new ArrayAdapter (
                UpdateTaskDialogFragment.this.getActivity (),
                android.R.layout.simple_spinner_item,
                hoursSpinnerArrayList
        ) );
        hoursSpentSpinner.setSelection ( 0 );

        percentageCompletePointSeekBar = ( SeekBar ) view.findViewById ( R.id.percentageCompletePointSeekBar );
        percentageCompletePointSeekBar.setProgress (
                incidentCursor.getInt ( incidentCursor.getColumnIndex (
                        DatabaseValues.Column.TASK_COMPLETION_PERCENTAGE.toString () ) )
        );
        percentageCompletePointSeekBar.setOnSeekBarChangeListener(  new SeekBar.OnSeekBarChangeListener ()
        {
            @Override
            public void onProgressChanged (
                    SeekBar seekBar,
                    int progress,
                    boolean fromUser
            ) {
                percentageCompleteTextView.setText ( "Percentage Complete: " + progress + "%" );
                if ( progress == 100 )
                {
                    taskCompleteTextView.setVisibility ( View.VISIBLE );
                }

                else
                {
                    taskCompleteTextView.setVisibility ( View.INVISIBLE );
                }
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

        percentageCompleteTextView = ( TextView ) view.findViewById
                ( R.id.percentageCompleteTextView );
        percentageCompleteTextView.setText ( "Percentage Complete: " +
                percentageCompletePointSeekBar.getProgress () + "%"
        );

        taskCompleteTextView = ( TextView ) view.findViewById ( R.id.taskCompleteTextView );
        if ( percentageCompletePointSeekBar.getProgress () == 100 )
        {
            taskCompleteTextView.setVisibility ( View.VISIBLE );
        }
        else
        {
            taskCompleteTextView.setVisibility ( View.INVISIBLE );
        }


        saveButton = ( Button ) view.findViewById ( R.id.saveButton );
        saveButton.setOnClickListener ( new View.OnClickListener()
        {
            @Override
            public void onClick ( View v )
            {
                String taskTitle = taskTitleTextView.getText ().toString ();

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

                ContentValues taskContentValues = getTaskUpdateContentValues ();
                boolean taskUpdated = database.updateTask (
                        taskID,
                        taskContentValues
                );
                Log.v (
                        "PUCCI",
                        "taskUpdated = " + taskUpdated
                );

                String progressID = database.getExistingProgressID (
                        taskID,
                        UtilityMethods.getDate ( new DatePicker ( getActivity () ) )
                );

                if ( progressID == null )
                {
                    progressID = "" + database.insertNewProgress(
                            getProgressNewInsertContentValues ()
                    );

                    Log.v (
                            "PUCCI",
                            "progressID = " + progressID
                    );
                }

                else
                {
                    boolean progressUpdated = database.updateProgress (
                            progressID,
                            taskID,
                            getProgressUpdateContentValues ()
                    );

                    Log.v (
                            "PUCCI",
                            "progressUpdated = " + progressUpdated
                    );
                }

                changeListener.notifyActionChange ( taskUpdated );
                dismiss ();
            }
        } );

        return view;
    }

    private ContentValues getTaskUpdateContentValues ()
    {
        ContentValues contentValues = new ContentValues ();
        contentValues.put (
                DatabaseValues.Column.TASK_TITLE.toString (),
                taskTitleTextView.getText ().toString ()
        );
        contentValues.put (
                DatabaseValues.Column.TASK_COMPLETION_PERCENTAGE.toString (),
                "" + percentageCompletePointSeekBar.getProgress ()
        );

        if ( percentageCompletePointSeekBar.getProgress () == 100 )
        {
            contentValues.put (
                    DatabaseValues.Column.TASK_COMPLETION_DATE_TIME.toString (),
                    UtilityMethods.getDateTime (
                            new DatePicker ( getActivity () ),
                            new TimePicker ( getActivity () )
                    )
            );
        }

        return contentValues;
    }

    private ContentValues getProgressUpdateContentValues ()
    {
        ContentValues contentValues = new ContentValues ();
        contentValues.put (
                DatabaseValues.Column.PROGRESS_DATE.toString (),
                UtilityMethods.getDate ( new DatePicker ( getActivity () ) )
        );
        contentValues.put (
                DatabaseValues.Column.PROGRESS_HOURS_SPENT.toString (),
                "" + hoursSpentSpinner.getSelectedItem ().toString ()
        );

        return contentValues;
    }

    private ContentValues getProgressNewInsertContentValues ()
    {
        ContentValues contentValues = new ContentValues ();
        contentValues.put (
                DatabaseValues.Column.PROGRESS_DATE.toString (),
                UtilityMethods.getDate ( new DatePicker ( getActivity () ) )
        );
        contentValues.put (
                DatabaseValues.Column.PROGRESS_HOURS_SPENT.toString (),
                "" + hoursSpentSpinner.getSelectedItem ().toString ()
        );
        contentValues.put (
                DatabaseValues.Column.TASK_ID.toString (),
                "" + taskID
        );

        return contentValues;
    }

    private int calculateTotalHoursSpent ()
    {
        int hoursSpent;
        try
        {
            hoursSpent = Integer.parseInt ( hoursSpentSpinner.getSelectedItem ().toString () );
        }
        catch ( Exception e )
        {
            hoursSpent = 0;
        }

        return fullHoursSpent + hoursSpent;
    }

    private String getDateTime ()
    {
        DatePicker datePicker = new DatePicker ( getActivity () );
        TimePicker timePicker = new TimePicker ( getActivity () );
        return UtilityMethods.getDateTime (
                datePicker,
                timePicker
        );
    }

    private void setTaskID ( String taskID )
    {
        this.taskID = taskID;
    }

    private void setChangeListener ( ChangeListener changeListener )
    {
        this.changeListener = changeListener;
    }
}
