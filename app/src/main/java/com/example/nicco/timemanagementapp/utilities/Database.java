package com.example.nicco.timemanagementapp.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

public class Database
{
    private SQLiteDatabase db;
    private Context context;
    private final DatabaseHelper databaseHelper;

    public Database ( Context context )
    {
        this.context = context;
        databaseHelper = new DatabaseHelper ( context );
        db = databaseHelper.getWritableDatabase ();
    }

    public Long insertNewGoal ( ContentValues contentValues )
    {

        return insertData (
                DatabaseValues.Table.GOAL,
                contentValues
        );
    }

    public Long insertNewTask ( ContentValues contentValues )
    {

        Long insertedTaskID = insertData (
                DatabaseValues.Table.TASK,
                contentValues
        );

        return insertedTaskID;
    }

    public Long insertNewProgress ( ContentValues contentValues )
    {
        Long insertedProgressID = insertData (
                DatabaseValues.Table.PROGRESS,
                contentValues
        );

        return insertedProgressID;
    }

    private Long insertData (
            DatabaseValues.Table table,
            ContentValues contentValues
    ) {

        Long id = db.insert (
              table.toString (),
              null,
              contentValues
        );

        return id;
    }

    public boolean updateTask (
            String _ID,
            ContentValues contentValues
    ) {

        boolean taskUpdated =  updateData (
                DatabaseValues.Table.TASK,
                contentValues,
                DatabaseValues.Column._ID + " = " + "'" + _ID + "'"
        );

        String dateCompleted = contentValues.getAsString (
                DatabaseValues.Column.TASK_COMPLETION_DATE_TIME.toString () );

        updateGoalStatus (
                _ID,
                dateCompleted
        );

        return taskUpdated;
    }

    public boolean updateGoal (
            String _ID,
            ContentValues contentValues
    ) {

        boolean goalUpdated =  updateData (
                DatabaseValues.Table.GOAL,
                contentValues,
                DatabaseValues.Column._ID + " = " + "'" + _ID + "'"
        );

        return goalUpdated;
    }

    public Cursor getGoal ( String _ID )
    {
        Cursor cursor = getCursor (
                new DatabaseValues.Table [] { DatabaseValues.Table.GOAL },
                "WHERE " + DatabaseValues.Column._ID + " = " + "'" + _ID + "'"
        );

        if ( cursor.getCount () == 0 )
        {
            return null;
        }

        return cursor;
    }

    private void updateGoalStatus (
            String taskID,
            String dateCompleted
    ) {
        Cursor cursor = getCursor (
                new DatabaseValues.Table [] { DatabaseValues.Table.TASK },
                "WHERE " + DatabaseValues.Column._ID + " = " + "'" + taskID + "'"
        );
        Log.v("PUCCI", "here!");
        if ( !cursor.moveToFirst () )
        {
            Log.v("PUCCI", "here 2!");
            return;
        }

        String goalID = cursor.getString ( cursor.getColumnIndex (
                DatabaseValues.Column.GOAL_ID.toString () ) );

        Cursor allIncompleteTasksCursor = getCursor (
                new DatabaseValues.Table [] { DatabaseValues.Table.TASK },
                "WHERE " + DatabaseValues.Column.GOAL_ID + " = " + "'" + goalID + "'" + " AND " +
                        DatabaseValues.Column.TASK_COMPLETION_DATE_TIME + " IS NULL "
        );

        if ( dateCompleted != null && !allIncompleteTasksCursor.moveToFirst () )
        {
            ContentValues updateGoalContentValues = new ContentValues ();
            updateGoalContentValues.put (
                    DatabaseValues.Column.GOAL_COMPLETION_DATE_TIME.toString (),
                    dateCompleted
            );
            boolean goalUpdated = updateGoal (
                    goalID,
                    updateGoalContentValues
            );
            Log.v ("PUCCI", "complete goalUpdated = " + goalUpdated);
        }
        else
        {
            ContentValues updateGoalContentValues = new ContentValues ();
            updateGoalContentValues.putNull (
                    DatabaseValues.Column.GOAL_COMPLETION_DATE_TIME.toString () );
            boolean goalUpdated = updateGoal (
                    goalID,
                    updateGoalContentValues
            );
            Log.v ("PUCCI", "incomplete goalUpdated = " + goalUpdated);
        }

    }

