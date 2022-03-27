package com.example.alec;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";
    String SESSION_ID = "session_id";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
//ToSave session
    public void saveSession(UserClass userClass){
        String type = userClass.getType();
        String id = userClass.getId();
        editor.putString(SESSION_KEY, type);
        editor.putString(SESSION_ID, id);
        editor.commit();
    }

    public String getSessionKey(){

        return sharedPreferences.getString(SESSION_KEY, "no");
    }

    public String getSessionId(){

        return sharedPreferences.getString(SESSION_ID, "no");
    }

    public void removeSession(){
        editor.putString(SESSION_KEY, "no");
        editor.putString(SESSION_ID, "no");
        editor.commit();
    }

}
