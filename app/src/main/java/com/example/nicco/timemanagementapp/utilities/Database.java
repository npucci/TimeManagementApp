package com.example.nicco.timemanagementapp.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public Long insertData (
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
}
