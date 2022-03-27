package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class LecForumTopicDeletePop extends AppCompatActivity {
    String tID,sName,date,uName,ques, result, cID,fID,cName,User_ID;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_forum_topic_delete_pop);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int hight = dm.heightPixels;

        getWindow().setLayout((int)(width *.7),(int)(hight * .5));
        // WindowManager.LayoutParams params = getWindow().getAttributes();
        // params.gravity = Gravity.CENTER;
        // params.x = 0;
        // params.y = -20;

        // getWindow().setAttributes(params);
        Intent intent =getIntent();

        tID = intent.getStringExtra("tID");
        sName = intent.getStringExtra("sName");
        date= intent.getStringExtra("date");
        uName = intent.getStringExtra("uName");
        ques = intent.getStringExtra("ques");
        cID = intent.getStringExtra("cID");
        fID = intent.getStringExtra("fID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("User_ID");
    }
    public void DeleteConfirm(View view){
        String topic_ID = tID;
        String type = "DeleteTopic";

        BackgroundWorkerForum backgroundWorkerForum  = new  BackgroundWorkerForum(this);
        String result;
        try {
            result = backgroundWorkerForum.execute(type, topic_ID).get();

            if(result.equals("Success")){
                Intent LecCourseTopics = new Intent(this,LecForumDiscTopic.class);
                LecCourseTopics.putExtra("fID",fID);
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
    public void Cancel(View view){
        finish();
    }
}