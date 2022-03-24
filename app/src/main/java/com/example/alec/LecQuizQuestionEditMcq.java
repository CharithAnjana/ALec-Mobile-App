package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LecQuizQuestionEditMcq extends AppCompatActivity {

    Spinner sPoint1, sPoint2, sPoint3, sPoint4, sPoint5;
    String quizID,quizName,qtID, question, courseName,qDuHr,chName1,chPoint1,chName2,chPoint2
            ,chName3,chPoint3,chName4,chPoint4,chName5,chPoint5,back;
    TextView quiz;
    EditText ques, ans1, ans2, ans3, ans4, ans5;
    CheckBox checkMulAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_quiz_question_edit_mcq);

        Intent intent = getIntent();
        qtID = intent.getStringExtra("qtID");
        question = intent.getStringExtra("question");
        quizName = intent.getStringExtra("quizName");
        quizID = intent.getStringExtra("quizID");
        courseName = intent.getStringExtra("courseName");
        qDuHr = intent.getStringExtra("qDuHr");
        chName1 = intent.getStringExtra("chName1");
        chPoint1 = intent.getStringExtra("chPoint1");
        chName2 = intent.getStringExtra("chName2");
        chPoint2 = intent.getStringExtra("chPoint2");
        chName3 = intent.getStringExtra("chName3");
        chPoint3 = intent.getStringExtra("chPoint3");
        chName4 = intent.getStringExtra("chName4");
        chPoint4 = intent.getStringExtra("chPoint4");
        chName5 = intent.getStringExtra("chName5");
        chPoint5 = intent.getStringExtra("chPoint5");
        back = intent.getStringExtra("Back");

        quiz = findViewById(R.id.QuizName);
        ques = findViewById(R.id.editTextTextMultiLine);
        ans1 = findViewById(R.id.editTextAnswer1);
        ans2 = findViewById(R.id.editTextAnswer2);
        ans3 = findViewById(R.id.editTextAnswer3);
        ans4 = findViewById(R.id.editTextAnswer4);
        ans5 = findViewById(R.id.editTextAnswer5);
        quiz.setText(quizName);
        ques.setText(question);
        ans1.setText(chName1);
        ans2.setText(chName2);
        ans3.setText(chName3);
        ans4.setText(chName4);
        ans5.setText(chName5);
        checkMulAns = findViewById(R.id.mulAns);

        sPoint1 = findViewById(R.id.spinnerPoints1);
        ArrayAdapter<CharSequence> pointadapter1 = ArrayAdapter.createFromResource(this,
                R.array.answer_point_mcq, android.R.layout.simple_spinner_item);
        pointadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPoint1.setAdapter(pointadapter1);
        if (chPoint1 != null) {
            int spinnerPosition = pointadapter1.getPosition(chPoint1);
            sPoint1.setSelection(spinnerPosition);
        }

        sPoint2 = findViewById(R.id.spinnerPoints2);
        ArrayAdapter<CharSequence> pointadapter2 = ArrayAdapter.createFromResource(this,
                R.array.answer_point_mcq, android.R.layout.simple_spinner_item);
        pointadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPoint2.setAdapter(pointadapter2);
        if (chPoint2 != null) {
            int spinnerPosition = pointadapter2.getPosition(chPoint2);
            sPoint2.setSelection(spinnerPosition);
        }

        sPoint3 = findViewById(R.id.spinnerPoints3);
        ArrayAdapter<CharSequence> pointadapter3 = ArrayAdapter.createFromResource(this,
                R.array.answer_point_mcq, android.R.layout.simple_spinner_item);
        pointadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPoint3.setAdapter(pointadapter3);
        if (chPoint3 != null) {
            int spinnerPosition = pointadapter3.getPosition(chPoint3);
            sPoint3.setSelection(spinnerPosition);
        }

        sPoint4 = findViewById(R.id.spinnerPoints4);
        ArrayAdapter<CharSequence> pointadapter4 = ArrayAdapter.createFromResource(this,
                R.array.answer_point_mcq, android.R.layout.simple_spinner_item);
        pointadapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPoint4.setAdapter(pointadapter4);
        if (chPoint4 != null) {
            int spinnerPosition = pointadapter4.getPosition(chPoint4);
            sPoint4.setSelection(spinnerPosition);
        }

        sPoint5 = findViewById(R.id.spinnerPoints5);
        ArrayAdapter<CharSequence> pointadapter5 = ArrayAdapter.createFromResource(this,
                R.array.answer_point_mcq, android.R.layout.simple_spinner_item);
        pointadapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPoint5.setAdapter(pointadapter5);
        if (chPoint5 != null) {
            int spinnerPosition = pointadapter5.getPosition(chPoint5);
            sPoint5.setSelection(spinnerPosition);
        }

        //To check Multiple Ans
        String type = "MCQMul";
        BackgroundWorkerCheckPoints BgWCheckPoints = new BackgroundWorkerCheckPoints(this);
        String result;
        try {
            result = BgWCheckPoints.execute(type, qtID).get();

            if(result.equals("mcq-m")){
                checkMulAns.setChecked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Back(View view){
        finish();
    }

    public void Cancel(View view){
        finish();
    }

    public void Save(View view){
        if(validateQuestionAns(ques, ans1, ans2)) {
            String ans_count = "2";
            String an1, an2, an3, an4="", an5="";

            String question = ques.getText().toString();
            an1 = ans1.getText().toString();
            an2 = ans2.getText().toString();
            an3 = ans3.getText().toString();
            if(!an3.isEmpty()){
                ans_count = "3";
                an4 = ans4.getText().toString();
                if(!an4.isEmpty()){
                    ans_count = "4";
                    an5 = ans5.getText().toString();
                    if(!an5.isEmpty()){
                        ans_count = "5";
                    }
                }
            }

            String pnt1 = sPoint1.getSelectedItem().toString();
            String pnt2 = sPoint2.getSelectedItem().toString();
            String pnt3 = sPoint3.getSelectedItem().toString();
            String pnt4 = sPoint4.getSelectedItem().toString();
            String pnt5 = sPoint5.getSelectedItem().toString();

            String qutype = "";
            if(checkMulAns.isChecked()){
                qutype = "mcq-m";
            }else {
                qutype = "mcq-s";
            }

            String type = "EditQuestMcq";

            BackgroundWorkerQuizQuestion backgroundWorkerQuizQuestion = new BackgroundWorkerQuizQuestion(this);
            String result;
            try {
                result = backgroundWorkerQuizQuestion.execute(type,quizID, qtID, question, qutype,
                        an1, an2, an3, an4, an5, pnt1, pnt2, pnt3, pnt4, pnt5, ans_count).get();

                if(result.equals("Success")){

                    if(back.equals("Add")){
                        Intent LecAddQuizQuestion = new Intent(this,LecAddQuizQuestion.class);
                        LecAddQuizQuestion.putExtra("qID",quizID);
                        LecAddQuizQuestion.putExtra("cName",courseName);
                        LecAddQuizQuestion.putExtra("qName",quizName);
                        LecAddQuizQuestion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(LecAddQuizQuestion);
                        finish();
                    }else {
                        Intent LecAddQuizQuestionEdit = new Intent(this,LecAddQuizQuestionEdit.class);
                        LecAddQuizQuestionEdit.putExtra("qID",quizID);
                        LecAddQuizQuestionEdit.putExtra("cName",courseName);
                        LecAddQuizQuestionEdit.putExtra("qName",quizName);
                        LecAddQuizQuestionEdit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(LecAddQuizQuestionEdit);
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void DeleteMCQ(View view){
        Intent LecQuizQuestionDeletePop = new Intent(this, LecQuizQuestionDeletePop.class);
        LecQuizQuestionDeletePop.putExtra("qtID",qtID);
        LecQuizQuestionDeletePop.putExtra("quizID",quizID);
        LecQuizQuestionDeletePop.putExtra("courseName",courseName);
        LecQuizQuestionDeletePop.putExtra("quizName",quizName);
        LecQuizQuestionDeletePop.putExtra("qDuHr",qDuHr);
        startActivity(LecQuizQuestionDeletePop);
        //finish();
    }


    private boolean validateQuestionAns(EditText que, EditText ans1, EditText ans2){
        String ques = que.getText().toString();
        String an1 = ans1.getText().toString();
        String an2 = ans2.getText().toString();
        if(!ques.isEmpty()){
            if( (!an1.isEmpty()) && (!an2.isEmpty()) ){
                return true;
            }
            else {
                Toast.makeText(this, "Invalid Answer Count", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else {
            Toast.makeText(this, "Invalid Question", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}