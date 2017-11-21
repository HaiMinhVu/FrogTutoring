package com.haivu.frogtutoring;

/**
 * Created by haivu on 11/20/17.
 */

public class students {
    private int stid;
    private String stname;
    private String stemail;
    private String stpass;
    private String stphone;

    public students(int stid, String stname, String stemail, String stpass, String stphone) {
        this.stid = stid;
        this.stname = stname;
        this.stemail = stemail;
        this.stpass = stpass;
        this.stphone = stphone;
    }

    public int getStid() {
        return stid;
    }

    public void setStid(int stid) {
        this.stid = stid;
    }

    public String getStname() {
        return stname;
    }

    public void setStname(String stname) {
        this.stname = stname;
    }

    public String getStemail() {
        return stemail;
    }

    public void setStemail(String stemail) {
        this.stemail = stemail;
    }

    public String getStpass() {
        return stpass;
    }

    public void setStpass(String stpass) {
        this.stpass = stpass;
    }

    public String getStphone() {
        return stphone;
    }

    public void setStphone(String stphone) {
        this.stphone = stphone;
    }
}
