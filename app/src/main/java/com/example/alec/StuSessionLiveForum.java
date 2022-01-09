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

public class StuSessionLiveForum extends AppCompatActivity {

    String sessionlForumSURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionliveforum.php";
    String courseID,sessionID,sessionName, courseName,userID;
    ListView liveForumLV;

    private static String[] qNo;
    private static String[] stId;
    private static String[] question;
    private static String[] points;
    private static String[] postTime;
    private static String[] rst;
    private static String[] stName;
    private static String[] voteSt;
    private static String[] voteString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_session_live_forum);

        Intent intent = getIntent();
        courseID = intent.getStringExtra("cID");
        sessionID = intent.getStringExtra("sID");
        sessionName = intent.getStringExtra("sName");
        courseName = intent.getStringExtra("cName");
        userID = intent.getStringExtra("UserID");

        sessionlForumSURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionliveforum.php?user_ID="+userID+"&session_ID="+sessionID;

        liveForumLV = (ListView)findViewById(R.id.liveForum);
        fetch_data_into_array(liveForumLV);

        //liveForumLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          //  @Override
            //public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              //  Intent StuSessionLiveForumVote = new Intent(getApplicationContext(), StuSessionLiveForumVote.class);
                //StuSessionLiveForumVote.putExtra("cID",qNo[i]);
                //StuSessionLiveForumVote.putExtra("sID",question[i]);
                //StuSessionLiveForumVote.putExtra("sName",points[i]);
                //StuSessionLiveForumVote.putExtra("cName",postTime[i]);
                //StuSessionLiveForumVote.putExtra("UserID",userID);
                //startActivity(StuSessionLiveForumVote);
            //}
        //});
    }

    private void fetch_data_into_array(View View) {

        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    qNo = new String[jsonArray.length()];
                    stId = new String[jsonArray.length()];
                    question = new String[jsonArray.length()];
                    points = new String[jsonArray.length()];
                    postTime = new String[jsonArray.length()];
                    rst = new String[jsonArray.length()];
                    stName = new String[jsonArray.length()];
                    voteSt = new String[jsonArray.length()];
                    voteString = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        qNo[i] = jsonObject.getString("question_id");
                        stId[i] = jsonObject.getString("student_id");
                        question[i] = jsonObject.getString("question");
                        points[i] = jsonObject.getString("points");
                        postTime[i] = jsonObject.getString("post_time");
                        rst[i] = jsonObject.getString("random_status");
                        if(rst[i].equals("T")){
                            stName[i] = jsonObject.getString("random_name");
                        }
                        else{
                            stName[i] = jsonObject.getString("name");
                        }
                        voteSt[i] = jsonObject.getString("vote_status");
                        if(!(voteSt[i].equals("null"))){
                            voteString[i] = "\uD83D\uDC4D";
                        }
                        else{
                            voteString[i] = "\uD83D\uDC4D\uD83C\uDFFE";
                        }
                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),qNo,stId,question,points,
                            postTime,stName,voteString);
                    liveForumLV.setAdapter(myAdepter);

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
        dbManager.execute(sessionlForumSURL);
    }

    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] qNo;
        String[] qType;
        String[] question;
        String[] vtString;
        String[] points;
        String[] postTime;
        String[] stName;

        MyAdepter(Context context, String[] qNo, String[] qType, String[] question, String[] points,
                  String[] postTime, String[] stName,String[] voteString) {
            super(context, R.layout.layout_session_liveforum,R.id.tvQNO,qNo);
            this.context = context;
            this.qNo = qNo;
            this.qType = qType;
            this.question = question;
            this.vtString = voteString;
            this.points = points;
            this.postTime = postTime;
            this.stName = stName;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_session_liveforum,parent,false);;

            TextView qu = row.findViewById(R.id.tvQ);
            TextView qNO = row.findViewById(R.id.tvQNO);
            TextView qt = row.findViewById(R.id.tvQT);
            TextView vts = row.findViewById(R.id.vt);
            TextView pts = row.findViewById(R.id.points);
            TextView pstTime = row.findViewById(R.id.tvDT);
            TextView stNme = row.findViewById(R.id.userName);

            vts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = "ManageVotes";
                    BackgroundWorkerLiveForumVotes BgWCheckPoints = new BackgroundWorkerLiveForumVotes(context);
                    String result;
                    try {
                        result = BgWCheckPoints.execute(type,userID,qNo[position]).get();
                        if(result.equals("Success")){
                            Intent liveForum = new Intent(context, StuSessionLiveForum.class);
                            liveForum.putExtra("cID", courseID);
                            liveForum.putExtra("sID", sessionID);
                            liveForum.putExtra("sName", sessionName);
                            liveForum.putExtra("cName", courseName);
                            liveForum.putExtra("UserID", userID);
                            startActivity(liveForum);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            qNO.setText(qNo[position]);
            qt.setText(qType[position]);
            qu.setText(question[position]);
            pts.setText(points[position]);
            pstTime.setText(postTime[position]);
            stNme.setText(stName[position]);
            vts.setText(voteString[position]);

            return row;
        }

    }
    public void Vote(View view){
        finish();
    }



    public void Back(View view){
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

    public void GoToPolls(View view){
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

    public void NewQuestion(View view){
        Intent in = new Intent(this,StuSessionLiveForumAddQuestion.class);
        in.putExtra("cID",courseID);
        in.putExtra("sID",sessionID);
        in.putExtra("sName",sessionName);
        in.putExtra("cName",courseName);
        in.putExtra("UserID",userID);
        startActivity(in);
        overridePendingTransition(0, 0);
        finish();
    }

    public void Refresh(View view){
        Toast.makeText(this, "Refreshing..", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed()
    {
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