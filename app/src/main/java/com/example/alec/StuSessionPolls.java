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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StuSessionPolls extends AppCompatActivity {

    String sessionPollsSURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionpolls.php";
    String sessionPollsAtSURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionpollsattempted.php";
    ListView unatPollsLV,atPollsLV;

    String courseID,sessionID,sessionName, courseName,userID;

    private static String[] qNo;
    private static String[] qType;
    private static String[] question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_session_polls);

        Intent intent = getIntent();
        courseID = intent.getStringExtra("cID");
        sessionID = intent.getStringExtra("sID");
        sessionName = intent.getStringExtra("sName");
        courseName = intent.getStringExtra("cName");
        userID = intent.getStringExtra("UserID");

        sessionPollsSURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionpolls.php?user_ID="+userID+"&session_ID="+sessionID;
        sessionPollsAtSURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionpollsattempted.php?user_ID="+userID+"&session_ID="+sessionID;

        unatPollsLV = (ListView)findViewById(R.id.unatPolls);
        atPollsLV = (ListView)findViewById(R.id.atPolls);
        fetch_data_into_array1(unatPollsLV);
        fetch_data_into_array2(atPollsLV);
    }

    private void fetch_data_into_array1(View View) {

        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    qNo = new String[jsonArray.length()];
                    qType = new String[jsonArray.length()];
                    question = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        qNo[i] = jsonObject.getString("question_no");
                        qType[i] = jsonObject.getString("question_type");
                        question[i] = jsonObject.getString("question");
                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),qNo,qType,question);
                    unatPollsLV.setAdapter(myAdepter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                    StringBuffer sb = new StringBuffer();
                    String line = null;
                    String result = null;
                    while ((line = bufferedReader.readLine()) != null){
                        sb.append(line+"\n");
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
        dbManager.execute(sessionPollsSURL);
    }

    private void fetch_data_into_array2(View View) {

        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    qNo = new String[jsonArray.length()];
                    qType = new String[jsonArray.length()];
                    question = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        qNo[i] = jsonObject.getString("question_no");
                        qType[i] = jsonObject.getString("question_type");
                        question[i] = jsonObject.getString("question");
                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),qNo,qType,question);
                    atPollsLV.setAdapter(myAdepter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                    StringBuffer sb = new StringBuffer();
                    String line = null;
                    String result = null;
                    while ((line = bufferedReader.readLine()) != null){
                        sb.append(line+"\n");
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
        dbManager.execute(sessionPollsAtSURL);
    }


    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] qNo;
        String[] qType;
        String[] question;

        MyAdepter(Context context, String[] qNo, String[] qType, String[] question) {
            super(context, R.layout.layout_session_polls,R.id.tvQNO,qNo);
            this.context = context;
            this.qNo = qNo;
            this.qType = qType;
            this.question = question;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_session_polls,parent,false);;

            TextView qu = row.findViewById(R.id.tvQ);
            TextView qNO = row.findViewById(R.id.tvQNO);
            TextView qt = row.findViewById(R.id.tvQT);

            qNO.setText(qNo[position]);
            qt.setText(qType[position]);
            qu.setText(question[position]);

            return row;
        }

    }

    public void Back(View view){
        finish();
    }

    public void GoToLiveForum(View view){
        Intent liveForum = new Intent(this,StuSessionLiveForum.class);
        liveForum.putExtra("cID",courseID);
        liveForum.putExtra("sID",sessionID);
        liveForum.putExtra("sName",sessionName);
        liveForum.putExtra("cName",courseName);
        liveForum.putExtra("UserID",userID);
        startActivity(liveForum);
        overridePendingTransition(0, 0);
        finish();
    }

    public void Refresh(View view){
        Toast.makeText(this, "Refreshing..", Toast.LENGTH_SHORT).show();
        Intent polls = new Intent(this,StuSessionPolls.class);
        polls.putExtra("cID",courseID);
        polls.putExtra("sID",sessionID);
        polls.putExtra("sName",sessionName);
        polls.putExtra("cName",courseName);
        polls.putExtra("UserID",userID);
        startActivity(polls);
        overridePendingTransition(0, 0);
        finish();
    }
}