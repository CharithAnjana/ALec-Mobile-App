package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LecScheduleQuiz extends AppCompatActivity {

    String qID, quName, qDuHr;
    TextView quizName;
    EditText OpDate, OpTime, ClsDate, ClsTime, Dur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_schedule_quiz);

        Intent intent = getIntent();
        qID = intent.getStringExtra("qID");
        quName = intent.getStringExtra("qName");
        qDuHr = intent.getStringExtra("qDuHr");

        quizName = findViewById(R.id.qName);
        quizName.setText(quName);

        OpDate = findViewById(R.id.editTextPubDate);
        OpTime = findViewById(R.id.editTextPubTime);
        ClsDate = findViewById(R.id.editTextClsDate);
        ClsTime = findViewById(R.id.editTextClsTime);

        Dur = findViewById(R.id.editTextDur);
        Dur.setText(qDuHr);

    }

    public void Back(View view){
        finish();
    }

    public void Cancel(View view){
        finish();
    }

    public void Schedule(View view){
        if(ValidateDateTime(OpDate,OpTime,ClsDate,ClsTime,Dur)){
            finish();
        }
    }


    private boolean ValidateDateTime(EditText ODate,EditText OTime,EditText CDate,EditText CTime,EditText Du){
        String OD = ODate.getText().toString();
        String OT = OTime.getText().toString();
        String CD = CDate.getText().toString();
        String CT = CTime.getText().toString();
        String DU = Du.getText().toString();
        if((!OD.isEmpty()) && (!OT.isEmpty()) && (!CD.isEmpty()) && (!CT.isEmpty()) && (!DU.isEmpty())){
            return true;
        }
        else {
            Toast.makeText(this, "All the inputs must be filed", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}