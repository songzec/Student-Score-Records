package com.example.chens.studentscorerecords.exception;

import android.app.AlertDialog;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by chens on 2016/3/31.
 */
public class MyException {

    private final String ERROR_TITLE = "ERROR";// Exception Dialog Title

    // Constructor
    public MyException(String s, AlertDialog.Builder b, File dir){
        Log.e(null, s);// log the exception in System.out of terminal
        try {
            long time = System.currentTimeMillis();
            String logName = "log-" + time + ".txt";
            File log = new File(dir, logName);
            BufferedWriter writer = new BufferedWriter(new FileWriter(log));
            writer.write("Error time(in milliseconds): " + time);
            writer.write(s);
            writer.close();
        } catch (Exception e1){
            e1.printStackTrace();
        }
        buildErrorDialog(s,b);
    }

    /*
    * buildErrorDialog()
    *
    * build the AlertDialog with information and error title
    *
    * */
    public void buildErrorDialog(String s, AlertDialog.Builder b){
        // set dialog title & message, and provide Button to dismiss
        b.setTitle(ERROR_TITLE);
        b.setMessage(s);
    }

}
