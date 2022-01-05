package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class InitPasswordChange extends AppCompatActivity {

    String User_ID;
    EditText Pass1,Pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_password_change);

        Intent intent = getIntent();
        User_ID = intent.getStringExtra("User_ID");

        Pass1 = (EditText)findViewById(R.id.editTextTextPassword1);
        Pass2 = (EditText)findViewById(R.id.editTextTextPassword2);
    }

    public void Back(View view){
        Intent login = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(login);
        finish();
    }

    public void ChangePass(View view){
        if(validatePass(Pass1) && validatePass(Pass2)){
            if(validatePassTogether(Pass1,Pass2)){
                String Pass = Pass1.getText().toString();
                String type = "InitChangePass";
                BackgroundWorkerLogin backgroundWorker = new BackgroundWorkerLogin(this);
                String reg = null;
                try {
                    reg = backgroundWorker.execute(type, User_ID, Pass).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(reg.equals("Success")){
                    Intent LoginActivity = new Intent(this, LoginActivity.class);
                    LoginActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(LoginActivity);
                }
            }
        }
    }

    private boolean validatePass(EditText passtxt){
        String pass = passtxt.getText().toString();
        if(!pass.isEmpty()){
            return true;
        }
        else {
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean validatePassTogether(EditText pass1, EditText pass2){
        String Pass1 = pass1.getText().toString();
        String Pass2 = pass2.getText().toString();
        if(Pass1.equals(Pass2)){
            return true;
        }
        else {
            Toast.makeText(this, "Password Mismatched", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}