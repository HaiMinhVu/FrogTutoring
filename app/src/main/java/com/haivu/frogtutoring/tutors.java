package com.haivu.frogtutoring;

public class tutors {
    private int tuid;
    private String tuname;
    private String tusubject;
    private String tubiography;
    private String tuemail;
    private String tupass;
    private String tuphone;
    private double turate;
    private double tuprice;

    public tutors(int tuid, String tuname, String tusubject, String tubiography, String tuemail, String tupass, String tuphone, double turate, double tuprice) {
        this.tuid = tuid;
        this.tuname = tuname;
        this.tusubject = tusubject;
        this.tubiography = tubiography;
        this.tuemail = tuemail;
        this.tupass = tupass;
        this.tuphone = tuphone;
        this.turate = turate;
        this.tuprice = tuprice;

    }

    public tutors(String tuname, String tusubject) {
        this.tuname = tuname;
        this.tusubject = tusubject;
    }

    public tutors(String tuname, String tusubject, String tubiography, double turate, double tuprice, String tuemail) {
        this.tuname = tuname;
        this.tusubject = tusubject;
        this.tubiography = tubiography;
        this.turate = turate;
        this.tuprice = tuprice;
        this.tuemail = tuemail;
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

    public String getTubiography() {
        return tubiography;
    }

    public void setTubiography(String tubiography) {
        this.tubiography = tubiography;
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

    public String getTuphone() {
        return tuphone;
    }

    public void setTuphone(String tuphone) {
        this.tuphone = tuphone;
    }

    public double getTurate() {
        return turate;
    }

    public void setTurate(double turate) {
        this.turate = turate;
    }

    public double getTuprice() {
        return tuprice;
    }

    public void setTuprice(double tuprice) {
        this.tuprice = tuprice;
    }
}

