package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class stu_forum_add_reply extends AppCompatActivity {

    String tID,sName,uName,date,ques,rst="F";
    EditText edtReply;
    TextView sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_forum_add_reply);

        Intent intent =getIntent();
        tID = intent.getStringExtra("tID");
        sName = intent.getStringExtra("sName");
        uName = intent.getStringExtra("uName");
        date = intent.getStringExtra("date");
        ques = intent.getStringExtra("ques");

        edtReply = findViewById(R.id.edtReply);
        sub = findViewById(R.id.subject);
        sub.setText(sName);

        Switch toggle = (Switch) findViewById(R.id.switch1);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rst = "T";
                }
            }
        });
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
                result = backgroundWorkerForum.execute(type, tID, reply, uID, rst).get();

                if(result.equals("Success")){

                    Intent StuForumTopicReplyList = new Intent(this,stu_forum_topic_reply_list.class);
                    StuForumTopicReplyList.putExtra("tID",tID);
                    StuForumTopicReplyList.putExtra("sName",sName);
                    StuForumTopicReplyList.putExtra("uName",uName);
                    StuForumTopicReplyList.putExtra("date",date);
                    StuForumTopicReplyList.putExtra("ques",ques);
                    StuForumTopicReplyList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(StuForumTopicReplyList);
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