package com.example.alec;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(UserClass userClass){
        String type = userClass.getType();
        editor.putString(SESSION_KEY, type).commit();
    }

    public String getSession(){

        return sharedPreferences.getString(SESSION_KEY, "no");
    }

    public void removeSession(){
        editor.putString(SESSION_KEY, "no").commit();
    }

}
