package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfiledetails extends AppCompatActivity {

    String Email, user_Type, user_ID, telephone_no, first_name, last_name;

    EditText fs, ls, tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profiledetails);

        Intent intent = getIntent();
        user_ID = intent.getStringExtra("user_ID");
        telephone_no = intent.getStringExtra("telephone_no");
        first_name = intent.getStringExtra("first_name");
        last_name = intent.getStringExtra("last_name");


        fs = findViewById(R.id.editTextfirstname);
        fs.setText(first_name);
        ls = findViewById(R.id.editTexLastname);
        ls.setText(last_name);
        tp = findViewById(R.id.editTextRegname);
        tp.setText(telephone_no);

    }

    public void Back(View view) {
        Intent StuHomeActivity = new Intent(this, StuHomeActivity.class);
        StuHomeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(StuHomeActivity);
    }

    public void Cancel(View view) {
        finish();
    }

    public void Update(View view) {
        String type = "Editemail";
        String FS = fs.getText().toString();
        String LN = ls.getText().toString();
        String TP = tp.getText().toString();


        BackgroundWorkerEditDetails backgroundWorkerEditDetails = new BackgroundWorkerEditDetails(this);
        String result;
        try {
            result = backgroundWorkerEditDetails.execute(type, FS, LN, TP, user_ID).get();

            if (!(result.isEmpty())) {
                Intent UserDeatails = new Intent(this, UserDeatails.class);
                UserDeatails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(UserDeatails);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}