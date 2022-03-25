package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfiledetails extends AppCompatActivity {

    String Email,tName,User_ID,user_Type,user_ID,reg_no,first_name,last_name;

    EditText email,fs,ls,rn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profiledetails);

        Intent intent =getIntent();
        Email = intent.getStringExtra("email");
        user_ID = intent.getStringExtra("user_ID");
        user_Type = intent.getStringExtra("user_Type");
        reg_no = intent.getStringExtra("reg_no");
        first_name = intent.getStringExtra("first_name");
        last_name = intent.getStringExtra("last_name");



        fs = findViewById(R.id.editTextfirstname);
        fs.setText(first_name);
        ls = findViewById(R.id.editTexLastname);
        ls.setText(last_name);
        rn = findViewById(R.id.editTextRegname);
        rn.setText(reg_no);

    }

    public void Back(View view){
        finish();
    }

    public void Cancel(View view){
        finish();
    }

    public void Update(View view){

            String type = "Editemail";
            String FS = fs.getText().toString();
            String LN = ls.getText().toString();
            String RN = rn.getText().toString();



            BackgroundWorkerEditEmail backgroundWorkerEditEmail = new BackgroundWorkerEditEmail(this);
            String result;
            try {
                result = backgroundWorkerEditEmail.execute(type,FS,LN,RN,user_ID,user_Type).get();

                if (result.equals("Success")) {
                    Intent UserDeatails = new Intent(this, UserDeatails.class);
                    UserDeatails.putExtra("user_ID", user_ID);
                    UserDeatails.putExtra("user_Type", user_Type);



                    UserDeatails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(UserDeatails);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }


}