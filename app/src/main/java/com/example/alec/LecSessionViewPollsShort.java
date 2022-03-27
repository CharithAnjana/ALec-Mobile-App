package com.example.alec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class LecSessionViewPollsShort extends AppCompatActivity {

    String qNo,qAns,question,qCnt, sID,sName,cID,cName;
    TextView ques, count, answer;
    Button Poll;
    String pollMcqLecURL = "http://10.0.2.2/ALec/public/api/V1/pollopenanslec.php";

    private static String[] aNo;
    private static String[] Ans;
    private static String[] cnt;
    ListView sessionPollsAnsLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_session_view_polls_short);

        Intent intent = getIntent();
        qNo = intent.getStringExtra("qNo");
        qAns = intent.getStringExtra("qAns");
        question = intent.getStringExtra("question");
        qCnt = intent.getStringExtra("qCnt");
        sID = intent.getStringExtra("sID");
        sName = intent.getStringExtra("sName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");

        ques = findViewById(R.id.textViewq);
        count = findViewById(R.id.textViewcount);
        Poll = findViewById(R.id.buttonSe);
        answer = findViewById(R.id.openAnswer);
        ques.setText(question);
        count.setText(qCnt);
        answer.setText(qAns);

        String type = "PollStatus";
        BackgroundWorkerSessionQuestion BgWCheckPoints = new BackgroundWorkerSessionQuestion(this);
        String result;
        try {
            result = BgWCheckPoints.execute(type, qNo).get();
            if(result.equals("T")){
                Poll.setText("Disable");
                Poll.setBackgroundColor(Color.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        pollMcqLecURL = "http://10.0.2.2/ALec/public/api/V1/pollopenanslec.php?ques_ID="+qNo;
        sessionPollsAnsLV = findViewById(R.id.pollShortAnswers);
        if(Integer.parseInt(qCnt)>0) {
            fetch_data_into_array1(sessionPollsAnsLV);
        }

    }

    private void fetch_data_into_array1(View view) {
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    aNo = new String[jsonArray.length()];
                    Ans = new String[jsonArray.length()];
                    cnt = new String[jsonArray.length()];

                    int qc = Integer.parseInt(qCnt);
                    int c;
                    String pc;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        aNo[i] = Integer.toString(i+1);
                        Ans[i] = jsonObject.getString("answer");
                        cnt[i] = jsonObject.getString("answer_count");

                        c = Integer.parseInt(cnt[i]);
                        pc = Integer.toString((c/qc)*100);
                        cnt[i] = pc+"%";
                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(), aNo, Ans, cnt);
                    sessionPollsAnsLV.setAdapter(myAdepter);

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
        dbManager.execute(pollMcqLecURL);
    }

    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] aNo;
        String[] ans;
        String[] per;

        MyAdepter(Context context, String[] aNo, String[] Ans, String[] per) {
            super(context, R.layout.layout_session_poll_short, R.id.tvQ, Ans);
            this.context = context;
            this.aNo = aNo;
            this.ans = Ans;
            this.per = per;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_session_poll_short, parent, false);


            TextView qans = row.findViewById(R.id.tvQ);
            //TextView qaNO = row.findViewById(R.id.points);
            TextView qpre = row.findViewById(R.id.per);

            //qaNO.setText(aNo[position]);
            qans.setText(ans[position]);
            qpre.setText(per[position]);


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
        Intent LecSessionViewPollsShort = new Intent(getApplicationContext(),LecSessionViewPollsShort.class);
        LecSessionViewPollsShort.putExtra("sID",sID);
        LecSessionViewPollsShort.putExtra("sName",sName);
        LecSessionViewPollsShort.putExtra("cID",cID);
        LecSessionViewPollsShort.putExtra("cName",cName);
        LecSessionViewPollsShort.putExtra("qNo",qNo);
        LecSessionViewPollsShort.putExtra("question",question);
        LecSessionViewPollsShort.putExtra("qCnt",qCnt);
        LecSessionViewPollsShort.putExtra("qAns",qAns);
        LecSessionViewPollsShort.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(LecSessionViewPollsShort);
        overridePendingTransition(0, 0);
        finish();
    }

    public void ManagePollOpen(View view){
        String type = "ManagePollStatus";
        BackgroundWorkerManageSession BgWCheckPoints = new BackgroundWorkerManageSession(this);
        String result;
        try {
            result = BgWCheckPoints.execute(type, sID, qNo).get();

            if(result != "Error"){
                Intent LecSessionViewPollsShort = new Intent(getApplicationContext(),LecSessionViewPollsShort.class);
                LecSessionViewPollsShort.putExtra("sID",sID);
                LecSessionViewPollsShort.putExtra("sName",sName);
                LecSessionViewPollsShort.putExtra("cID",cID);
                LecSessionViewPollsShort.putExtra("cName",cName);
                LecSessionViewPollsShort.putExtra("qNo",qNo);
                LecSessionViewPollsShort.putExtra("question",question);
                LecSessionViewPollsShort.putExtra("qCnt",qCnt);
                LecSessionViewPollsShort.putExtra("qAns",qAns);
                LecSessionViewPollsShort.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LecSessionViewPollsShort);
                overridePendingTransition(0, 0);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}