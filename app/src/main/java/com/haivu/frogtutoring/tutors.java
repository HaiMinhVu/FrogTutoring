package com.haivu.frogtutoring;

public class tutors {
    private int tuid;
    private String tuname;
    private String tusubject;
    private String tuemail;
    private String tupass;
    private double turate;

    public tutors(int tuid, String tuname, String tusubject, String tuemail, String tupass, double turate) {
        this.tuid = tuid;
        this.tuname = tuname;
        this.tusubject = tusubject;
        this.tuemail = tuemail;
        this.tupass = tupass;
        this.turate = turate;

    }

    public tutors(String tuname, String tusubject) {
        this.tuname = tuname;
        this.tusubject = tusubject;
        //this.turate = turate;
    }

    public tutors(String tuname, String tusubject, double turate) {
        this.tuname = tuname;
        this.tusubject = tusubject;
        this.turate = turate;
    }

    public int getTuid() {
        return tuid;
    }

    public void setTuid(int tuid) {
        this.tuid = tuid;
    }

    public String getTuname() {
        return tuname;
    }

    public void setTuname(String tuname) {
        this.tuname = tuname;
    }

    public String getTusubject() {
        return tusubject;
    }

    public void setTusubject(String tusubject) {
        this.tusubject = tusubject;
    }

    public String getTuemail() {
        return tuemail;
    }

    public void setTuemail(String tuemail) {
        this.tuemail = tuemail;
    }

    public String getTupass() {
        return tupass;
    }

    public void setTupass(String tupass) {
        this.tupass = tupass;
    }

    public double getTurate() {
        return turate;
    }

    public void setTurate(double turate) {
        this.turate = turate;
    }



}

