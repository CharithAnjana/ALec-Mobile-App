package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class stu_forum_add_new_disc_topic extends AppCompatActivity {

    String cID, cName, fID, uID;
    TextView course;
    EditText sub, que;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_forum_add_new_disc_topic);

        Intent intent = getIntent();
        cID = intent.getStringExtra("cID");
        fID = intent.getStringExtra("fID");
        cName = intent.getStringExtra("cName");
        uID = intent.getStringExtra("uID");

        course = findViewById(R.id.cName);
        course.setText(cName);

        sub = findViewById(R.id.editTextSubName);
        que = findViewById(R.id.editTextTextMultiLine);

    }

    public void Cancel(View view){
        finish();
    }

    public void Back(View view){
        finish();
    }

    public void Create(View view){
        if(validateTopicForum(sub, que)){
            String Subject = sub.getText().toString();
            String Question = que.getText().toString();
            String type = "NewTopicF";

            BackgroundWorkerForum backgroundWorkerForum = new BackgroundWorkerForum(this);
            String result;
            try {
                result = backgroundWorkerForum.execute(type, Subject, Question, fID, uID).get();

                if(result.equals("Success")){

                    Intent StuForumDiscTopic = new Intent(this,stu_forum_disc_topic.class);
                    StuForumDiscTopic.putExtra("cID",cID);
                    StuForumDiscTopic.putExtra("fID",fID);
                    StuForumDiscTopic.putExtra("cName",cName);
                    StuForumDiscTopic.putExtra("UserID",uID);
                    StuForumDiscTopic.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(StuForumDiscTopic);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateTopicForum(EditText suject, EditText quest){
        String Suject = suject.getText().toString();
        String Quest = quest.getText().toString();
        if(!Suject.isEmpty()){
            if(!Quest.isEmpty()){
                return true;
            }
            else {
                Toast.makeText(this, "Invalid Question", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else {
            Toast.makeText(this, "Invalid Subject", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}