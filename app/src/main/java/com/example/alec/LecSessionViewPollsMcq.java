package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LecSessionViewPollsMcq extends AppCompatActivity {

    String qNo,qAns,question,qCnt, sID,sName,cID,cName;
    TextView ques, count, answer;
    Button Poll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_session_view_polls_mcq);

        Intent intent = getIntent();
        qNo = intent.getStringExtra("qNo");
        qAns = intent.getStringExtra("qAns");
        question = intent.getStringExtra("question");
        qCnt = intent.getStringExtra("qCnt");
        sID = intent.getStringExtra("sID");
        sName = intent.getStringExtra("sName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");

        ques = findViewById(R.id.textViewq);
        count = findViewById(R.id.textViewcount);
        Poll = findViewById(R.id.buttonSe);
        answer = findViewById(R.id.mcqAnswer);
        ques.setText(question);
        count.setText(qCnt);
        answer.setText(qAns);


        //To check Poll status
        String type = "PollStatus";
        BackgroundWorkerSessionQuestion BgWCheckPoints = new BackgroundWorkerSessionQuestion(this);
        String result;
        try {
            result = BgWCheckPoints.execute(type, qNo).get();
            if(result.equals("T")){
                Poll.setText("Disable");
                Poll.setBackgroundColor(Color.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Back(View view){
        Intent n = new Intent(this,LecViewSession.class);
        n.putExtra("sID",sID);
        n.putExtra("sName",sName);
        n.putExtra("cID",cID);
        n.putExtra("cName",cName);
        n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(n);
        finish();finish();
    }

    public void Refresh(View view){
        Toast.makeText(this, "Refreshing..", Toast.LENGTH_SHORT).show();
        Intent LecSessionViewPollsMcq = new Intent(getApplicationContext(),LecSessionViewPollsMcq.class);
        LecSessionViewPollsMcq.putExtra("sID",sID);
        LecSessionViewPollsMcq.putExtra("sName",sName);
        LecSessionViewPollsMcq.putExtra("cID",cID);
        LecSessionViewPollsMcq.putExtra("cName",cName);
        LecSessionViewPollsMcq.putExtra("qNo",qNo);
        LecSessionViewPollsMcq.putExtra("question",question);
        LecSessionViewPollsMcq.putExtra("qCnt",qCnt);
        LecSessionViewPollsMcq.putExtra("qAns",qAns);
        LecSessionViewPollsMcq.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(LecSessionViewPollsMcq);
        overridePendingTransition(0, 0);
        finish();
    }

    public void ManagePollMcq(View view){
        String type = "ManagePollStatus";
        BackgroundWorkerManageSession BgWCheckPoints = new BackgroundWorkerManageSession(this);
        String result;
        try {
            result = BgWCheckPoints.execute(type, sID, qNo).get();

            if(result != "Error"){
                Intent LecSessionViewPollsMcq = new Intent(getApplicationContext(),LecSessionViewPollsMcq.class);
                LecSessionViewPollsMcq.putExtra("sID",sID);
                LecSessionViewPollsMcq.putExtra("sName",sName);
                LecSessionViewPollsMcq.putExtra("cID",cID);
                LecSessionViewPollsMcq.putExtra("cName",cName);
                LecSessionViewPollsMcq.putExtra("qNo",qNo);
                LecSessionViewPollsMcq.putExtra("question",question);
                LecSessionViewPollsMcq.putExtra("qCnt",qCnt);
                LecSessionViewPollsMcq.putExtra("qAns",qAns);
                LecSessionViewPollsMcq.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LecSessionViewPollsMcq);
                overridePendingTransition(0, 0);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}