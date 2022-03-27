package com.example.alec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserDeatails extends AppCompatActivity {

    String profileURL = "http://10.0.2.2/ALec/public/api/V1/getuserdetails.php";
    String user_ID, user_Type;

    String User_ID, tID, tName;
    TextView FirstName, LastName, TP, Email, RegNo;

    private static String[] user_id;
    private static String[] email;
    private static String[] telephone_no;
    private static String[] first_name;
    private static String[] last_name;
    private static String[] reg_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_deatails);

        SessionManagement sessionManagement = new SessionManagement(this);
        user_ID = sessionManagement.getSessionId();
        user_Type = sessionManagement.getSessionKey();

        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        Email = findViewById(R.id.Email);
        TP = findViewById(R.id.TP);
        RegNo = findViewById(R.id.RegNo);


        profileURL = "http://10.0.2.2/ALec/public/api/V1/getuserdetails.php?user_ID=" + user_ID + "&user_Type=" + user_Type;
        fetch_data_into_textviews();

    }

    private void fetch_data_into_textviews() {
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    user_id = new String[jsonArray.length()];
                    email = new String[jsonArray.length()];
                    telephone_no = new String[jsonArray.length()];
                    first_name = new String[jsonArray.length()];
                    last_name = new String[jsonArray.length()];
                    reg_no = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        user_id[i] = jsonObject.getString("user_id");
                        email[i] = jsonObject.getString("email");
                        telephone_no[i] = jsonObject.getString("tele");
                        first_name[i] = jsonObject.getString("first_name");
                        last_name[i] = jsonObject.getString("last_name");
                        reg_no[i] = jsonObject.getString("reg_no");
                    }

                    FirstName.setText(first_name[0]);
                    LastName.setText(last_name[0]);
                    RegNo.setText(reg_no[0]);
                    Email.setText(email[0]);
                    User_ID = user_id[0];
                    if(!(telephone_no[0].equals(""))){
                        TP.setText(telephone_no[0]);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                    StringBuffer sb = new StringBuffer();
                    String line = null;
                    String result = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    bufferedInputStream.close();
                    result = sb.toString();

                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        dbManager dbManager = new dbManager();
        dbManager.execute(profileURL);

    }


    public void EditProfile(View view) {
        Intent EditProfiledetails = new Intent(this, EditProfiledetails.class);
        EditProfiledetails.putExtra("user_ID", User_ID);
        EditProfiledetails.putExtra("telephone_no", telephone_no[0]);
        EditProfiledetails.putExtra("first_name", first_name[0]);
        EditProfiledetails.putExtra("last_name", last_name[0]);
        startActivity(EditProfiledetails);
    }


    public void Back(View view) {
        finish();
    }
}