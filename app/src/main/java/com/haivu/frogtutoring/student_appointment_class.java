package com.haivu.frogtutoring;

/**
 * Created by haivu on 12/3/17.
 */

public class student_appointment_class {
    private int stapptid;
    private int tuid;
    private int stid;
    private String tuname;
    private String date;
    private String starttime;
    private String endtime;
    private int apptstatus;

    public student_appointment_class(int stapptid, int tuid, int stid, String tuname, String date, String starttime, String endtime, int apptstatus) {
        this.stapptid = stapptid;
        this.tuid = tuid;
        this.stid = stid;
        this.tuname = tuname;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.apptstatus = apptstatus;
    }

    public student_appointment_class(String tuname, String date, String starttime, String endtime, int stapptid, int tuid) {
        this.tuname = tuname;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.stapptid = stapptid;
        this.tuid = tuid;
    }

    public student_appointment_class(String tuname, String date, String starttime, String endtime) {
        this.tuname = tuname;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public int getStapptid() {
        return stapptid;
    }

    public void setStapptid(int stapptid) {
        this.stapptid = stapptid;
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

    public String getTuname() {
        return tuname;
    }

    public void setTuname(String tuname) {
        this.tuname = tuname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getApptstatus() {
        return apptstatus;
    }

    public void setApptstatus(int apptstatus) {
        this.apptstatus = apptstatus;
    }
}
