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
import android.widget.ToggleButton;

public class StuSessionLiveForumAddQuestion extends AppCompatActivity {

    String courseID,sessionID,sessionName, courseName,userID,rst="F";
    EditText que;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_session_live_forum_add_question);

        Intent intent = getIntent();
        courseID = intent.getStringExtra("cID");
        sessionID = intent.getStringExtra("sID");
        sessionName = intent.getStringExtra("sName");
        courseName = intent.getStringExtra("cName");
        userID = intent.getStringExtra("UserID");

        Switch toggle = (Switch) findViewById(R.id.switch1);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rst = "T";
                }
            }
        });

        que = findViewById(R.id.editTextTextMultiLine);
    }
    public void Cancel(View view){
        Intent liveForum = new Intent(this,StuSessionLiveForum.class);
        liveForum.putExtra("cID",courseID);
        liveForum.putExtra("sID",sessionID);
        liveForum.putExtra("sName",sessionName);
        liveForum.putExtra("cName",courseName);
        liveForum.putExtra("UserID",userID);
        startActivity(liveForum);
        overridePendingTransition(0, 0);
        finish();
    }

    public void Back(View view){
        Intent liveForum = new Intent(this,StuSessionLiveForum.class);
        liveForum.putExtra("cID",courseID);
        liveForum.putExtra("sID",sessionID);
        liveForum.putExtra("sName",sessionName);
        liveForum.putExtra("cName",courseName);
        liveForum.putExtra("UserID",userID);
        startActivity(liveForum);
        overridePendingTransition(0, 0);
        finish();
    }

    public void Create(View view){
        if(validateTopicForum(que)){
            String Question = que.getText().toString();
            String type = "AddQuestion";

            BackgroundWorkerLiveForumVotes backgroundWorkerForum = new BackgroundWorkerLiveForumVotes(this);
            String result;
            try {
                result = backgroundWorkerForum.execute(type, userID, Question, sessionID, rst).get();

                if(result.equals("Success")){

                    Intent liveForum = new Intent(this,StuSessionLiveForum.class);
                    liveForum.putExtra("cID",courseID);
                    liveForum.putExtra("sID",sessionID);
                    liveForum.putExtra("sName",sessionName);
                    liveForum.putExtra("cName",courseName);
                    liveForum.putExtra("UserID",userID);
                    liveForum.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(liveForum);
                    //overridePendingTransition(0, 0);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent liveForum = new Intent(this,StuSessionLiveForum.class);
        liveForum.putExtra("cID",courseID);
        liveForum.putExtra("sID",sessionID);
        liveForum.putExtra("sName",sessionName);
        liveForum.putExtra("cName",courseName);
        liveForum.putExtra("UserID",userID);
        startActivity(liveForum);
        overridePendingTransition(0, 0);
        finish();
    }

    private boolean validateTopicForum(EditText quest){
        String Quest = quest.getText().toString();
        if(!Quest.isEmpty()){
                return true;
        }
        else {
            Toast.makeText(this, "Invalid Question", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}