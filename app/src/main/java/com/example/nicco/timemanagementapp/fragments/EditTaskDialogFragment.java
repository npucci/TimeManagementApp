package com.example.nicco.timemanagementapp.fragments;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
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

public class EditTaskDialogFragment extends DialogFragment
{
    public static final String FRAGMENT_TAG = "editTaskDialog";

    private String taskID;

    private ChangeListener changeListener;

    private EditText taskTitleEditText;

    private TextView taskCompleteTextView;
    private TextView taskEstimatedCostTextView;
    private TextView percentageCompleteTextView;

    private SeekBar percentageCompletePointSeekBar;
    private SeekBar taskEstimatedPointSeekBar;

    private DatePicker taskDeadlineDatePicker;
    private TimePicker taskDeadlineTimePicker;

    private Button saveButton;


    public static EditTaskDialogFragment newInstance (
            String taskID,
            ChangeListener changeListener
    ) {
        EditTaskDialogFragment editTaskDialogFragment = new EditTaskDialogFragment ();
        if ( changeListener == null )
        {
            changeListener = new NullChangeListener ();
        }

        editTaskDialogFragment.setTaskID ( taskID );
        editTaskDialogFragment.setChangeListener ( changeListener );
        return editTaskDialogFragment;
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
                R.layout.dialog_fragment_edit_task,
                container,
                false
        );

        Database db = new Database ( view.getContext () );
        Cursor taskCursor = db.getCursor (
                new DatabaseValues.Table[] { DatabaseValues.Table.TASK },
                "WHERE " + DatabaseValues.Column._ID.toString () + " = " + taskID
        );

        taskCursor.moveToNext();

        taskTitleEditText = ( EditText ) view.findViewById ( R.id.taskTitleEditText );
        taskTitleEditText.setText (
                taskCursor.getString ( taskCursor.getColumnIndex (
                        DatabaseValues.Column.TASK_TITLE.toString () ) )
        );

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
        taskEstimatedPointSeekBar.setProgress ( taskCursor.getInt ( taskCursor.getColumnIndex (
                DatabaseValues.Column.TASK_ESTIMATED_COST.toString () ) ) );

        taskEstimatedCostTextView.setText (
                getResources ().getString ( R.string.task_estimated_cost )
                        + " " + taskEstimatedPointSeekBar.getProgress () );

        // yyyy-MM-dd HH:mm:ss.SSS
        String dateTime = taskCursor.getString ( taskCursor.getColumnIndex (
                DatabaseValues.Column.TASK_DUE_DATE.toString () ) );

        // yyyy-MM-dd
        String date = dateTime.split ( " " ) [ 0 ];
        int year = Integer.parseInt ( date.split ( "-" ) [ 0 ] );
        int month = Integer.parseInt ( date.split ( "-" ) [ 1 ] ) - 1;
        int day = Integer.parseInt ( date.split ( "-" ) [ 2 ] );

        taskDeadlineDatePicker = ( DatePicker ) view.findViewById ( R.id.taskDeadlineDatePicker );
        taskDeadlineDatePicker.updateDate (
                year,
                month,
                day
        );

        // HH:mm:ss.SSS
        String time = dateTime.split ( " " ) [ 1 ];
        Log.v ("PUCCI", "time = " + time);
        int hour;
        try
        {
            hour = Integer.parseInt ( time.split ( ":" ) [ 0 ] );
        }
        catch ( Exception e )
        {
            hour = Integer.parseInt ( "" + time.split ( ":" ) [ 0 ].charAt ( 1 ) );
        }

        int minute;
        try
        {
            minute = Integer.parseInt ( time.split ( ":" ) [ 1 ] );
        }
        catch ( Exception e )
        {
            minute = Integer.parseInt ( "" + time.split ( ":" ) [ 1 ].charAt ( 1 ) );
        }

        taskDeadlineTimePicker = ( TimePicker ) view.findViewById ( R.id.taskDeadlineTimePicker );
        taskDeadlineTimePicker.setCurrentHour ( hour );
        taskDeadlineTimePicker.setCurrentMinute ( minute );

        percentageCompletePointSeekBar = ( SeekBar ) view.findViewById ( R.id.percentageCompletePointSeekBar );
        percentageCompletePointSeekBar.setProgress (
                taskCursor.getInt ( taskCursor.getColumnIndex (
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
                        DatabaseValues.Column._ID.toString (),
                        "" + taskID
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
                else
                {
                    contentValues.putNull (
                            DatabaseValues.Column.TASK_COMPLETION_DATE_TIME.toString ()
                    );
                }


                boolean inserted = database.updateTask (
                        "" + taskID,
                        contentValues
                );

                Log.v (
                        "PUCCI",
                        "inserted = " + inserted
                );

                changeListener.notifyActionChange ( true );
                dismiss ();
            }
        } );

        return view;
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
