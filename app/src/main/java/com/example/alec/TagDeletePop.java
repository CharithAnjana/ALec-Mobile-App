package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class TagDeletePop extends AppCompatActivity {

    String student_id,tag_name,tag_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_delete_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int hight = dm.heightPixels;

        getWindow().setLayout((int)(width *.7),(int)(hight * .5));

        Intent intent =getIntent();
        student_id = intent.getStringExtra("student_id");
        tag_name = intent.getStringExtra("tag_name");
        tag_id = intent.getStringExtra("tag_id");


    }
    public void DeleteConfirm(View view) {

        String Tag_id = tag_id;
        String type = "Deletetag";

        BackgroundWorkertag backgroundWorkertag = new BackgroundWorkertag(this);
        String result;
        try {
            result = backgroundWorkertag.execute(type, Tag_id).get();

            if(result.equals("Success")){
                Intent LecQuizList = new Intent(this, TaggedName.class);
                LecQuizList.putExtra("Student_id", student_id);

                LecQuizList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LecQuizList);
                finish();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void Cancel(View view){
        finish();
    }
}