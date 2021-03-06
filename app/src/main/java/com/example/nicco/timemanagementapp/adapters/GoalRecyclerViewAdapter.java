package com.example.nicco.timemanagementapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicco.timemanagementapp.R;
import com.example.nicco.timemanagementapp.activities.GoalTaskManagerActivity;
import com.example.nicco.timemanagementapp.utilities.DatabaseValues;

public class GoalRecyclerViewAdapter extends RecyclerView.Adapter
        < GoalRecyclerViewAdapter.GoalViewHolder >
{
    private Context context;
    private CursorAdapter cursorAdapter;

    public GoalRecyclerViewAdapter (
            Context context,
            Cursor cursor
    ) {
        this.context = context;

        cursorAdapter = new CursorAdapter (
                context,
                cursor,
                1
        ) {
            @Override
            public View newView (
                    Context context,
                    Cursor cursor,
                    ViewGroup viewGroup
            ) {
                return null;
            }

            @Override
            public void bindView (
                    View view,
                    Context context,
                    Cursor cursor
            ) {

            }
        };
    }

    @Override
    public GoalViewHolder onCreateViewHolder (
            ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate (
                R.layout.goal_recycler_view_item,
                parent,
                false
        );
        GoalViewHolder viewHolder = new GoalViewHolder( view );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (
            GoalViewHolder holder,
            int position
    ) {

        cursorAdapter.getCursor ().moveToPosition ( position );
        Cursor cursor = cursorAdapter.getCursor ();

        holder.goalKeyTextView.setText (
                cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column._ID.toString ()
                ) )
        );

        holder.goalTitleTextView.setText (
                "Title: " + cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column.GOAL_TITLE.toString ()
                ) )
        );

        holder.goalDescriptionTextView.setText (
                "Description: " + cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column.GOAL_DESCRIPTION.toString ()
                ) )
        );

        holder.categoryTypeTextView.setText (
                "Category: " + cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column.GOAL_CATEGORY_TYPE.toString ()
                ) )
        );

        holder.goalCreationDateTimeTextView.setText (
                "Created: " + cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column.GOAL_CREATION_DATE_TIME.toString ()
                ) )
        );

        String completedDateTime = cursor.getString ( cursor.getColumnIndex (
                DatabaseValues.Column.GOAL_COMPLETION_DATE_TIME.toString ()
        ) );
        if ( completedDateTime == null || completedDateTime.isEmpty () )
        {
            completedDateTime = "Status: Ongoing";
        }
        else
        {
            completedDateTime = "Completed: " + completedDateTime;
        }
        holder.goalCompletionDateTimeTextView.setText (
                completedDateTime
        );
    }


    @Override
    public int getItemCount ()
    {
        return cursorAdapter.getCount ();
    }

    public class GoalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView goalKeyTextView;
        public TextView goalTitleTextView;
        public TextView goalDescriptionTextView;
        public TextView categoryTypeTextView;
        public TextView goalCreationDateTimeTextView;
        public TextView goalCompletionDateTimeTextView;
        public LinearLayout myLayout;

        Context context;

        public GoalViewHolder ( View itemView )
        {
            super ( itemView );
            myLayout = ( LinearLayout ) itemView;

            goalKeyTextView = ( TextView ) itemView.findViewById ( R.id.goalKeyTextView );
            goalTitleTextView = ( TextView ) itemView.findViewById ( R.id.goalTitleTextView );
            goalDescriptionTextView = ( TextView ) itemView.findViewById ( R.id.goalDescriptionTextView );
            categoryTypeTextView = ( TextView ) itemView.findViewById ( R.id.categoryTypeTextView );
            goalCreationDateTimeTextView = ( TextView ) itemView.findViewById ( R.id.goalCreationDateTimeTextView );
            goalCompletionDateTimeTextView = ( TextView ) itemView.findViewById ( R.id.goalCompletionDateTimeTextView );

            itemView.setOnClickListener ( this );
            context = itemView.getContext ();

        }

        @Override
        public void onClick ( View view )
        {
            Toast.makeText(
                    context,
                            goalKeyTextView.getText () +
                            " " + goalTitleTextView.getText () +
                            " " + categoryTypeTextView.getText () +
                            " " + goalCreationDateTimeTextView.getText (),
                    Toast.LENGTH_LONG
            ).show ();

            Intent intent = new Intent (
                    context,
                    GoalTaskManagerActivity.class
            );

            intent.putExtra (
                    DatabaseValues.Column._ID.toString (),
                    goalKeyTextView.getText ().toString ()
            );
            intent.putExtra (
                    DatabaseValues.Column.GOAL_TITLE.toString (),
                    goalTitleTextView.getText ().toString ()
            );
            intent.putExtra (
                    DatabaseValues.Column.GOAL_DESCRIPTION.toString (),
                    goalDescriptionTextView.getText ().toString ()
            );
            intent.putExtra (
                    DatabaseValues.Column.GOAL_CATEGORY_TYPE.toString (),
                    categoryTypeTextView.getText ().toString ()
            );
            intent.putExtra (
                    DatabaseValues.Column.GOAL_CREATION_DATE_TIME.toString (),
                    goalCreationDateTimeTextView.getText ().toString ()
            );
            intent.putExtra (
                    DatabaseValues.Column.GOAL_COMPLETION_DATE_TIME.toString (),
                    goalCompletionDateTimeTextView.getText ().toString ()
            );


            context.startActivity ( intent );
        }
    }
}