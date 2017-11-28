package com.example.nicco.timemanagementapp.utilities;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

public class DatabaseValues
{
    public static final String DATABASE_NAME = "TimeManagementDatabase";

    public static final String CREATE_TABLE_GOAL =
            "CREATE TABLE " + Table.GOAL + " ( " +
                    Column._ID.getColumnNameAndDataType () +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Column.GOAL_TITLE.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.GOAL_DESCRIPTION.getColumnNameAndDataType () + ", " +
                    Column.GOAL_CATEGORY_TYPE.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.GOAL_CREATION_DATE_TIME.getColumnNameAndDataType () + " NOT NULL DEFAULT ( DATETIME ( 'now', 'localtime') ), " +
                    Column.GOAL_COMPLETION_DATE_TIME.getColumnNameAndDataType () + ", " +
                    "FOREIGN KEY ( " + Column.GOAL_CATEGORY_TYPE + " ) " +
                    "REFERENCES " + Table.CATEGORY + " ( " +
                    Column.GOAL_CATEGORY_TYPE + " )" +
                    " )";
    public static final String DROP_TABLE_GOAL = "DROP TABLE IF EXISTS " + Table.GOAL;

    public static final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE " + Table.CATEGORY + " ( " +
                    Column._ID.getColumnNameAndDataType () +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Column.GOAL_CATEGORY_TYPE.getColumnNameAndDataType () +
                    " NOT NULL" +", " +
                    "CONSTRAINT UC_" + Table.CATEGORY + " UNIQUE ( " +
                    Column.GOAL_CATEGORY_TYPE + " ) " +
                    " )";
    public static final String DROP_TABLE_CATEGORY = "DROP TABLE IF EXISTS " + Table.CATEGORY;

    public static final String CREATE_TABLE_RETROSPECTIVE =
            "CREATE TABLE " + Table.RETROSPECTIVE + " ( " +
                    Column._ID.getColumnNameAndDataType () +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Column.RETROSPECTIVE_TITLE.getColumnNameAndDataType () +
                    " NOT NULL DEFAULT ( 'Entry' ), " +
                    Column.GOAL_ID.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.RETROSPECTIVE_ENTRY.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.RETROSPECTIVE_CREATION_DATE.getColumnNameAndDataType () +
                    " NOT NULL DEFAULT ( DATETIME ( 'now', 'localtime') ), " +
                    "CONSTRAINT UC_Retrospective UNIQUE ( " +
                    Column.RETROSPECTIVE_CREATION_DATE + " ) " +
                    "FOREIGN KEY ( " + Column.GOAL_ID + " ) " +
                    "REFERENCES " + Table.GOAL + " ( " +
                    Column._ID + " ) ON DELETE CASCADE" +
                    " )";
    public static final String DROP_TABLE_RETROSPECTIVE = "DROP TABLE IF EXISTS " +
            Table.RETROSPECTIVE;

    public static final String CREATE_TABLE_TASK =
            "CREATE TABLE " + Table.TASK + " ( " +
                    Column._ID.getColumnNameAndDataType () +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Column.TASK_TITLE.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.GOAL_ID.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.TASK_ESTIMATED_COST.getColumnNameAndDataType () +
                    " NOT NULL DEFAULT ( 1 ), " +
                    Column.TASK_DUE_DATE.getColumnNameAndDataType () +
                    " NOT NULL DEFAULT ( DATETIME ( 'now', 'localtime') ), " +
                    Column.TASK_CREATION_DATE_TIME.getColumnNameAndDataType () +
                    " NOT NULL DEFAULT ( DATETIME ( 'now', 'localtime') ), " +
                    Column.TASK_COMPLETION_DATE_TIME.getColumnNameAndDataType () + ", " +
                    Column.TASK_COMPLETION_PERCENTAGE.getColumnNameAndDataType () + ", " +
                    "CONSTRAINT UC_" + Table.TASK + " UNIQUE ( " +
                    Column._ID + ", " +
                    Column.TASK_TITLE + " ) " +
                    "FOREIGN KEY ( " + Column.GOAL_ID + " ) " +
                    "REFERENCES " + Table.GOAL + " ( " +
                    Column._ID + " ) ON DELETE CASCADE" +
                    " )";

    public static final String CREATE_TABLE_PROGRESS =
            "CREATE TABLE " + Table.PROGRESS + " ( " +
                    Column._ID.getColumnNameAndDataType () +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Column.TASK_ID.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.PROGRESS_DATE.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.PROGRESS_HOURS_SPENT.getColumnNameAndDataType () +
                    " NOT NULL DEFAULT ( 0 ), " +
                    "CONSTRAINT UC_" + Table.PROGRESS + " UNIQUE ( " +
                    Column.TASK_ID + ", " +
                    Column.PROGRESS_DATE + " ) " +
                    "FOREIGN KEY ( " + Column.TASK_ID + " ) " +
                    "REFERENCES " + Table.TASK + " ( " +
                    Column._ID + " ) ON DELETE CASCADE" +
                    " )";
    public static final String DROP_TABLE_TASK = "DROP TABLE IF EXISTS " + Table.TASK;

    public static final int DATABASE_VERSION = 1;
    public static final int CATEGORY_TYPE_VARCHAR_LENGTH = 30;
    public static final int TITLE_VARCHAR_LENGTH = 30;
    public static final int DESCRIPTION_VARCHAR_LENGTH = 100;
    public static final int POST_VARCHAR_LENGTH = 500;

    public enum Table
    {
        CATEGORY ( "Category" ),
        GOAL ( "Goal" ),
        PROGRESS ( "Progress" ),
        RETROSPECTIVE ( "Retrospective" ),
        TASK ( "Task" );

        private String tableName;

        Table ( String tableName )
        {
            this.tableName = tableName;
        }

        @Override
        public String toString ()
        {
            return tableName;
        }
    }

    public enum Column
    {
        GOAL_CATEGORY_TYPE (
                "goal_category_type",
                "VARCHAR ( " + CATEGORY_TYPE_VARCHAR_LENGTH + " )"
        ),

        GOAL_ID (
                "goal_id",
                "INTEGER"
        ),
        GOAL_COMPLETION_DATE_TIME (
                "goal_completion_date_time",
                "DATETIME"
        ),
        GOAL_CREATION_DATE_TIME (
                "goal_creation_date_time",
                "DATETIME"
        ),
        GOAL_TITLE (
                "goal_title",
                "VARCHAR ( " + TITLE_VARCHAR_LENGTH + " )"
        ),
        GOAL_DESCRIPTION (
                "goal_description",
                "VARCHAR ( " + DESCRIPTION_VARCHAR_LENGTH + " )"
        ),

        _ID (
                "_id",
                "INTEGER"
        ),

        PROGRESS_DATE(
                "progress_date",
                "DATE"
        ),
        PROGRESS_HOURS_SPENT (
                "progress_hours_spent",
                "INTEGER"
        ),

        RETROSPECTIVE_CREATION_DATE (
                "retrospective_creation_date",
                "DATE"
        ),
        RETROSPECTIVE_ENTRY (
                "retrospective_entry",
                "VARCHAR ( " + POST_VARCHAR_LENGTH + " )"
        ),
        RETROSPECTIVE_TITLE (
                "retrospective_title",
                "VARCHAR ( " + TITLE_VARCHAR_LENGTH + " )"
        ),

        TASK_COMPLETION_DATE_TIME (
                "task_completion_date_time",
                "DATETIME"
        ),
        TASK_COMPLETION_PERCENTAGE (
                "task_completion_percentage",
                "INTEGER"
        ),
        TASK_CREATION_DATE_TIME (
                "task_creation_date_time",
                "DATETIME"
        ),
        TASK_DUE_DATE (
                "task_due_date",
                "DATETIME"
        ),
        TASK_ESTIMATED_COST (
                "task_estimated_cost",
                "INTEGER"
        ),
        TASK_ID (
                "task_id",
                "INTEGER"
        ),
        TASK_TITLE (
                "task_name",
                "VARCHAR ( " + TITLE_VARCHAR_LENGTH + " )"
        );

        private String columnName;
        private String dataType;

        Column ( String columnName , String dataType )
        {
            this.columnName = columnName;
            this.dataType = dataType;
        }

        @Override
        public String toString ()
        {
            return columnName;
        }

        public String getDataType ()
        {
            return dataType;
        }

        public String getColumnNameAndDataType ()
        {
            return columnName + " " + dataType;
        }
    }

    public enum Category
    {
        HEALTH ( "health" ),
        HOBBY ( "hobby" ),
        OTHER ( "other" ),
        PERSONAL ( "personal" ),
        RESPONSIBILITY ( "responsibility" ),
        SCHOOL ( "school" ),
        SOCIAL ( "social" ),
        WORK ( "work" );


        private String categoryName;

        Category ( String categoryName )
        {
            this.categoryName = categoryName;
        }

        @Override
        public String toString ()
        {
            return categoryName;
        }
    }
}
