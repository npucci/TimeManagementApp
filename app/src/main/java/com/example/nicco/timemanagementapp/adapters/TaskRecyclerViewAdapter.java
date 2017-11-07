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

import static com.example.nicco.timemanagementapp.R.id.goalCreationDateTimeTextView;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter
        <TaskRecyclerViewAdapter.GoalViewHolder>
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
    public GoalViewHolder onCreateViewHolder (
            ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate (
                R.layout.task_recycler_view_item,
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

        holder.taskKeyTextView.setText (
                cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column._ID.toString ()
                ) )
        );

        holder.taskTitleTextView.setText (
                cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column.TASK_TITLE.toString ()
                ) )
        );

        holder.taskEstimatedCostTextView.setText (
                cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column.TASK_ESTIMATED_COST.toString ()
                ) )
        );

        holder.taskCreationDateTimeTextView.setText (
                cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column.TASK_CREATION_DATE_TIME.toString ()
                ) )
        );

        holder.taskCompletionDateTimeTextView.setText (
                cursor.getString ( cursor.getColumnIndex (
                        DatabaseValues.Column.TASK_COMPLETION_DATE_TIME.toString ()
                ) )
        );
    }


    @Override
    public int getItemCount ()
    {
        return cursorAdapter.getCount ();
    }

    public class GoalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView taskKeyTextView;
        public TextView taskTitleTextView;
        public TextView taskEstimatedCostTextView;
        public TextView taskCreationDateTimeTextView;
        public TextView taskCompletionDateTimeTextView;
        public LinearLayout myLayout;

        Context context;

        public GoalViewHolder ( View itemView )
        {
            super ( itemView );
            myLayout = ( LinearLayout ) itemView;

            taskKeyTextView = ( TextView ) itemView.findViewById ( R.id.goalKeyTextView );
            taskTitleTextView = ( TextView ) itemView.findViewById ( R.id.taskTitleTextView );
            taskEstimatedCostTextView = ( TextView ) itemView.findViewById ( R.id.taskEstimatedCostTextView );
            taskCreationDateTimeTextView = ( TextView ) itemView.findViewById ( goalCreationDateTimeTextView );
            taskCompletionDateTimeTextView = ( TextView ) itemView.findViewById ( R.id.goalCompletionDateTimeTextView );

            itemView.setOnClickListener ( this );
            context = itemView.getContext ();

        }

        @Override
        public void onClick ( View view )
        {
            Toast.makeText(
                    context,
                    taskKeyTextView.getText () +
                            " " + taskTitleTextView.getText () +
                            " " + taskEstimatedCostTextView.getText () +
                            " " + taskCreationDateTimeTextView.getText (),
                    Toast.LENGTH_LONG
            ).show ();

        }
    }
}