package com.example.alec;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LecDeleteTopicPop extends AppCompatActivity {

    String tID,tName,cID,cName,User_ID, result;
    String deletopicURL = "http://10.0.2.2/ALec/public/api/V1/deletecoursetopic.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_window_delete_topic);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.3));

        Intent intent =getIntent();
        tID = intent.getStringExtra("tID");
        tName = intent.getStringExtra("tName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("UserID");
    }

    public void Cancel(View view){
        //Intent intent = new Intent(this,LecQuizList.class);
        //intent.putExtra("tID",tID);
        //intent.putExtra("tName",tName);
        //intent.putExtra("cID",cID);
        //intent.putExtra("cName",cName);
        //intent.putExtra("UserID",User_ID);
        //startActivity(intent);
        finish();
    }

    public void DeleteConfirm(View view){
        String topic_ID = tID;
        String type = "DeleteTopic";

        BackgroundWorkerCourseTopic backgroundWorkerCourseTopic = new BackgroundWorkerCourseTopic(this);
        String result;
        try {
            result = backgroundWorkerCourseTopic.execute(type, topic_ID).get();

            if(result.equals("Success")){
                Intent LecCourseTopics = new Intent(this,LecCourseTopics.class);
                LecCourseTopics.putExtra("cID",cID);
                LecCourseTopics.putExtra("cName",cName);
                LecCourseTopics.putExtra("UserID",User_ID);
                LecCourseTopics.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LecCourseTopics);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
