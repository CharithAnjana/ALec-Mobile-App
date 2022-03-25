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

public class LecSessionViewLiveForum extends AppCompatActivity {

    String sID,sName,cID,cName,userID;
    String sessionlForumSURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionliveforumlec.php";
    ListView liveForumLV;

    private static String[] qNo;
    private static String[] stId;
    private static String[] question;
    private static String[] points;
    private static String[] postTime;
    private static String[] rst;
    private static String[] stName;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_session_view_live_forum);

        Intent intent = getIntent();
        sID = intent.getStringExtra("sID");
        sName = intent.getStringExtra("sName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");

        String r = "1";
        sessionlForumSURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionliveforumlec.php?session_ID="+sID+"&f="+r;

        liveForumLV = (ListView)findViewById(R.id.liveForumlec);
        fetch_data_into_array(liveForumLV);

    }

    private void fetch_data_into_array(ListView liveForumLV) {
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

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        qNo[i] = jsonObject.getString("question_id");
                        stId[i] = jsonObject.getString("student_id");
                        question[i] = jsonObject.getString("question");
                        points[i] = jsonObject.getString("points");
                        postTime[i] = jsonObject.getString("post_time");
                        rst[i] = jsonObject.getString("resolved_status");
                        stName[i] = jsonObject.getString("name");
                        if(rst[i].equals("0")){
                            rst[i] = "⛔";
                        }
                        else{
                            rst[i] = "✔️";
                        }
                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),qNo,stId,question,points,
                            postTime,stName,rst);
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
        String[] points;
        String[] postTime;
        String[] stName;
        String[] rst;

        MyAdepter(Context context, String[] qNo, String[] qType, String[] question, String[] points,
                  String[] postTime, String[] stName, String[] rst) {
            super(context, R.layout.layout_session_forum_lec, R.id.tvQNO, qNo);
            this.context = context;
            this.qNo = qNo;
            this.qType = qType;
            this.question = question;
            this.points = points;
            this.postTime = postTime;
            this.stName = stName;
            this.rst = rst;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_session_forum_lec, parent, false);
            ;

            TextView qu = row.findViewById(R.id.tvQ);
            TextView qNO = row.findViewById(R.id.tvQNO);
            TextView qt = row.findViewById(R.id.tvQT);
            TextView rsl = row.findViewById(R.id.rs);
            TextView pts = row.findViewById(R.id.points);
            TextView pstTime = row.findViewById(R.id.tvDT);
            TextView stNme = row.findViewById(R.id.userName);

            rsl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = "ManageResolve";
                    BackgroundWorkerLiveForumVotes BgWCheckPoints = new BackgroundWorkerLiveForumVotes(context);
                    String result;
                    try {
                        result = BgWCheckPoints.execute(type, qNo[position]).get();
                        if (result.equals("Success")) {
                            Intent liveForum = new Intent(context, LecSessionViewLiveForum.class);
                            liveForum.putExtra("cID", cID);
                            liveForum.putExtra("sID", sID);
                            liveForum.putExtra("sName", sName);
                            liveForum.putExtra("cName", cName);
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
            rsl.setText(rst[position]);

            return row;
        }
    }




    public void Back(View view){
        Intent n = new Intent(this,LecViewSession.class);
        n.putExtra("sID",sID);
        n.putExtra("sName",sName);
        n.putExtra("cID",cID);
        n.putExtra("cName",cName);
        n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(n);
        finish();
    }

    public void Refresh(View view){
        Toast.makeText(this, "Refreshing..", Toast.LENGTH_SHORT).show();
        Intent LecSessionViewLiveForum = new Intent(getApplicationContext(),LecSessionViewLiveForum.class);
        LecSessionViewLiveForum.putExtra("sID",sID);
        LecSessionViewLiveForum.putExtra("sName",sName);
        LecSessionViewLiveForum.putExtra("cID",cID);
        LecSessionViewLiveForum.putExtra("cName",cName);
        LecSessionViewLiveForum.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(LecSessionViewLiveForum);
        overridePendingTransition(0, 0);
        finish();
    }
}