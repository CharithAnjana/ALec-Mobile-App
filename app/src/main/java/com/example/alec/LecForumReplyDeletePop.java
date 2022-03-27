package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class LecForumReplyDeletePop extends AppCompatActivity {
    String tID, sName, date, uName, ques, result, cID, fID, cName, User_ID, rID, point, ustype, reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_forum_reply_delete_pop);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int hight = dm.heightPixels;

        getWindow().setLayout((int) (width * .7), (int) (hight * .5));
        // WindowManager.LayoutParams params = getWindow().getAttributes();
        // params.gravity = Gravity.CENTER;
        // params.x = 0;
        // params.y = -20;

        // getWindow().setAttributes(params);


        Intent intent = getIntent();
        rID = intent.getStringExtra("rID");
        tID = intent.getStringExtra("tID");
        sName = intent.getStringExtra("sName");
        uName = intent.getStringExtra("uName");
        date = intent.getStringExtra("date");
        ques = intent.getStringExtra("ques");
        cID = intent.getStringExtra("cID");
        fID = intent.getStringExtra("fID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("User_ID");
        point = intent.getStringExtra("point");
        ustype = intent.getStringExtra("ustype");
        reply = intent.getStringExtra("reply");
    }

    public void DeleteConfirm(View view) {
        String reply_id = rID;
        String type = "DeleteReply";

        BackgroundWorkerForum backgroundWorkerForum = new BackgroundWorkerForum(this);
        String result;
        try {
            result = backgroundWorkerForum.execute(type, reply_id).get();

            if (result.equals("Success")) {
                Intent LecForumTopicReplyView = new Intent(this, LecForumTopicReplyList.class);
                LecForumTopicReplyView.putExtra("tID", rID);
                LecForumTopicReplyView.putExtra("tID", tID);
                LecForumTopicReplyView.putExtra("sName", sName);
                LecForumTopicReplyView.putExtra("uName", uName);
                LecForumTopicReplyView.putExtra("date", date);
                LecForumTopicReplyView.putExtra("ques", ques);
                LecForumTopicReplyView.putExtra("cID", cID);
                LecForumTopicReplyView.putExtra("cName", cName);
                LecForumTopicReplyView.putExtra("User_ID", User_ID);
                LecForumTopicReplyView.putExtra("point", point);
                LecForumTopicReplyView.putExtra("user_type", ustype);
                LecForumTopicReplyView.putExtra("fID", fID);

                startActivity(LecForumTopicReplyView);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Cancel(View view) {
        finish();
    }
}