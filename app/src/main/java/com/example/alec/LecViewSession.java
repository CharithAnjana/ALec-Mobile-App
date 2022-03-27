package com.example.alec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LecViewSession extends AppCompatActivity {

    String SessionPollsURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionpollslec.php";
    String SessionForumURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionliveforumlec.php";

    String sID, sName, cID, cName;
    TextView sname, forumTxt;
    ImageView online;
    Button manage;
    ListView sessionPollsLV, sessionForumLV;

    private static String[] qNo;
    private static String[] qType;
    private static String[] question;
    private static String[] Status;
    private static String[] qCnt;

    private static String[] qNof;
    private static String[] stIdf;
    private static String[] questionf;
    private static String[] pointsf;
    private static String[] postTimef;
    private static String[] rstf;
    private static String[] stNamef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_view_session);

        Intent intent = getIntent();
        sID = intent.getStringExtra("sID");
        sName = intent.getStringExtra("sName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");

        sname = findViewById(R.id.textViewSeName);
        forumTxt = findViewById(R.id.textliveForum);
        online = findViewById(R.id.imageView3);
        manage = findViewById(R.id.buttonSe);
        sname.setText(sName);

        sessionPollsLV = findViewById(R.id.sessionPolls);
        sessionForumLV = findViewById(R.id.sessionForum);

        //To check session status
        String type = "sessionStatus";
        BackgroundWorkerSessionQuestion BgWCheckPoints = new BackgroundWorkerSessionQuestion(this);
        String result;
        try {
            result = BgWCheckPoints.execute(type, sID).get();

            if (result.equals("F")) {
                online.setVisibility(View.INVISIBLE);

            }
            if (result.equals("T")) {
                manage.setText("Stop");
                manage.setBackgroundColor(Color.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        String r = "0";
        SessionPollsURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionpollslec.php?session_ID=" + sID;
        SessionForumURL = "http://10.0.2.2/ALec/public/api/V1/viewsessionliveforumlec.php?session_ID=" + sID + "&f=" + r;

        fetch_data_into_array1(sessionPollsLV);
        fetch_data_into_array2(sessionForumLV);

        sessionPollsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if (qType[i].equals("mcq-tf") || qType[i].equals("mcq")) {
                    Intent LecSessionViewPollsMcq = new Intent(getApplicationContext(), LecSessionViewPollsMcq.class);
                    LecSessionViewPollsMcq.putExtra("sID", sID);
                    LecSessionViewPollsMcq.putExtra("sName", sName);
                    LecSessionViewPollsMcq.putExtra("cID", cID);
                    LecSessionViewPollsMcq.putExtra("cName", cName);
                    LecSessionViewPollsMcq.putExtra("qNo", qNo[i]);
                    //LecSessionViewPollsMcq.putExtra("qType",qType[i]);
                    LecSessionViewPollsMcq.putExtra("question", question[i]);
                    LecSessionViewPollsMcq.putExtra("qCnt", qCnt[i]);
                    //LecSessionViewPollsMcq.putExtra("qPubTime",qPubTime[i]);
                    startActivity(LecSessionViewPollsMcq);
                    overridePendingTransition(0, 0);
                }
                if (qType[i].equals("open")) {
                    Intent LecSessionViewPollsShort = new Intent(getApplicationContext(), LecSessionViewPollsShort.class);
                    LecSessionViewPollsShort.putExtra("sID", sID);
                    LecSessionViewPollsShort.putExtra("sName", sName);
                    LecSessionViewPollsShort.putExtra("cID", cID);
                    LecSessionViewPollsShort.putExtra("cName", cName);
                    LecSessionViewPollsShort.putExtra("qNo", qNo[i]);
                    //LecSessionViewPollsShort.putExtra("qType",qType[i]);
                    LecSessionViewPollsShort.putExtra("question", question[i]);
                    LecSessionViewPollsShort.putExtra("qCnt", qCnt[i]);
                    //LecSessionViewPollsShort.putExtra("qPubTime",qPubTime[i]);
                    startActivity(LecSessionViewPollsShort);
                    overridePendingTransition(0, 0);
                }
            }
        });

        forumTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LecSessionViewLiveForum = new Intent(getApplicationContext(), LecSessionViewLiveForum.class);
                LecSessionViewLiveForum.putExtra("sID", sID);
                LecSessionViewLiveForum.putExtra("sName", sName);
                LecSessionViewLiveForum.putExtra("cID", cID);
                LecSessionViewLiveForum.putExtra("cName", cName);
                startActivity(LecSessionViewLiveForum);
                overridePendingTransition(0, 0);
            }
        });

        //To underline Text
        SpannableString content = new SpannableString("Live Forum");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        forumTxt.setText(content);
    }


    private void fetch_data_into_array1(View view) {
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

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        qNo[i] = jsonObject.getString("question_no");
                        qType[i] = jsonObject.getString("question_type");
                        question[i] = jsonObject.getString("question");
                        Status[i] = jsonObject.getString("status");
                        qCnt[i] = jsonObject.getString("question_count");

                        if (Status[i].equals("T")) {
                            Status[i] = "\uD83D\uDC41";
                        } else {
                            Status[i] = "*";
                        }
                    }

                    MyAdepter1 myAdepter = new MyAdepter1(getApplicationContext(), qNo, qType, question
                            , Status, qCnt);
                    sessionPollsLV.setAdapter(myAdepter);

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

    private void fetch_data_into_array2(View view) {
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    int len = jsonArray.length();
                    if (len > 2) {
                        len = 2;
                    }

                    qNof = new String[len];
                    stIdf = new String[len];
                    questionf = new String[len];
                    pointsf = new String[len];
                    postTimef = new String[len];
                    rstf = new String[len];
                    stNamef = new String[len];

                    for (int i = 0; i < len; i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        qNof[i] = jsonObject.getString("question_id");
                        stIdf[i] = jsonObject.getString("student_id");
                        questionf[i] = jsonObject.getString("question");
                        pointsf[i] = jsonObject.getString("points");
                        postTimef[i] = jsonObject.getString("post_time");
                        rstf[i] = jsonObject.getString("resolved_status");
                        stNamef[i] = jsonObject.getString("name");
                        if (rstf[i].equals("0")) {
                            rstf[i] = "⛔";
                        } else {
                            rstf[i] = "✔️";
                        }
                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(), qNof, stIdf, questionf
                            , pointsf, postTimef, stNamef, rstf);
                    sessionForumLV.setAdapter(myAdepter);

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
        dbManager.execute(SessionForumURL);
    }

    class MyAdepter1 extends ArrayAdapter<String> {

        Context context;
        String[] qNo;
        String[] qType;
        String[] question;
        String[] Status;
        String[] qDur;

        MyAdepter1(Context context, String[] qNo, String[] qType, String[] question, String[] Status
                , String[] qDur) {
            super(context, R.layout.layout_lec_session_polls, R.id.tvQNO, qNo);
            this.context = context;
            this.qNo = qNo;
            this.question = question;
            this.qType = qType;
            this.Status = Status;
            this.qDur = qDur;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_lec_session_polls, parent, false);
            ;

            TextView qno = row.findViewById(R.id.tvQNO);
            TextView ques = row.findViewById(R.id.tvQ);
            TextView qt = row.findViewById(R.id.tvQT);
            TextView qP = row.findViewById(R.id.points);
            TextView qD = row.findViewById(R.id.tvQDU);


            qt.setText(qType[position]);
            qP.setText(Status[position]);
            qD.setText(qDur[position]);
            qno.setText(qNo[position]);
            ques.setText(question[position]);


            return row;
        }

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
                            Intent liveForum = new Intent(context, LecViewSession.class);
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


    public void ManageSession(View view) {
        if (manage.getText().toString().equals("Stop"))
        {
            Intent LecViewSession = new Intent(getApplicationContext(), LecSessionStopPop.class);
            LecViewSession.putExtra("sID", sID);
            LecViewSession.putExtra("sName", sName);
            LecViewSession.putExtra("cID", cID);
            LecViewSession.putExtra("cName", cName);
            startActivity(LecViewSession);
        }
        else {
            String type = "ManageStatus";
            BackgroundWorkerManageSession BgWCheckPoints = new BackgroundWorkerManageSession(this);
            String result;
            try {
                result = BgWCheckPoints.execute(type, sID, cID).get();

                if (result != "Error") {
                    Intent LecViewSession = new Intent(getApplicationContext(), LecViewSession.class);
                    LecViewSession.putExtra("sID", sID);
                    LecViewSession.putExtra("sName", sName);
                    LecViewSession.putExtra("cID", cID);
                    LecViewSession.putExtra("cName", cName);
                    LecViewSession.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(LecViewSession);
                    overridePendingTransition(0, 0);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Back(View view) {
        Intent n = new Intent(this, LecSessions.class);
        n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(n);
        finish();
    }

    public void Refresh(View view) {
        Toast.makeText(this, "Refreshing..", Toast.LENGTH_SHORT).show();
        Intent LecViewSession = new Intent(getApplicationContext(), LecViewSession.class);
        LecViewSession.putExtra("sID", sID);
        LecViewSession.putExtra("sName", sName);
        LecViewSession.putExtra("cID", cID);
        LecViewSession.putExtra("cName", cName);
        LecViewSession.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(LecViewSession);
        overridePendingTransition(0, 0);
        finish();
    }
}