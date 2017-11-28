package com.example.nicco.timemanagementapp.utilities;

import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nicco on 2017-11-28.
 */

public class UtilityMethods
{
    public static String getDateTime (
            DatePicker datePicker,
            TimePicker timePicker
    ) {
        if ( datePicker == null )
        {
            return null;
        }

        if ( timePicker == null )
        {
            return null;
        }


        String year = "" + datePicker.getYear ();
        String month = "" + ( datePicker.getMonth () + 1 );
        if ( month.length () == 1 )
        {
            month = "0" + month;
        }
        String day = "" + datePicker.getDayOfMonth ();
        if ( day.length () == 1 )
        {
            day = "0" + day;
        }

        String hour = "" + timePicker.getCurrentHour ();
        if ( hour.length () == 1 )
        {
            hour = "0" + hour;
        }

        String minute = "" + timePicker.getCurrentMinute ();
        if ( minute.length () == 1 )
        {
            minute = "0" + day;
        }

        // YYYY-mm-dd HH:MM:SS
        String deadlineDateTime = year + "-" + month + "-" + day + " " + hour + ":" +
                minute + ":00.000";
        try
        {
            Date date = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" ).parse ( deadlineDateTime );
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return dateFormat.format( date );
        }
        catch ( ParseException e )
        {
            e.printStackTrace ();
        }
        return null;
    }

    public static String getDate ( DatePicker datePicker )
    {
        if ( datePicker == null )
        {
            return null;
        }


        String year = "" + datePicker.getYear ();
        String month = "" + ( datePicker.getMonth () + 1 );
        if ( month.length () == 1 )
        {
            month = "0" + month;
        }
        String day = "" + datePicker.getDayOfMonth ();
        if ( day.length () == 1 )
        {
            day = "0" + day;
        }

        // YYYY-mm-dd
        String deadlineDateTime = year + "-" + month + "-" + day;
        try
        {
            Date date = new SimpleDateFormat( "yyyy-MM-dd" ).parse ( deadlineDateTime );
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format( date );
        }
        catch ( ParseException e )
        {
            e.printStackTrace ();
        }
        return null;
    }
}
