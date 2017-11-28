package com.example.nicco.timemanagementapp.fragments;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nicco.timemanagementapp.R;
import com.example.nicco.timemanagementapp.interfaces.ChangeListener;
import com.example.nicco.timemanagementapp.interfaces.NullChangeListener;
import com.example.nicco.timemanagementapp.utilities.Database;
import com.example.nicco.timemanagementapp.utilities.DatabaseValues;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

public class AddGoalDialogFragment extends DialogFragment
{
    public static final String FRAGMENT_TAG = "editGoalDialog";

    private ChangeListener changeListener;

    private EditText goalTitleEditText;
    private EditText goalDescriptionEditText;

    private Spinner categorySpinner;

    private Button saveButton;

    public static AddGoalDialogFragment newInstance (ChangeListener changeListener )
    {
        AddGoalDialogFragment addGoalDialogFragment = new AddGoalDialogFragment();
        if ( changeListener == null )
        {
            changeListener = new NullChangeListener ();
        }
        addGoalDialogFragment.setChangeListener ( changeListener );
        return addGoalDialogFragment;
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
                R.layout.dialog_fragment_add_goal,
                container,
                false
        );

        goalTitleEditText = ( EditText ) view.findViewById ( R.id.goalTitleEditText );
        goalDescriptionEditText = ( EditText ) view.findViewById ( R.id.goalDescriptionEditText );

        Database db = new Database ( view.getContext () );

        categorySpinner = ( Spinner ) view.findViewById ( R.id.categorySpinner );
        ArrayAdapter < String > arrayAdapter = new ArrayAdapter <> (
                view.getContext (),
                R.layout.spinner_item,
                db.getAllCategories ()
        );
        categorySpinner.setAdapter ( arrayAdapter );

        saveButton = ( Button ) view.findViewById ( R.id.saveButton );
        saveButton.setOnClickListener ( new View.OnClickListener()
        {
            @Override
            public void onClick ( View v )
            {
                String goalTitle = goalTitleEditText.getText ().toString ();
                String goalDescription = goalDescriptionEditText.getText ().toString ();
                String goalCategory = categorySpinner.getSelectedItem ().toString ( );
                boolean hasNeededInput =
                        goalTitle != null &&
                                !goalTitle.isEmpty () &&
                                goalCategory != null &&
                                !goalCategory.isEmpty ();

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
                        DatabaseValues.Column.GOAL_TITLE.toString (),
                        goalTitle
                );
                contentValues.put (
                        DatabaseValues.Column.GOAL_DESCRIPTION.toString (),
                        goalDescription
                );
                contentValues.put (
                        DatabaseValues.Column.GOAL_CATEGORY_TYPE.toString (),
                        goalCategory
                );

                Long insertedGoalID = database.insertNewGoal ( contentValues );

                Log.v (
                        "PUCCI",
                        "insertedGoalID = " + insertedGoalID
                );

                changeListener.notifyActionChange ( true );
                dismiss ();
            }
        } );

        return view;
    }

    private void setChangeListener ( ChangeListener changeListener )
    {
        this.changeListener = changeListener;
    }
}
