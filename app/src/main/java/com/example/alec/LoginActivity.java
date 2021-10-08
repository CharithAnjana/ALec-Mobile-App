package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText EmailEt,PassEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailEt = (EditText)findViewById(R.id.editTextTextEmailAddress);
        PassEt = (EditText)findViewById(R.id.editTextTextPassword);

    }

    public void OnLogin(View view) throws IOException {
        if(validateEmail(EmailEt) && validatePass(PassEt)) {
            String email = EmailEt.getText().toString();
            String pass = PassEt.getText().toString();
            String type = "Login";
            BackgroundWorkerLogin backgroundWorker = new BackgroundWorkerLogin(this);
            String reg = null;
            try {
                reg = backgroundWorker.execute(type, email, pass).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if(!(reg.equals("Login fail"))){
                if(!(reg.equals("No Access"))) {

                    String[] val = reg.split(",");
                    if (val[2].equals("stu")) {

                        UserClass userClass = new UserClass(val[2], val[1]);
                        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                        sessionManagement.saveSession(userClass);

                        Intent StuHomeActivity = new Intent(LoginActivity.this, StuHomeActivity.class);
                        StuHomeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(StuHomeActivity);
                    }

                    if (val[2].equals("lec")) {

                        UserClass userClass = new UserClass(val[2], val[1]);
                        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                        sessionManagement.saveSession(userClass);

                        Intent LecHomeActivity = new Intent(LoginActivity.this, LecHomeActivity.class);
                        LecHomeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(LecHomeActivity);
                    }
                }
            }
        }

    }

    //to validate email
    private boolean validateEmail(EditText emailtxt){
        String email = emailtxt.getText().toString();
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        else {
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //to validate password
    private boolean validatePass(EditText passtxt){
        String pass = passtxt.getText().toString();
        if(!pass.isEmpty()){
            return true;
        }
        else {
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}