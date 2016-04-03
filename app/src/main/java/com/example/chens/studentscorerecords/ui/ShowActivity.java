package com.example.chens.studentscorerecords.ui;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.chens.studentscorerecords.R;
import com.example.chens.studentscorerecords.exception.MyException;
import com.example.chens.studentscorerecords.model.Records;
import com.example.chens.studentscorerecords.util.DataBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ShowActivity extends AppCompatActivity {
    private Map<Integer, Records> IDSet = new HashMap<Integer, Records>();
    private double[] highScore = {-1, -1, -1, -1, -1};
    private double[] lowScore = {101, 101, 101, 101, 101};
    private double[] avgScore = new double[5];
    private int numOfStudents = 0;
    private TableLayout tableLayout;
    DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        tableLayout = (TableLayout) findViewById(R.id.resultTable);
        db = new DataBase(ShowActivity.this);
        //db.deleteAll();
        InputStream inputStream = getResources().openRawResource(R.raw.input);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        try {
            boolean inputIsValid = true;
            TableRow TR = new TableRow(this);
            TextView TV = new TextView(this);
            TV.setText("Stud        Q1          Q2          Q3          Q4          Q5");
            TR.addView(TV);
            tableLayout.addView(TR);
            while ((line = reader.readLine()) != null) {

                TR = new TableRow(this);
                TV = new TextView(this);

                StringBuilder output = new StringBuilder();

                String[] args = line.split("\t");
                int ID = Integer.parseInt(args[0]);
                if (ID < 1000 || ID > 9999) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);
                    File dir = getFilesDir();
                    MyException e = new MyException("ID out of range: " + String.valueOf(ID), builder, dir);
                    builder.show();
                    inputIsValid = false;
                    break;
                } else if (IDSet.containsKey(ID)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);
                    File dir = getFilesDir();
                    MyException e = new MyException("ID already exists: " + String.valueOf(ID), builder, dir);
                    builder.show();
                    inputIsValid = false;
                    break;
                }
                output.append(ID);

                double[] scores = new double[5];
                for (int i = 0; i < 5; i++) {
                    scores[i] = Double.parseDouble(args[i + 1]);
                    if (scores[i] < 0 || scores[i] > 100) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);
                        File dir = getFilesDir();
                        MyException e = new MyException("Score invalid", builder, dir);
                        builder.show();
                        inputIsValid = false;
                        break;
                    }
                    output.append("       ").append(scores[i]);
                }
                if (inputIsValid) {
                    for (int i = 0; i < 5; i++) {
                        if (scores[i] > highScore[i]) {
                            highScore[i] = scores[i];
                        }
                        if (scores[i] < lowScore[i]) {
                            lowScore[i] = scores[i];
                        }
                        avgScore[i] = (avgScore[i] * numOfStudents + scores[i]) / (numOfStudents + 1);
                    }
                    numOfStudents++;
                    Records records = new Records(ID, scores);
                    IDSet.put(ID, records);
                    db.insertRecords(records);
                    TV.setText(output.toString());
                    TR.addView(TV);
                    tableLayout.addView(TR);
                } else {
                    break;
                }
            }

            TR = new TableRow(this);
            TV = new TextView(this);
            TV.setText("");
            TR.addView(TV);
            tableLayout.addView(TR);

            TR = new TableRow(this);
            TV = new TextView(this);
            StringBuilder output = new StringBuilder();

            output.append("High");
            for (int i = 0; i < 5; i++){
                output.append("       ").append(String.format("%.1f",highScore[i]));
            }
            TV.setText(output.toString());
            TR.addView(TV);
            tableLayout.addView(TR);

            TR = new TableRow(this);
            TV = new TextView(this);
            output = new StringBuilder();
            output.append("Low ");
            for (int i = 0; i < 5; i++){
                output.append("       ").append(String.format("%.1f",lowScore[i]));
            }
            TV.setText(output.toString());
            TR.addView(TV);
            tableLayout.addView(TR);

            TR = new TableRow(this);
            TV = new TextView(this);
            output = new StringBuilder();
            output.append("Avg ");
            for (int i = 0; i < 5; i++){
                output.append("       ").append(String.format("%.1f",avgScore[i]));
            }
            TV.setText(output.toString());
            TR.addView(TV);
            tableLayout.addView(TR);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
