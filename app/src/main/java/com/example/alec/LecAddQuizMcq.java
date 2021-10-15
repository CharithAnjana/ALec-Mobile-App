package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LecAddQuizMcq extends AppCompatActivity {

    Spinner sPoint1, sPoint2, sPoint3, sPoint4, sPoint5;
    EditText q, a1, a2, a3, a4, a5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_add_quiz_mcq);
    }

    public void Back(View view){
        finish();
    }

    public void Create(View view){
        if(validateQuestionAns(q, a1)) {
            finish();
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
                Toast.makeText(this, "Invalid Answer", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else {
            Toast.makeText(this, "Invalid Question", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}