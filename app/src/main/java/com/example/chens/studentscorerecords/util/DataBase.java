package com.example.chens.studentscorerecords.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chens.studentscorerecords.model.Records;

/**
 * Created by chens on 2016/3/30.
 */
public class DataBase {
    private DatabaseOpenHelper databaseOpenHelper;
    private final String tableName = "StudentRecordsTable";
    private final String StudentID = "StudentID";
    private final String Q1 = "Q1";
    private final String Q2 = "Q2";
    private final String Q3 = "Q3";
    private final String Q4 = "Q4";
    private final String Q5 = "Q5";
    private SQLiteDatabase database;

    public DataBase(Context context) {
        databaseOpenHelper =
                new DatabaseOpenHelper(context, "StudentRecordsDatabase", null, 1);
    }

    public void insertRecords(Records records) {
        ContentValues newRecords = new ContentValues();
        newRecords.put(StudentID, records.getID());
        newRecords.put(Q1, records.getScores()[0]);
        newRecords.put(Q2, records.getScores()[1]);
        newRecords.put(Q3, records.getScores()[2]);
        newRecords.put(Q4, records.getScores()[3]);
        newRecords.put(Q5, records.getScores()[4]);
        open();
        database.insert(tableName, null, newRecords);
        close();
    }
    public void open() throws SQLException {
        // create or open a database for reading/writing
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            database.close(); // close the database connection
        }
    }

    public void deleteAll() {
        open();
        database.delete(tableName, "1", null);
        close();
    }
    public Cursor getAllScore() {
        return database.query(tableName, new String[]{StudentID, Q1, Q2, Q3, Q4, Q5},
                null, null, null, null, null);
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String createQuery = "CREATE TABLE " + tableName + " ("
                    + StudentID + " INTEGER PRIMARY KEY,"
                    + Q1 + " DOUBLE,"
                    + Q2 + " DOUBLE,"
                    + Q3 + " DOUBLE,"
                    + Q4 + " DOUBLE,"
                    + Q5 + " DOUBLE);";
            db.execSQL(createQuery);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
