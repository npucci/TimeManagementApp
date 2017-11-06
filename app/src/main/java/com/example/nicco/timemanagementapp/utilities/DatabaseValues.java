package com.example.nicco.timemanagementapp.utilities;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 */

public class DatabaseValues
{
    public static final String DATABASE_NAME = "TimeManagementDatabase";

    public static final String CREATE_TABLE_GOAL =
            "CREATE TABLE " + Table.GOAL.tableName + " ( " +
                    Column.GOAL_KEY.getColumnNameAndDataType () +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Column.GOAL_TITLE.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.GOAL_DESCRIPTION.getColumnNameAndDataType () + ", " +
                    Column.CATEGORY_TYPE.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.GOAL_CREATION_DATE_TIME.getColumnNameAndDataType () + " NOT NULL DEFAULT ( DATETIME ( 'now' ) ), " +
                    Column.GOAL_COMPLETION_DATE_TIME.getColumnNameAndDataType () + ", " +
                    "FOREIGN KEY ( " + Column.CATEGORY_TYPE + " ) " +
                    "REFERENCES " + Table.CATEGORY + " ( " +
                    Column.CATEGORY_TYPE + " )" +
                    " )";
    public static final String DROP_TABLE_GOAL = "DROP TABLE IF EXISTS " + Table.GOAL;

    public static final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE " + Table.CATEGORY.tableName + " ( " +
                    Column.CATEGORY_KEY.getColumnNameAndDataType () +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Column.CATEGORY_TYPE.getColumnNameAndDataType () +
                    " NOT NULL" +", " +
                    "CONSTRAINT UC_" + Table.CATEGORY + " UNIQUE ( " +
                    Column.CATEGORY_TYPE + " ) " +
                    " )";
    public static final String DROP_TABLE_CATEGORY = "DROP TABLE IF EXISTS " + Table.CATEGORY;

    public static final String CREATE_TABLE_RETROSPECTIVE =
            "CREATE TABLE " + Table.RETROSPECTIVE.tableName + " ( " +
                    Column.RETROSPECTIVE_KEY.getColumnNameAndDataType () +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Column.RETROSPECTIVE_TITLE.getColumnNameAndDataType () +
                    " NOT NULL DEFAULT ( 'Entry' ), " +
                    Column.GOAL_KEY.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.RETROSPECTIVE_ENTRY.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.RETROSPECTIVE_CREATION_DATE.getColumnNameAndDataType () +
                    " NOT NULL DEFAULT ( DATETIME ( 'now' ) ), " +
                    "CONSTRAINT UC_Retrospective UNIQUE ( " +
                    Column.RETROSPECTIVE_CREATION_DATE + " ) " +
                    "FOREIGN KEY ( " + Column.GOAL_KEY + " ) " +
                    "REFERENCES " + Table.GOAL + " ( " +
                    Column.GOAL_KEY + " ) ON DELETE CASCADE" +
                    " )";
    public static final String DROP_TABLE_RETROSPECTIVE = "DROP TABLE IF EXISTS " +
            Table.RETROSPECTIVE;

    public static final String CREATE_TABLE_TASK =
            "CREATE TABLE " + Table.TASK.tableName + " ( " +
                    Column.TASK_KEY.getColumnNameAndDataType () +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Column.TASK_TITLE.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.GOAL_KEY.getColumnNameAndDataType () + " NOT NULL, " +
                    Column.TASK_DESCRIPTION.getColumnNameAndDataType () + ", " +
                    Column.TASK_ESTIMATED_COST.getColumnNameAndDataType () +
                    " NOT NULL DEFAULT ( 1 ), " +
                    Column.TASK_CREATION_DATE_TIME.getColumnNameAndDataType () +
                    " NOT NULL DEFAULT ( DATETIME ( 'now' ) ), " +
                    Column.TASK_COMPLETION_DATE_TIME.getColumnNameAndDataType () + ", " +
                    Column.TASK_DUE_DATE.getColumnNameAndDataType () + ", " +
                    "CONSTRAINT UC_" + Table.TASK + " UNIQUE ( " +
                    Column.GOAL_KEY + ", " +
                    Column.TASK_TITLE + " ) " +
                    "FOREIGN KEY ( " + Column.GOAL_KEY + " ) " +
                    "REFERENCES " + Table.GOAL + " ( " +
                    Column.GOAL_KEY + " ) ON DELETE CASCADE" +
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
        CATEGORY_KEY (
                "category_key",
                "INTEGER"
        ),
        CATEGORY_TYPE (
                "category_type",
                "VARCHAR ( " + CATEGORY_TYPE_VARCHAR_LENGTH + " )"
        ),

        GOAL_COMPLETION_DATE_TIME (
                "goal_completion_date_time",
                "DATETIME"
        ),
        GOAL_CREATION_DATE_TIME (
                "goal_creation_date_time",
                "DATETIME"
        ),
        GOAL_KEY (
                "goal_key",
                "INTEGER"
        ),
        GOAL_TITLE (
                "goal_title",
                "VARCHAR ( " + TITLE_VARCHAR_LENGTH + " )"
        ),
        GOAL_DESCRIPTION (
                "goal_description",
                "VARCHAR ( " + DESCRIPTION_VARCHAR_LENGTH + " )"
        ),

        RETROSPECTIVE_CREATION_DATE (
                "retrospective_creation_date",
                "DATE"
        ),
        RETROSPECTIVE_KEY (
                "retrospective_key",
                "INTEGER"
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
        TASK_CREATION_DATE_TIME (
                "task_creation_date_time",
                "DATETIME"
        ),
        TASK_DESCRIPTION (
                "task_description",
                "VARCHAR ( " + DESCRIPTION_VARCHAR_LENGTH + " )"
        ),
        TASK_DUE_DATE (
                "task_due_date",
                "DATETIME"
        ),
        TASK_ESTIMATED_COST (
                "task_estimated_cost",
                "INTEGER"
        ),
        TASK_KEY (
                "task_key",
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
