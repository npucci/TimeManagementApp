package com.example.nicco.timemanagementapp.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
    }

    public Long insertData (
            DatabaseValues.Table table,
            ContentValues contentValues
    ) {
        databaseHelper.getWritableDatabase ();

//        Long id = databaseHelper.insert (
//              table
//              null
//              contentValues
//        )

        return 1L;
    }
}
