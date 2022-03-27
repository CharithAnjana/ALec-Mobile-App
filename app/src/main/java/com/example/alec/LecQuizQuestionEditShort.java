package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LecQuizQuestionEditShort extends AppCompatActivity {

    Spinner sPoint;
    String quizID,quizName,qtID, question, courseName,qDuHr,chName1,chPoint1,back;
    EditText quest, ans;
    TextView quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_quiz_question_edit_short);

        Intent intent = getIntent();
        qtID = intent.getStringExtra("qtID");
        question = intent.getStringExtra("question");
        quizName = intent.getStringExtra("quizName");
        quizID = intent.getStringExtra("quizID");
        courseName = intent.getStringExtra("courseName");
        qDuHr = intent.getStringExtra("qDuHr");
        chName1 = intent.getStringExtra("chName1");
        chPoint1 = intent.getStringExtra("chPoint1");
        back = intent.getStringExtra("Back");

        quiz = findViewById(R.id.QuizName);
        quest = findViewById(R.id.editTextTextMultiLine);
        ans = findViewById(R.id.editTextAnswer);
        quiz.setText(quizName);
        quest.setText(question);
        ans.setText(chName1);

        sPoint = findViewById(R.id.spinnerPoints);
        ArrayAdapter<CharSequence> pointadapter = ArrayAdapter.createFromResource(this,
                R.array.answer_point_short, android.R.layout.simple_spinner_item);
        pointadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPoint.setAdapter(pointadapter);
        if (chPoint1 != null) {
            int spinnerPosition = pointadapter.getPosition(chPoint1);
            sPoint.setSelection(spinnerPosition);
        }
    }

    public void Back(View view){
        finish();
    }

    public void Cancel(View view){
       finish();
    }

    public void Save(View view){
        if(validateQuestionAns(quest, ans)) {
            String question = quest.getText().toString();
            String answer = ans.getText().toString();
            String point = "100";
            String qutype = "short ans";
            String type = "EditQuestShort";

            BackgroundWorkerQuizQuestion backgroundWorkerQuizQuestion = new BackgroundWorkerQuizQuestion(this);
            String result;
            try {
                result = backgroundWorkerQuizQuestion.execute(type, qtID, question, qutype, answer, point).get();

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
    private boolean validateQuestionAns(EditText que, EditText ans){
        String ques = que.getText().toString();
        String answ = ans.getText().toString();
        if(!ques.isEmpty()){
            if(!answ.isEmpty()){
                return true;
            }
            else {
                Toast.makeText(this, "Invalid Answer", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else {
            Toast.makeText(this, "Invalid Question", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void DeleteShort(View view){
        Intent LecQuizQuestionDeletePop = new Intent(this, LecQuizQuestionDeletePop.class);
        LecQuizQuestionDeletePop.putExtra("qtID",qtID);
        LecQuizQuestionDeletePop.putExtra("quizID",quizID);
        LecQuizQuestionDeletePop.putExtra("courseName",courseName);
        LecQuizQuestionDeletePop.putExtra("quizName",quizName);
        LecQuizQuestionDeletePop.putExtra("qDuHr",qDuHr);
        startActivity(LecQuizQuestionDeletePop);
        //finish();
    }
}