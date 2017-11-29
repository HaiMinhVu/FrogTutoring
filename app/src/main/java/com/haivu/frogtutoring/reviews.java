package com.haivu.frogtutoring;

/**
 * Created by haivu on 11/27/17.
 */

public class reviews {
    private int tuid;
    private int stid;
    private double rating;
    private String comment;
    private int count;

    public reviews(int tuid, int stid, double rating, String comment, int count) {
        this.tuid = tuid;
        this.stid = stid;
        this.rating = rating;
        this.comment = comment;
        this.count = count;
    }

    public reviews(double rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public int getTuid() {
        return tuid;
    }

    public void setTuid(int tuid) {
        this.tuid = tuid;
    }

    public int getStid() {
        return stid;
    }

    public void setStid(int stid) {
        this.stid = stid;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