    public String getExistingProgressID (
            String taskID,
            String date
    ) {
        String sql = "SELECT " + DatabaseValues.Column._ID +
                " FROM " + DatabaseValues.Table.PROGRESS +
                " WHERE " + DatabaseValues.Column.TASK_ID + " = " + "'" + taskID + "'" + " AND " +
                DatabaseValues.Column.PROGRESS_DATE + " = " + "'" + date + "'";

        Cursor cursor = db.rawQuery (
                sql,
                null
        );

        String progressID = null;
        if ( cursor.moveToFirst () )
        {
            progressID = cursor.getString ( 0 );
        }

        cursor.close ();
        return progressID;
    }

    public boolean updateProgress (
            String _ID,
            String taskID,
            ContentValues contentValues
    ) {

        return updateData (
                DatabaseValues.Table.PROGRESS,
                contentValues,
                DatabaseValues.Column.TASK_ID + " = " + "'" + taskID + "'" + " AND " +
                        DatabaseValues.Column._ID + " = " + "'" + _ID + "'"
        );
    }

    private boolean updateData (
            DatabaseValues.Table table,
            ContentValues contentValues,
            String where
    ) {

        int numRowsEffected = db.update (
                table.toString (),
                contentValues,
                where,
                null
        );

        return numRowsEffected != 0;
    }

    public String [] getAllCategories ()
    {
        Cursor cursor = db.rawQuery (
                "SELECT " + DatabaseValues.Column.GOAL_CATEGORY_TYPE +
                        " FROM " + DatabaseValues.Table.CATEGORY,
                null
        );

        ArrayList < String > results = new ArrayList ();
        cursor.moveToFirst ();
        while ( !cursor.isAfterLast () )
        {
            results.add ( cursor.getString ( 0 ) );
            cursor.moveToNext ();
        }
        return results.toArray ( new String [ results.size () ] );
    }

    public Cursor getCursor (
            DatabaseValues.Table [] tables,
            String where
    ) {
        if ( tables == null || tables.length == 0 )
        {
            return null;
        }
        else if ( where == null )
        {
            where = "";
        }
        else
        {
            where = " " + where;
        }

        String tableArgs = tables [ 0 ].toString ();
        for ( int i = 1 ; i < tables.length ; i++ )
        {
            tableArgs += " INNER JOIN " + tables [ i ];
        }

        String selectArgs = tables [ 0 ] + "." + DatabaseValues.Column._ID;
        for ( int i = 1 ; i < tables.length ; i++ )
        {
            selectArgs += ", " + tables [ i ] + "." + DatabaseValues.Column._ID;
        }
        selectArgs += ", *";

        String sql = "SELECT " + selectArgs + " FROM " + tableArgs + where +
                " ORDER BY " + tables [ 0 ] + "." + DatabaseValues.Column._ID + " DESC";

        return db.rawQuery (
                sql,
                null
        );
    }

    public Cursor getUrgentTasksCursor ()
    {

        String sql = "SELECT * FROM " + DatabaseValues.Table.TASK +
                " WHERE " + DatabaseValues.Column.TASK_COMPLETION_DATE_TIME + " IS NULL " +
                " ORDER BY " + "ABS ( julianday(" + DatabaseValues.Column.TASK_DUE_DATE +
                ") - julianday(DATETIME ('now', 'localtime') ) )" + " ASC, " +
            DatabaseValues.Column.TASK_ESTIMATED_COST + " DESC, " +
                DatabaseValues.Column.TASK_CREATION_DATE_TIME + " DESC LIMIT 10";

        return db.rawQuery (
                sql,
                null
        );
    }

    public int totalSpentHoursOnTask ( String taskID )
    {
        String sql = "SELECT SUM ( " + DatabaseValues.Column.PROGRESS_HOURS_SPENT + " )" +
                " FROM " + DatabaseValues.Table.PROGRESS +
                " WHERE " + DatabaseValues.Column.TASK_ID + " = " + taskID;

        Cursor sumResult = db.rawQuery (
                sql,
                null
        );

        sumResult.moveToFirst ();
        return sumResult.getInt ( 0 );
    }
}
