package com.example.nicco.timemanagementapp.adapters;

import android.content.Context;
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
import com.example.nicco.timemanagementapp.utilities.DatabaseValues;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter
        < TaskRecyclerViewAdapter.TaskViewHolder >
{
    private Context context;
    private CursorAdapter cursorAdapter;

    public TaskRecyclerViewAdapter (
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
    public TaskViewHolder onCreateViewHolder (
            ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate (
                R.layout.task_recycler_view_item,
                parent,
                false
        );
        TaskViewHolder viewHolder = new TaskViewHolder( view );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (
            TaskViewHolder holder,
            int position
    ) {

        cursorAdapter.getCursor ().moveToPosition ( position );
        Cursor cursor = cursorAdapter.getCursor ();

        try
        {
            int taskKey = Integer.parseInt ( cursor.getString ( cursor.getColumnIndex (
                    DatabaseValues.Column._ID.toString ()
            ) ) );
            holder.taskKey = taskKey;
        }

        catch ( Exception e )
        {
            holder.taskKey = -1;
        }

        try
        {
            int taskEstimatedCost = Integer.parseInt ( cursor.getString ( cursor.getColumnIndex (
                    DatabaseValues.Column.TASK_ESTIMATED_COST.toString ()
            ) ) );
            holder.taskEstimatedCost = taskEstimatedCost;
        }

        catch ( Exception e )
        {
            holder.taskEstimatedCost = -1;
        }

        holder.taskTitleTextView.setText (
                "Task: " + cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column.TASK_TITLE.toString ()
                ) )
        );

        try
        {
            float completionPercentage = Float.parseFloat ( cursor.getString ( cursor.getColumnIndex (
                    DatabaseValues.Column.TASK_COMPLETION_PERCENTAGE.toString ()
            ) ) );
            holder.taskCompletionPercentageTextView.setText (
                    "Completion Percentage: " + completionPercentage
            );
        }

        catch ( Exception e )
        {
            holder.taskCompletionPercentageTextView.setText ( "Completion %: -1" );
        }

        holder.taskCreationDateTimeTextView.setText (
                "Created: " + cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column.TASK_CREATION_DATE_TIME.toString ()
                ) )
        );

        holder.taskDeadlineTextView.setText (
                "Due Date: " + cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column.TASK_DUE_DATE.toString ()
                ) )
        );


        String completedDateTime = cursor.getString ( cursor.getColumnIndex (
                DatabaseValues.Column.TASK_COMPLETION_DATE_TIME.toString () ) );
        if ( completedDateTime == null || completedDateTime.isEmpty () )
        {
            completedDateTime = "Completion Date: Ongoing";

        }
        holder.taskCompletionDateTimeTextView.setText ( "Completion Date: " + completedDateTime );
    }


    @Override
    public int getItemCount ()
    {
        return cursorAdapter.getCount ();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public int taskKey;
        public int taskEstimatedCost;
        public TextView taskTitleTextView;
        public TextView taskCompletionPercentageTextView;
        public TextView taskDeadlineTextView;
        public TextView taskCreationDateTimeTextView;
        public TextView taskCompletionDateTimeTextView;
        public LinearLayout myLayout;

        Context context;

        public TaskViewHolder ( View itemView )
        {
            super ( itemView );
            myLayout = ( LinearLayout ) itemView;

            taskKey = -1;
            taskEstimatedCost = -1;
            taskTitleTextView = ( TextView ) itemView.findViewById
                    ( R.id.taskTitleTextView );
            taskCompletionPercentageTextView = ( TextView ) itemView.findViewById
                    ( R.id.taskCompletionPercentageTextView );
            taskDeadlineTextView = ( TextView ) itemView.findViewById
                    ( R.id.taskDeadlineTextView );
            taskCreationDateTimeTextView = ( TextView ) itemView.findViewById
                    ( R.id.taskCreationDateTimeTextView );
            taskCompletionDateTimeTextView = ( TextView ) itemView.findViewById
                    ( R.id.taskCompletionDateTimeTextView );

            itemView.setOnClickListener ( this );
            context = itemView.getContext ();

        }

        @Override
        public void onClick ( View view )
        {
            Toast.makeText(
                    context,
                    "task key = " + taskKey + ", taskEstimatedCost = " + taskEstimatedCost,
                    Toast.LENGTH_LONG
            ).show ();

        }
    }
}