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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StuViewAttemPollsQuestion extends AppCompatActivity {

    String sID,sName,User_ID;
    TextView sessionName;

    String SessionPollsURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionpollslec.php";
    ListView pollsListViewLV;

    private static String[] qNo;
    private static String[] qType;
    private static String[] question;
    private static String[] Status;
    private static String[] qCnt;
    private static String[] qAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_view_attem_polls_question);

        Intent intent =getIntent();
        sID = intent.getStringExtra("sID");
        sName = intent.getStringExtra("sName");
        User_ID = intent.getStringExtra("UserID");

        sessionName = findViewById(R.id.sessionName);
        sessionName.setText(sName);

        SessionPollsURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionpollslec.php?session_ID=" + sID;

        pollsListViewLV = (ListView)findViewById(R.id.pollsList);
        fetch_data_into_array(pollsListViewLV);

        pollsListViewLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if (qType[i].equals("mcq-tf") || qType[i].equals("mcq")) {
                    Intent mcq = new Intent(getApplicationContext(), StuViewAttemPollsQuesMcq.class);
                    //mcq.putExtra("sID", sID);
                    //mcq.putExtra("sName", sName);;
                    mcq.putExtra("qNo", qNo[i]);
                    //mcq.putExtra("qType",qType[i]);
                    mcq.putExtra("question", question[i]);
                    mcq.putExtra("qCnt", qCnt[i]);
                    mcq.putExtra("qAns",qAns[i]);
                    startActivity(mcq);
                    overridePendingTransition(0, 0);
                }
                if (qType[i].equals("open")) {
                    Intent open = new Intent(getApplicationContext(), StuViewAttemPollsQuesOpen.class);
                    //open.putExtra("sID", sID);
                    //open.putExtra("sName", sName);
                    open.putExtra("qNo", qNo[i]);
                    //open.putExtra("qType",qType[i]);
                    open.putExtra("question", question[i]);
                    open.putExtra("qCnt", qCnt[i]);
                    open.putExtra("qAns",qAns[i]);
                    startActivity(open);
                    overridePendingTransition(0, 0);
                }
            }
        });

    }

    private void fetch_data_into_array(View view) {
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    qNo = new String[jsonArray.length()];
                    qType = new String[jsonArray.length()];
                    question = new String[jsonArray.length()];
                    Status = new String[jsonArray.length()];
                    qCnt = new String[jsonArray.length()];
                    qAns = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        qNo[i] = jsonObject.getString("question_no");
                        qType[i] = jsonObject.getString("question_type");
                        question[i] = jsonObject.getString("question");
                        Status[i] = Integer.toString(i+1);
                        qCnt[i] = jsonObject.getString("question_count");
                        qAns[i] = jsonObject.getString("answer");
                    }

                    MyAdepter1 myAdepter = new MyAdepter1(getApplicationContext(), qNo, qType, question
                            , Status, qCnt, qAns);
                    pollsListViewLV.setAdapter(myAdepter);

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
        dbManager.execute(SessionPollsURL);
    }

    class MyAdepter1 extends ArrayAdapter<String> {

        Context context;
        String[] qNo;
        String[] qType;
        String[] question;
        String[] Status;
        String[] qDur;
        String[] qAns;

        MyAdepter1(Context context, String[] qNo, String[] qType, String[] question, String[] Status
                , String[] qDur, String[] qAns) {
            super(context, R.layout.layout_lec_session_polls, R.id.tvQNO, qNo);
            this.context = context;
            this.qNo = qNo;
            this.question = question;
            this.qType = qType;
            this.Status = Status;
            this.qDur = qDur;
            this.qAns = qAns;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_lec_session_polls, parent, false);

            TextView qno = row.findViewById(R.id.tvQNO);
            TextView ques = row.findViewById(R.id.tvQ);
            TextView qt = row.findViewById(R.id.tvQT);
            TextView qP = row.findViewById(R.id.points);
            TextView qD = row.findViewById(R.id.tvQDU);
            TextView qans = row.findViewById(R.id.tvQPT);

            qt.setText(qType[position]);
            qP.setText(Status[position]);
            qD.setText(qDur[position]);
            qno.setText(qNo[position]);
            ques.setText(question[position]);
            qans.setText(qAns[position]);

            return row;
        }

    }


    public void Back(View view){
        finish();
    }
}