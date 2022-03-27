package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StuAttemptQuizDetails extends AppCompatActivity {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    String qId,qName,qPubTime,qClsTime,qDur,userID;
    TextView quizName, courseName, publishedDate, closeDate, duration;
    Long ih, im, is, lh, lm, ls, h, m, s,ch,cm,cs,cy,cmon,cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_attempt_quiz_details);

        Intent intent = getIntent();
        qId = intent.getStringExtra("qID");
        qName = intent.getStringExtra("qName");
        qPubTime = intent.getStringExtra("pubDate");
        qClsTime = intent.getStringExtra("clsDate");
        qDur = intent.getStringExtra("dur");
        userID = intent.getStringExtra("userID");

        quizName = findViewById(R.id.quizName);
        courseName = findViewById(R.id.courseName);
        publishedDate = findViewById(R.id.publishDate);
        closeDate = findViewById(R.id.closeDate);
        duration = findViewById(R.id.duration);

        quizName.setText(qName);
        //courseName.setText("Test DB");
        publishedDate.setText(qPubTime);
        closeDate.setText(qClsTime);
        duration.setText(qDur);

    }

    public void Back(View view) {
        finish();
    }

    public void AttemptQuiz(View view) {
        if(ValidateDateTime()) {
            Intent StuAttemptQuizConfirmPopup = new Intent(this, StuAttemptQuizConfirmPopup.class);
            StuAttemptQuizConfirmPopup.putExtra("qID", qId);
            StuAttemptQuizConfirmPopup.putExtra("qDur", qDur);
            //StuAttemptQuizConfirmPopup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(StuAttemptQuizConfirmPopup);
        }
    }

    private boolean ValidateDateTime() {
        int flag = 0;

        //Get Close Date Time
        String[] DateTime = qClsTime.split(" ");

        //----Get Today Date & Time----
        Date Today = new Date();
        String TD = dateFormat.format(Today);
        Date TodayTime = new Date();
        String TT = timeFormat.format(Today);
        try {
            Date clsDate =  dateFormat.parse(DateTime[0]);
            Date clsTime = timeFormat.parse(DateTime[1]);
            Today = dateFormat.parse(TD);
            TodayTime = timeFormat.parse(TT);

            if(Today.equals(clsDate)){
                if(TodayTime.after(clsTime)){
                    Toast.makeText(this, "Quiz Not Available", Toast.LENGTH_LONG).show();
                    flag =1;
                }
            }else if (Today.after(clsDate)) {
                Toast.makeText(this, "Quiz Not Available", Toast.LENGTH_LONG).show();
                flag = 1;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        //----------------------
        if(flag==0){
            return true;
        }
        else { return false; }
    }
}