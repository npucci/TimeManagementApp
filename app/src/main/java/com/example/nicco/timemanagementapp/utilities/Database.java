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
                "SELECT " + DatabaseValues.Column.CATEGORY_TYPE.toString () +
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

//    public Cursor getSelectedDataCursor ( DatabaseValues.Table table, DatabaseValues.Column [] databaseColumns )
//    {
//        return db.query (
//                table.toString (),
//                enumArrayToStringArray ( databaseColumns ),
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//    }

    private String [] enumArrayToStringArray ( Enum [] enums )
    {
        if (enums == null )
        {
            return null;
        }

        String [] stringArray = new String [ enums.length ];
        for ( int i = 0 ; i < enums.length ; i++ )
        {
            stringArray [ i ] = enums [ i ].toString ();
        }

        return stringArray;
    }
}
