package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class LecSessionStopPop extends AppCompatActivity {

    String sID,sName,cID,cName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_session_stop_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.3));

        Intent intent = getIntent();
        sID = intent.getStringExtra("sID");
        sName = intent.getStringExtra("sName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
    }

    public void Cancel(View view){
        finish();
    }

    public void DeleteConfirm(View view){
        String type = "ManageStatus";
        BackgroundWorkerManageSession BgWCheckPoints = new BackgroundWorkerManageSession(this);
        String result;
        try {
            result = BgWCheckPoints.execute(type, sID, cID).get();

            if(result != "Error"){
                Intent LecViewSession = new Intent(getApplicationContext(),LecViewSession.class);
                LecViewSession.putExtra("sID",sID);
                LecViewSession.putExtra("sName",sName);
                LecViewSession.putExtra("cID",cID);
                LecViewSession.putExtra("cName",cName);
                LecViewSession.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LecViewSession);
                overridePendingTransition(0, 0);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}