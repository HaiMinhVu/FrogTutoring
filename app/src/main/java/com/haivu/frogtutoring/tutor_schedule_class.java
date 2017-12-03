package com.haivu.frogtutoring;

/**
 * Created by haivu on 12/2/17.
 */

public class tutor_schedule_class {
    private int scheid;
    private int tuid;
    private String date;
    private String start;
    private String end;
    private String duration;
    private int status;

    public tutor_schedule_class(int scheid, int tuid, String date, String start, String end, String duration, int status) {
        this.scheid = scheid;
        this.tuid = tuid;
        this.date = date;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.status = status;
    }



    public int getScheid() {
        return scheid;
    }

    public void setScheid(int scheid) {
        this.scheid = scheid;
    }

    public int getTuid() {
        return tuid;
    }

    public void setTuid(int tuid) {
        this.tuid = tuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
