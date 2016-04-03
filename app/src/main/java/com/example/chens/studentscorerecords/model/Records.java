package com.example.chens.studentscorerecords.model;

/**
 * Created by chens on 2016/3/30.
 */
public class Records {
    private int ID;
    private double[] scores;
    public Records(int ID, double[] scores) {
        this.ID = ID;
        this.scores = scores;
    }

    public int getID() {
        return ID;
    }

    public double[] getScores() {
        return scores;
    }



}
