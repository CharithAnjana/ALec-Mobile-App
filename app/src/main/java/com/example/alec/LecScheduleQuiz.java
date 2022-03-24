package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.InputType;
import android.text.format.DateFormat;
import android.text.style.CharacterStyle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LecScheduleQuiz extends AppCompatActivity {

    String qID, quName, qDuHr, Val, tID,tName,cID,cName,User_ID,qDuhr;
    TextView quizName, OpDate, OpTime, ClsDate, ClsTime;
    EditText Dur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_schedule_quiz);

        Intent intent = getIntent();
        qID = intent.getStringExtra("qID");
        quName = intent.getStringExtra("qName");
        qDuHr = intent.getStringExtra("qDuHr");
        Val = intent.getStringExtra("Val");
        tID = intent.getStringExtra("tID");
        tName = intent.getStringExtra("tName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("UserID");
        quizName = findViewById(R.id.qName);
        quizName.setText(quName);

        OpDate = findViewById(R.id.editTextPubDate);
        OpTime = findViewById(R.id.editTextPubTime);
        ClsDate = findViewById(R.id.editTextClsDate);
        ClsTime = findViewById(R.id.editTextClsTime);

        Dur = findViewById(R.id.editTextDur);
        Dur.setText(qDuHr);


        OpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(OpDate);
            }
        });
        OpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(OpTime);
            }
        });
        ClsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(ClsDate);
            }
        });
        ClsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(ClsTime);
            }
        });

    }

    private void showDateDialog(TextView Date) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                CharSequence date = DateFormat.format("yyyy-MM-dd",calendar);
                Date.setText(date);
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showTimeDialog(TextView Time) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                CharSequence time = DateFormat.format("HH:mm",calendar);
                Time.setText(time);
            }
        },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();
    }





    public void Back(View view){
        finish();
    }

    public void Cancel(View view){
        finish();
    }

    public void Schedule(View view){
        if(ValidateDateTime(OpDate,OpTime,ClsDate,ClsTime,Dur)){
            String quiz_id = qID;
            String quiz_dur = Dur.getText().toString();
            String pub_date = OpDate.getText().toString()+" "+OpTime.getText().toString()+":00";
            String cls_date = ClsDate.getText().toString()+" "+ClsTime.getText().toString()+":00";
            String type= "Schedule";
            String St = "1";    //1 for Schedule and 0 for cancel

            BackgroundWorkerQuiz backgroundWorkerQuiz = new BackgroundWorkerQuiz(this);
            String result;
            try {
                result = backgroundWorkerQuiz.execute(type, quiz_id, pub_date, cls_date, quiz_dur, St).get();

                if(result.equals("Success")){
                    if(Val.equals("Now")) {
                        Intent LecAddQuizSelectOption = new Intent(this, LecAddQuizSelectOption.class);
                        //LecQuizScheduleOption.putExtra("qID",qID);
                        LecAddQuizSelectOption.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(LecAddQuizSelectOption);
                        finish();
                    }
                    else{
                        Intent LecViewQuizDetails = new Intent(this,LecViewQuizDetails.class);
                        LecViewQuizDetails.putExtra("qID",qID);
                        LecViewQuizDetails.putExtra("qName",quName);
                        LecViewQuizDetails.putExtra("tID",tID);
                        LecViewQuizDetails.putExtra("tName",tName);
                        LecViewQuizDetails.putExtra("cID",cID);
                        LecViewQuizDetails.putExtra("cName",cName);
                        LecViewQuizDetails.putExtra("UserID",User_ID);
                        LecViewQuizDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(LecViewQuizDetails);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private boolean ValidateDateTime(TextView ODate, TextView OTime, TextView CDate, TextView CTime, EditText Du){
        int flag = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Date OD = new Date();
        Date CD = new Date();
        Date OT = new Date();
        Date CT = new Date();
        Date DU = new Date();

        //----Get Today Date & Time----
        Date ValidDate = new Date();
        String VD = dateFormat.format(ValidDate);
        Date ValidTime = new Date();
        String VT = timeFormat.format(ValidDate);
        try {
            ValidDate = dateFormat.parse(VD);
            ValidTime = timeFormat.parse(VT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //----------------------

        //----Convert TextView To Date & Time----
        try {
            //Date
            if(ODate.getText().toString().isEmpty()){
                OD = null;
            }
            else { OD = dateFormat.parse(ODate.getText().toString());
            }
            if(CDate.getText().toString().isEmpty()){
                CD = null;
            }
            else { CD = dateFormat.parse(CDate.getText().toString());
            }
            //Time
            if(OTime.getText().toString().isEmpty()){
                OT = null;
            }
            else { OT = timeFormat.parse(OTime.getText().toString());
            }
            if(CTime.getText().toString().isEmpty()){
                CT = null;
            }
            else { CT = timeFormat.parse(CTime.getText().toString());
            }
            //Duration
            if(Du.getText().toString().isEmpty()){
                DU = null;
            }
            else { DU = timeFormat.parse(Du.getText().toString());
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }

        //------------------------------------

        if((OD!=null) && (OT!=null) && (CD!=null) && (CT!=null) && (DU!=null)){
            flag++;
            if(flag==1 && (ValidDate.before(OD) || ValidDate.equals(OD)) && (ValidDate.before(CD) || ValidDate.equals(CD)) && (OD.before(CD) || OD.equals(CD)) ){
                flag++;
            }
            else { Toast.makeText(this, "Invalid Dates", Toast.LENGTH_LONG).show(); }
        }
        else { Toast.makeText(this, "All the inputs must be filed", Toast.LENGTH_LONG).show(); }


        if(flag==2 && OD.equals(CD)){
            if (OD.equals(ValidDate)){
                Boolean f = ValidTime.before(OT) && ValidTime.before(CT) && OT.before(CT);
                if(f) {
                    flag++;
                }
                else{
                    flag--;
                    Toast.makeText(this, "Invalid Time", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Boolean f = OT.before(CT);
                if(f) {
                    flag++;
                }
                else{
                    flag--;
                    Toast.makeText(this, "Invalid Time", Toast.LENGTH_LONG).show();
                }
            }
        }
        if(flag>1){
            return true;
        }
        else { return false; }
    }
}