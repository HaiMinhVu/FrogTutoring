package com.haivu.frogtutoring;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by haivu on 11/25/17.
 */

public class UserSession {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public UserSession(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("mylogin", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin){
        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }

}
