package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LecAddQuizShort extends AppCompatActivity {

    Spinner pointsSpinner;
    EditText q, a;
    String quizID, quizName, course, back, qDuHr;
    TextView quname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_add_quiz_short);

        Intent intent = getIntent();
        quizID = intent.getStringExtra("quizID");
        course = intent.getStringExtra("cName");
        quizName = intent.getStringExtra("quizName");
        back = intent.getStringExtra("Back");
        qDuHr = intent.getStringExtra("qDuHr");

        q = findViewById(R.id.editTextTextMultiLine);
        a = findViewById(R.id.editTextAnswer);
        quname = findViewById(R.id.QuizName);
        quname.setText(quizName);

        pointsSpinner = findViewById(R.id.spinnerPoints);
        ArrayAdapter<CharSequence> pointadapter = ArrayAdapter.createFromResource(this,
                R.array.answer_point_short, android.R.layout.simple_spinner_item);
        pointadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pointsSpinner.setAdapter(pointadapter);
    }

    public void Back(View view){
        finish();
    }

    public void Create(View view){
        if(validateQuestionAns(q, a)) {
            String question = q.getText().toString();
            String answer = a.getText().toString();
            String point = "100";
            String qutype = "short ans";
            String type = "NewQuestShort";

            BackgroundWorkerQuizQuestion backgroundWorkerQuizQuestion = new BackgroundWorkerQuizQuestion(this);
            String result;
            try {
                result = backgroundWorkerQuizQuestion.execute(type, quizID, question, qutype, answer, point).get();

                if(result.equals("Success")){

                        if(back.equals("Add")){
                            Intent LecAddQuizQuestion = new Intent(this,LecAddQuizQuestion.class);
                            LecAddQuizQuestion.putExtra("qID",quizID);
                            LecAddQuizQuestion.putExtra("cName",course);
                            LecAddQuizQuestion.putExtra("qName",quizName);
                            LecAddQuizQuestion.putExtra("qDuration",qDuHr);
                            LecAddQuizQuestion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(LecAddQuizQuestion);
                            finish();
                        }else {
                            Intent LecAddQuizQuestionEdit = new Intent(this,LecAddQuizQuestionEdit.class);
                            LecAddQuizQuestionEdit.putExtra("qID",quizID);
                            LecAddQuizQuestionEdit.putExtra("cName",course);
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

    public void Cancel(View view){
        finish();
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
}