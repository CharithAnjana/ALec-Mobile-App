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

public class LecAddQuizMcq extends AppCompatActivity {

    Spinner sPoint1, sPoint2, sPoint3, sPoint4, sPoint5;
    EditText q, a1, a2, a3, a4, a5;
    CheckBox checkMulAns;
    String quizID, quizName, course;
    TextView quname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_add_quiz_mcq);

        Intent intent = getIntent();
        quizID = intent.getStringExtra("quizID");
        course = intent.getStringExtra("cName");
        quizName = intent.getStringExtra("quizName");

        quname = findViewById(R.id.QuizName);
        quname.setText(quizName);

        q = findViewById(R.id.editTextTextMultiLine);
        checkMulAns = findViewById(R.id.mulAns);
        a1 = findViewById(R.id.editTextAnswer1);
        a2 = findViewById(R.id.editTextAnswer2);
        a3 = findViewById(R.id.editTextAnswer3);
        a4 = findViewById(R.id.editTextAnswer4);
        a5 = findViewById(R.id.editTextAnswer5);

        sPoint1 = findViewById(R.id.spinnerPoints1);
        ArrayAdapter<CharSequence> pointadapter1 = ArrayAdapter.createFromResource(this,
                R.array.answer_point_mcq, android.R.layout.simple_spinner_item);
        pointadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPoint1.setAdapter(pointadapter1);

        sPoint2 = findViewById(R.id.spinnerPoints2);
        ArrayAdapter<CharSequence> pointadapter2 = ArrayAdapter.createFromResource(this,
                R.array.answer_point_mcq, android.R.layout.simple_spinner_item);
        pointadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPoint2.setAdapter(pointadapter2);

        sPoint3 = findViewById(R.id.spinnerPoints3);
        ArrayAdapter<CharSequence> pointadapter3 = ArrayAdapter.createFromResource(this,
                R.array.answer_point_mcq, android.R.layout.simple_spinner_item);
        pointadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPoint3.setAdapter(pointadapter3);

        sPoint4 = findViewById(R.id.spinnerPoints4);
        ArrayAdapter<CharSequence> pointadapter4 = ArrayAdapter.createFromResource(this,
                R.array.answer_point_mcq, android.R.layout.simple_spinner_item);
        pointadapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPoint4.setAdapter(pointadapter4);

        sPoint5 = findViewById(R.id.spinnerPoints5);
        ArrayAdapter<CharSequence> pointadapter5 = ArrayAdapter.createFromResource(this,
                R.array.answer_point_mcq, android.R.layout.simple_spinner_item);
        pointadapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPoint5.setAdapter(pointadapter5);


    }

    public void Back(View view){
        finish();
    }

    public void Create(View view){
        if(validateQuestionAns(q, a1, a2)) {
            String ans_count = "2";
            String ans1, ans2, ans3, ans4="", ans5="";

            String question = q.getText().toString();
            ans1 = a1.getText().toString();
            ans2 = a2.getText().toString();
            ans3 = a3.getText().toString();
            if(!ans3.isEmpty()){
                ans_count = "3";
                ans4 = a4.getText().toString();
                if(!ans4.isEmpty()){
                    ans_count = "4";
                    ans5 = a5.getText().toString();
                    if(!ans4.isEmpty()){
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

            String type = "NewQuestMcq";

            BackgroundWorkerQuizQuestion backgroundWorkerQuizQuestion = new BackgroundWorkerQuizQuestion(this);
            String result;
            try {
                result = backgroundWorkerQuizQuestion.execute(type, quizID, question, qutype,
                        ans1, ans2, ans3, ans4, ans5, pnt1, pnt2, pnt3, pnt4, pnt5, ans_count).get();

                if(result.equals("Success")){

                    Intent LecAddQuizQuestion = new Intent(this,LecAddQuizQuestion.class);
                    LecAddQuizQuestion.putExtra("qID",quizID);
                    LecAddQuizQuestion.putExtra("cName",course);
                    LecAddQuizQuestion.putExtra("qName",quizName);
                    LecAddQuizQuestion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(LecAddQuizQuestion);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Cancel(View view){
        finish();
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