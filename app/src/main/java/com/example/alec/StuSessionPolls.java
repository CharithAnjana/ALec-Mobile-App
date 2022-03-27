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
import android.widget.AdapterView;
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

    private static String[] qNo1;
    private static String[] qType1;
    private static String[] question1;
    private static String[] qPubTime1;
    private static String[] qDur1;

    private static String[] qNo2;
    private static String[] qType2;
    private static String[] question2;
    private static String[] ans2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_session_polls);

        Intent intent = getIntent();
        //courseID = intent.getStringExtra("cID");
        sessionID = intent.getStringExtra("sID");
        //sessionName = intent.getStringExtra("sName");
        //courseName = intent.getStringExtra("cName");
        userID = intent.getStringExtra("UserID");

        sessionPollsSURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionpolls.php?user_ID="+userID+"&session_ID="+sessionID;
        sessionPollsAtSURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionpollsattempted.php?user_ID="+userID+"&session_ID="+sessionID;

        unatPollsLV = (ListView)findViewById(R.id.unatPolls);
        atPollsLV = (ListView)findViewById(R.id.atPolls);
        fetch_data_into_array1(unatPollsLV);
        fetch_data_into_array2(atPollsLV);

        unatPollsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(qType1[i].equals("mcq-tf")|| qType1[i].equals("mcq")){
                    Intent StuSessionPollsMCQ = new Intent(getApplicationContext(), StuSessionPollsAttemptMCQ.class);
                    StuSessionPollsMCQ.putExtra("qNo",qNo1[i]);
                    StuSessionPollsMCQ.putExtra("question",question1[i]);
                    StuSessionPollsMCQ.putExtra("qPubTime",qPubTime1[i]);
                    StuSessionPollsMCQ.putExtra("qDur",qDur1[i]);
                    StuSessionPollsMCQ.putExtra("userID",userID);
                    StuSessionPollsMCQ.putExtra("sID",sessionID);
                    startActivity(StuSessionPollsMCQ);
                    overridePendingTransition(0, 0);
                }
                if(qType1[i].equals("open")){
                    Intent StuSessionPollsAttemptShort = new Intent(getApplicationContext(), StuSessionPollsAttemptShort.class);
                    StuSessionPollsAttemptShort.putExtra("qNo",qNo1[i]);
                    StuSessionPollsAttemptShort.putExtra("question",question1[i]);
                    StuSessionPollsAttemptShort.putExtra("qPubTime",qPubTime1[i]);
                    StuSessionPollsAttemptShort.putExtra("qDur",qDur1[i]);
                    StuSessionPollsAttemptShort.putExtra("userID",userID);
                    StuSessionPollsAttemptShort.putExtra("sID",sessionID);
                    startActivity(StuSessionPollsAttemptShort);
                    overridePendingTransition(0, 0);
                }
            }
        });

        atPollsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(qType2[i].equals("mcq-tf")|| qType2[i].equals("mcq")){
                    Intent StuViewAttemPollsQuesMcq = new Intent(getApplicationContext(), StuViewAttemPollsQuesMcq.class);
                    StuViewAttemPollsQuesMcq.putExtra("qNo",qNo2[i]);
                    StuViewAttemPollsQuesMcq.putExtra("question",question2[i]);
                    StuViewAttemPollsQuesMcq.putExtra("qAns",ans2[i]);
                    startActivity(StuViewAttemPollsQuesMcq);
                    overridePendingTransition(0, 0);
                }
                if(qType2[i].equals("open")){
                    Intent StuViewAttemPollsQuesOpen = new Intent(getApplicationContext(), StuViewAttemPollsQuesOpen.class);
                    StuViewAttemPollsQuesOpen.putExtra("qNo",qNo2[i]);
                    StuViewAttemPollsQuesOpen.putExtra("question",question2[i]);
                    StuViewAttemPollsQuesOpen.putExtra("qAns",ans2[i]);
                    startActivity(StuViewAttemPollsQuesOpen);
                    overridePendingTransition(0, 0);
                }
            }
        });
    }

    private void fetch_data_into_array1(View View) {

        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    qNo1 = new String[jsonArray.length()];
                    qType1 = new String[jsonArray.length()];
                    question1 = new String[jsonArray.length()];
                    qPubTime1 = new String[jsonArray.length()];
                    qDur1 = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        qNo1[i] = jsonObject.getString("question_no");
                        qType1[i] = jsonObject.getString("question_type");
                        question1[i] = jsonObject.getString("question");
                        qPubTime1[i] = jsonObject.getString("published_time");
                        qDur1[i] = jsonObject.getString("duration");
                    }

                    MyAdepter1 myAdepter = new MyAdepter1(getApplicationContext(),qNo1,qType1,question1
                    ,qPubTime1,qDur1);
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
                    qNo2 = new String[jsonArray.length()];
                    qType2 = new String[jsonArray.length()];
                    question2 = new String[jsonArray.length()];
                    ans2 = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        qNo2[i] = jsonObject.getString("question_no");
                        qType2[i] = jsonObject.getString("question_type");
                        question2[i] = jsonObject.getString("question");
                        ans2[i] = jsonObject.getString("answer");
                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),qNo2,qType2,question2,ans2);
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

    class MyAdepter1 extends ArrayAdapter<String> {

        Context context;
        String[] qNo;
        String[] qType;
        String[] question;
        String[] qPubTime;
        String[] qDur;

        MyAdepter1(Context context, String[] qNo, String[] qType, String[] question, String[] qPubTime
                , String[] qDur) {
            super(context, R.layout.layout_session_polls,R.id.tvQNO,qNo);
            this.context = context;
            this.qNo = qNo;
            this.qType = qType;
            this.question = question;
            this.qPubTime = qPubTime;
            this.qDur = qDur;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_session_polls,parent,false);;

            TextView qu = row.findViewById(R.id.tvQ);
            TextView qNO = row.findViewById(R.id.tvQNO);
            TextView qt = row.findViewById(R.id.tvQT);
            TextView qPT = row.findViewById(R.id.tvQPT);
            TextView qD = row.findViewById(R.id.tvQDU);

            qNO.setText(qNo[position]);
            qt.setText(qType[position]);
            qu.setText(question[position]);
            qPT.setText(qPubTime[position]);
            qD.setText(qDur[position]);

            return row;
        }

    }

    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] qNo;
        String[] qType;
        String[] question;
        String[] ans;

        MyAdepter(Context context, String[] qNo, String[] qType, String[] question, String[] ans) {
            super(context, R.layout.layout_session_polls,R.id.tvQNO,qNo);
            this.context = context;
            this.qNo = qNo;
            this.qType = qType;
            this.question = question;
            this.ans = ans;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_session_polls,parent,false);;

            TextView qu = row.findViewById(R.id.tvQ);
            TextView qNO = row.findViewById(R.id.tvQNO);
            TextView qt = row.findViewById(R.id.tvQT);
            TextView an = row.findViewById(R.id.tvQPT);

            qNO.setText(qNo[position]);
            qt.setText(qType[position]);
            qu.setText(question[position]);
            an.setText(ans[position]);

            return row;
        }
    }

    public void Back(View view){
        finish();
    }

    public void GoToLiveForum(View view){
        Intent liveForum = new Intent(this,StuSessionLiveForum.class);
        //liveForum.putExtra("cID",courseID);
        liveForum.putExtra("sID",sessionID);
        //liveForum.putExtra("sName",sessionName);
        //liveForum.putExtra("cName",courseName);
        liveForum.putExtra("UserID",userID);
        startActivity(liveForum);
        overridePendingTransition(0, 0);
        finish();
    }

    public void Refresh(View view){
        Toast.makeText(this, "Refreshing..", Toast.LENGTH_SHORT).show();
        Intent polls = new Intent(this,StuSessionPolls.class);
        //polls.putExtra("cID",courseID);
        polls.putExtra("sID",sessionID);
        //polls.putExtra("sName",sessionName);
        //polls.putExtra("cName",courseName);
        polls.putExtra("UserID",userID);
        startActivity(polls);
        overridePendingTransition(0, 0);
        finish();
    }
}