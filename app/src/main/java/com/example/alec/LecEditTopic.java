package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LecEditTopic extends AppCompatActivity {

    String cID,cName,tID,tName,User_ID;
    TextView Course;
    EditText TopicName,TopicDescrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_edit_topic);

        Intent intent =getIntent();
        tID = intent.getStringExtra("tID");
        tName = intent.getStringExtra("tName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("UserID");

        Course = findViewById(R.id.cName);
        TopicName = findViewById(R.id.editTextTopicName);
        TopicName.setText(tName);
        TopicDescrip = findViewById(R.id.editTextTextMultiLine);
    }

    public void Back(View view){
        finish();
    }

    public void Cancel(View view){
        finish();
    }

    public void Update(View view){
        if(validateTopic(TopicName)) {
            String topic_ID = tID;
            String topic_Name = TopicName.getText().toString();
            String type = "EditTopic";

            BackgroundWorkerCourseTopic backgroundWorkerCourseTopic = new BackgroundWorkerCourseTopic(this);
            String result;
            try {
                result = backgroundWorkerCourseTopic.execute(type, topic_ID, topic_Name).get();

                if (result.equals("Success")) {
                    Intent LecQuizList = new Intent(this, LecQuizList.class);
                    LecQuizList.putExtra("tID", tID);
                    LecQuizList.putExtra("tName", topic_Name);
                    LecQuizList.putExtra("cID", cID);
                    LecQuizList.putExtra("cName", cName);
                    LecQuizList.putExtra("UserID", User_ID);
                    LecQuizList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(LecQuizList);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateTopic(EditText Topic){
        String topic = Topic.getText().toString();
        if(!topic.isEmpty()){
            return true;
        }
        else {
            Toast.makeText(this, "Invalid Topic Name", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}