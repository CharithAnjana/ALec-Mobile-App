package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Struct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StuSessionPollsAttemptShort extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    private CountDownTimer countDownTimer;
    private long timeLeft, initTime;

    String qNo,question,qPubTime,qDur,userID,sessionID;
    TextView Question,qTime;
    Long ih,im,is,lh,lm,ls,h,m,s;
    EditText Ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_session_polls_attempt_short);

        Ans = findViewById(R.id.editTextTextAns);

        Intent intent = getIntent();
        qNo = intent.getStringExtra("qNo");
        question = intent.getStringExtra("question");
        userID = intent.getStringExtra("userID");
        qPubTime = intent.getStringExtra("qPubTime");
        qDur = intent.getStringExtra("qDur");
        sessionID = intent.getStringExtra("sID");

        String[] DateTime = qPubTime.split(" ");
        String[] Time = DateTime[1].split(":");
        ih = Long.parseLong(Time[0]);
        im = Long.parseLong(Time[1]);
        is = Long.parseLong(Time[2]);

        String[] TimeDur = qDur.split(":");
        h = Long.parseLong(TimeDur[0]);
        m = Long.parseLong(TimeDur[1]);
        s = Long.parseLong(TimeDur[2]);

        lh = ih + h;
        lm = im + m;
        ls = is + s;

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date today = new Date();
        Date last = new Date();
        String Now = timeFormat.format(today);
        try {
            today = timeFormat.parse(Now);
            last = timeFormat.parse(lh+":"+lm+":"+ls);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (today.after(last)){
            Intent polls = new Intent(getApplicationContext(), StuSessionPolls.class);
            polls.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            polls.putExtra("sID", sessionID);
            polls.putExtra("UserID", userID);
            startActivity(polls);
            overridePendingTransition(0, 0);
            Toast.makeText(this, "Question Timeout..", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {

            String[] NowTime = Now.split(":");
            ih = Long.parseLong(NowTime[0]);
            im = Long.parseLong(NowTime[1]);
            is = Long.parseLong(NowTime[2]);

            initTime = ((lh - ih) * 3600 + (lm - im) * 60 + (ls - is)) * 1000;
            timeLeft = initTime;

            Question = findViewById(R.id.questionShort);
            qTime = findViewById(R.id.time);
            Question.setText(question);

            countDownTimer = new CountDownTimer(initTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeft = millisUntilFinished;
                    updateTime();
                }

                @Override
                public void onFinish() {
                    String a = Ans.getText().toString();
                    if(a.isEmpty()){
                        Intent polls = new Intent(getApplicationContext(), StuSessionPolls.class);
                        polls.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        polls.putExtra("sID", sessionID);
                        polls.putExtra("UserID", userID);
                        startActivity(polls);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                    else{
                        submit(a);
                    }
                }
            }.start();
        }
    }

    public void updateTime(){
        int hr = (int) timeLeft / 3600000;
        int min = (int) (timeLeft % 3600000) / 60000;
        int sec = (int) (timeLeft % 60000) / 1000;

        String timeLeft;
        timeLeft = hr + " : " + min + " : ";
        if(sec < 10){
            timeLeft += "0";
        }
        timeLeft += sec;
        qTime.setText(timeLeft);
    }

    public void Submit(View view){
        if(ValidateAns(Ans)) {
            String ans = Ans.getText().toString();
            submit(ans);
        }
    }

    private boolean ValidateAns(EditText ans){
        String a = ans.getText().toString();
        if(a.isEmpty()){
            Toast.makeText(this, "Please enter an Answer", Toast.LENGTH_SHORT).show();
            return false;}
        else {return true;}
    }

    @Override
    public void onBackPressed() {
        TwoBack();
    }
    public void Back(View view){
        TwoBack();
    }

    private void submit(String ans){
        String qno = qNo;
        String userId = userID;
        String type = "ShortAnsAtmp";

        BackgroundWorkerSessionQuestion backgroundWorkerSessionQuestion = new BackgroundWorkerSessionQuestion(this);
        String result;
        try {
            result = backgroundWorkerSessionQuestion.execute(type, qno, userId, ans).get();

            if (result.equals("Success")) {
                Intent StuSessionPolls = new Intent(this, StuSessionPolls.class);
                StuSessionPolls.putExtra("UserID", userID);
                StuSessionPolls.putExtra("sID", sessionID);
                StuSessionPolls.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(StuSessionPolls);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TwoBack(){
        if (doubleBackToExitPressedOnce) {
            Intent StuSessionPolls = new Intent(getApplicationContext(), StuSessionPolls.class);
            StuSessionPolls.putExtra("sID",sessionID);
            StuSessionPolls.putExtra("UserID",userID);
            StuSessionPolls.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(StuSessionPolls);
            overridePendingTransition(0, 0);
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}