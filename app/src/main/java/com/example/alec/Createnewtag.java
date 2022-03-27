package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Createnewtag extends AppCompatActivity {

    String Student_id,tag_name,tag_id,student_id, cName, fID, uID;
    TextView course;
    EditText tagname ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnewtag);

        tagname = findViewById(R.id.editTextSubName);

        Intent intent = getIntent();
        Student_id = intent.getStringExtra("Student_id"); }

    public void Cancel(View view){
        finish();
    }

    public void Back(View view){
        finish();
    }

    public void Create(View view){
        if(validatetag(tagname)){
            String TagName = tagname.getText().toString();
            String type = "NewTag";

            BackgroundWorkertag backgroundworkertag = new BackgroundWorkertag(this);
            String result;
            try {
                result = backgroundworkertag.execute(type, Student_id,TagName).get();

                if(result.equals("Success")){

                    Intent TaggedName = new Intent(this,TaggedName.class);
                    TaggedName.putExtra("Student_id",Student_id);

                    TaggedName.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(TaggedName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validatetag(EditText tagname){
        String Tagname = tagname.getText().toString();

        if(!Tagname.isEmpty()){
            return true;
            }
            else {
                Toast.makeText(this, "Invalid Question", Toast.LENGTH_LONG).show();
                return false;
            }


    }
}