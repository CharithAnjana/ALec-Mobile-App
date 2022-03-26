package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LecSessionViewPollsShort extends AppCompatActivity {

    String qNo,qPubTime,question,qCnt, sID,sName,cID,cName;
    TextView ques, count;
    Button Poll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_session_view_polls_short);

        Intent intent = getIntent();
        qNo = intent.getStringExtra("qNo");
        //qPubTime = intent.getStringExtra("qPubTime");
        question = intent.getStringExtra("question");
        qCnt = intent.getStringExtra("qCnt");
        sID = intent.getStringExtra("sID");
        sName = intent.getStringExtra("sName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");

        ques = findViewById(R.id.textViewq);
        count = findViewById(R.id.textViewcount);
        Poll = findViewById(R.id.buttonSe);
        ques.setText(question);
        count.setText(qCnt);

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
        finish();
    }

    public void Refresh(View view){
        Toast.makeText(this, "Refreshing..", Toast.LENGTH_SHORT).show();
        Intent LecSessionViewPollsShort = new Intent(getApplicationContext(),LecSessionViewPollsShort.class);
        LecSessionViewPollsShort.putExtra("sID",sID);
        LecSessionViewPollsShort.putExtra("sName",sName);
        LecSessionViewPollsShort.putExtra("cID",cID);
        LecSessionViewPollsShort.putExtra("cName",cName);
        LecSessionViewPollsShort.putExtra("qNo",qNo);
        LecSessionViewPollsShort.putExtra("question",question);
        LecSessionViewPollsShort.putExtra("qCnt",qCnt);
        LecSessionViewPollsShort.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(LecSessionViewPollsShort);
        overridePendingTransition(0, 0);
        finish();
    }

    public void ManagePollOpen(View view){
        String type = "ManagePollStatus";
        BackgroundWorkerManageSession BgWCheckPoints = new BackgroundWorkerManageSession(this);
        String result;
        try {
            result = BgWCheckPoints.execute(type, sID, qNo).get();

            if(result != "Error"){
                Intent LecSessionViewPollsShort = new Intent(getApplicationContext(),LecSessionViewPollsShort.class);
                LecSessionViewPollsShort.putExtra("sID",sID);
                LecSessionViewPollsShort.putExtra("sName",sName);
                LecSessionViewPollsShort.putExtra("cID",cID);
                LecSessionViewPollsShort.putExtra("cName",cName);
                LecSessionViewPollsShort.putExtra("qNo",qNo);
                LecSessionViewPollsShort.putExtra("question",question);
                LecSessionViewPollsShort.putExtra("qCnt",qCnt);
                LecSessionViewPollsShort.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LecSessionViewPollsShort);
                overridePendingTransition(0, 0);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}