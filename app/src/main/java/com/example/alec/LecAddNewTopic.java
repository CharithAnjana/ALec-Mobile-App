package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LecAddNewTopic extends AppCompatActivity {

    String cID;
    String cName;
    String User_ID;
    TextView Course;
    EditText TopicName,TopicDescrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_add_new_topic);

        Intent intent =getIntent();
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("User_ID");

        Course = findViewById(R.id.cName);
        TopicName = findViewById(R.id.editTextTopicName);
        TopicDescrip = findViewById(R.id.editTextTextMultiLine);

        Course.setText(cName);

    }

    public void Create(View view){
        if(validateTopic(TopicName)){
            String course_ID = cID;
            String course_Name = cName;
            SessionManagement sessionManagement = new SessionManagement(this);
            String lecturer_ID = sessionManagement.getSessionId();
            String topic_Name = TopicName.getText().toString();
            String topic_Descrip = TopicDescrip.getText().toString();
            String type = "NewTopic";

            BackgroundWorkerCourseTopic backgroundWorkerCourseTopic = new BackgroundWorkerCourseTopic(this);
            String result;
            try {
                result = backgroundWorkerCourseTopic.execute(type, course_ID, lecturer_ID, topic_Name, topic_Descrip).get();

                if(result.equals("Success")){

                    Intent LecCourseTopics = new Intent(this,LecCourseTopics.class);
                    LecCourseTopics.putExtra("cID",course_ID);
                    LecCourseTopics.putExtra("cName",course_Name);
                    LecCourseTopics.putExtra("UserID",lecturer_ID);
                    startActivity(LecCourseTopics);
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

    public void Back(View view){
        finish();
    }

    public void Cancel(View view){
        finish();
    }
}