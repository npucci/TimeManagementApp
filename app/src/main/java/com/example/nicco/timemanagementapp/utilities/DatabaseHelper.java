package com.example.nicco.timemanagementapp.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.nicco.timemanagementapp.utilities.DatabaseValues.DATABASE_VERSION;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    private Context context;

    public DatabaseHelper ( Context context )
    {
        super (
                context,
                DatabaseValues.DATABASE_NAME,
                null,
                DATABASE_VERSION
        );

        this.context = context;
    }

    @Override
    public void onCreate ( SQLiteDatabase db )
    {
        try
        {
            db.execSQL ( DatabaseValues.CREATE_TABLE_CATEGORY );
            Log.v (
                    "PUCCI",
                    "Create Table: " + DatabaseValues.Table.CATEGORY.toString () + "\n"
            );

            db.execSQL ( DatabaseValues.CREATE_TABLE_GOAL );
            Log.v (
                    "PUCCI",
                    "Create Table: " + DatabaseValues.Table.GOAL.toString () + "\n"
            );

            db.execSQL ( DatabaseValues.CREATE_TABLE_RETROSPECTIVE );
            Log.v (
                    "PUCCI",
                    "Create Table: " + DatabaseValues.Table.RETROSPECTIVE.toString () + "\n"
            );

            db.execSQL ( DatabaseValues.CREATE_TABLE_TASK );
            Log.v (
                    "PUCCI",
                    "Create Table: " + DatabaseValues.Table.TASK.toString () + "\n"
            );

        }
        catch ( SQLException e )
        {
            Log.v (
                    "PUCCI",
                    e.getMessage ()
            );
        }

        initializeCategories ( db );
    }

    @Override
    public void onUpgrade (
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {
        try
        {
            db.execSQL ( DatabaseValues.DROP_TABLE_CATEGORY );
            Log.v (
                    "PUCCI",
                    "Drop Table: " + DatabaseValues.Table.CATEGORY.toString () + "\n"
            );

            db.execSQL ( DatabaseValues.DROP_TABLE_GOAL );
            Log.v (
                    "PUCCI",
                    "Drop Table: " + DatabaseValues.Table.GOAL.toString () + "\n"
            );

            db.execSQL ( DatabaseValues.DROP_TABLE_RETROSPECTIVE );
            Log.v (
                    "PUCCI",
                    "Drop Table: " + DatabaseValues.Table.RETROSPECTIVE.toString () + "\n"
            );

            db.execSQL ( DatabaseValues.DROP_TABLE_TASK );
            Log.v (
                    "PUCCI",
                    "Drop Table: " + DatabaseValues.Table.TASK.toString () + "\n"
            );

            onCreate ( db );
        }
        catch ( SQLException e )
        {
            Log.v (
                    "PUCCI",
                    e.getMessage ()
            );
        }
    }

    private void initializeCategories ( SQLiteDatabase db )
    {
        for ( DatabaseValues.Category category : DatabaseValues.Category.values () )
        {
            try
            {
                ContentValues contentValues = new ContentValues ();

                contentValues.put (
                        DatabaseValues.Column.CATEGORY_TYPE.toString (),
                        category.toString ()
                );

                db.insert (
                        DatabaseValues.Table.CATEGORY.toString (),
                        null,
                        contentValues
                );
            }

            catch ( SQLException e )
            {
                Log.v (
                        "PUCCI",
                        e.getMessage ()
                );
            }
        }
    }
}
