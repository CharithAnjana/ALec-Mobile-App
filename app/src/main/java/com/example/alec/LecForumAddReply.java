package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LecForumAddReply extends AppCompatActivity {

    String tID,sName,uName,date,ques,cID,fID,cName,User_ID,point,ustype;
    EditText edtReply;
    TextView sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_forum_add_reply);

        Intent intent =getIntent();
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
        ustype = intent.getStringExtra("user_type");

        edtReply = findViewById(R.id.edtReply);
        sub = findViewById(R.id.subject);
        sub.setText(sName);

    }

    public void ReplySubmit(View view){
        if(validateReply(edtReply)){
            String reply = edtReply.getText().toString();
            SessionManagement sessionManagement = new SessionManagement(this);
            String uID = sessionManagement.getSessionId();
            String type = "NewTopicReply";

            BackgroundWorkerForum backgroundWorkerForum = new BackgroundWorkerForum(this);
            String result;
            try {
                result = backgroundWorkerForum.execute(type, tID, reply, uID).get();

                if(result.equals("Success")){

                    Intent LecForumTopicReplyList = new Intent(this,LecForumTopicReplyList.class);
                    LecForumTopicReplyList.putExtra("tID",tID);
                    LecForumTopicReplyList.putExtra("sName",sName);
                    LecForumTopicReplyList.putExtra("uName",uName);
                    LecForumTopicReplyList.putExtra("date",date);
                    LecForumTopicReplyList.putExtra("ques",ques);
                    LecForumTopicReplyList.putExtra("cID", cID);
                    LecForumTopicReplyList.putExtra("fID", fID);
                    LecForumTopicReplyList.putExtra("cName", cName);
                    LecForumTopicReplyList.putExtra("User_ID", User_ID);
                    LecForumTopicReplyList.putExtra("point", point);
                    LecForumTopicReplyList.putExtra("user_type", ustype);
                    LecForumTopicReplyList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(LecForumTopicReplyList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Cancel(View view){
        finish();
    }

    public void Back(View view){
        finish();
    }

    private boolean validateReply(EditText rep){
        String reply = rep.getText().toString();
        if(!reply.isEmpty()){
            return true;
        }
        else {
            Toast.makeText(this, "Invalid Reply", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}