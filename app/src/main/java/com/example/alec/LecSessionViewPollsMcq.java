package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class LecSessionViewPollsMcq extends AppCompatActivity {

    String qNo,qAns,question,qCnt, sID,sName,cID,cName;
    TextView ques, count, answer,a,ansA,b,ansB,c,ansC,d,ansD,e,ansE,ap,bp,cp,dp,ep;
    Button Poll;
    String pollMcqLecURL = "http://10.0.2.2/ALec/public/api/V1/pollmcqanslec.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_session_view_polls_mcq);

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
        answer = findViewById(R.id.mcqAnswer);
        ques.setText(question);
        count.setText(qCnt);
        answer.setText(qAns);

        a = findViewById(R.id.a);
        ansA = findViewById(R.id.ansA);
        ap = findViewById(R.id.ap);
        b = findViewById(R.id.b);
        ansB = findViewById(R.id.ansB);
        bp = findViewById(R.id.bp);
        c = findViewById(R.id.c);
        ansC = findViewById(R.id.ansC);
        cp = findViewById(R.id.cp);
        d = findViewById(R.id.d);
        ansD = findViewById(R.id.ansD);
        dp = findViewById(R.id.dp);
        e = findViewById(R.id.e);
        ansE = findViewById(R.id.ansE);
        ep = findViewById(R.id.ep);

        pollMcqLecURL = "http://10.0.2.2/ALec/public/api/V1/pollmcqanslec.php?ques_ID="+qNo;
        fetch_data_into_textviews();


        //To check Poll status
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

    }


    private void fetch_data_into_textviews() {
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;

                    String[] Ans = new String[jsonArray.length()];
                    String[] cnt = new String[jsonArray.length()];
                    int qc = Integer.parseInt(qCnt);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        Ans[i] = jsonObject.getString("choice_name");
                        cnt[i] = jsonObject.getString("answer_count");
                    }
                    int aap,bbp,ccp,ddp,eep;

                    //to prevent divide by 0 error
                    if(qc==0){qc=1;}
                    //

                    aap = ((Integer.parseInt(cnt[0]))/qc)*100;
                    bbp = ((Integer.parseInt(cnt[1]))/qc)*100;




                    ansA.setText(Ans[0]);
                    ansB.setText(Ans[1]);
                    ap.setText(Integer.toString(aap)+"%");
                    bp.setText(Integer.toString(bbp)+"%");

                    if(jsonArray.length()==3){
                        ccp = ((Integer.parseInt(cnt[2]))/qc)*100;
                        c.setVisibility(View.VISIBLE);
                        ansC.setVisibility(View.VISIBLE);
                        cp.setVisibility(View.VISIBLE);
                        cp.setText(Integer.toString(ccp)+"%");
                        ansC.setText(Ans[2]);
                    }
                    if(jsonArray.length()==4){
                        ccp = ((Integer.parseInt(cnt[2]))/qc)*100;
                        c.setVisibility(View.VISIBLE);
                        ansC.setVisibility(View.VISIBLE);
                        cp.setVisibility(View.VISIBLE);
                        cp.setText(Integer.toString(ccp)+"%");
                        ansC.setText(Ans[2]);

                        ddp = ((Integer.parseInt(cnt[3]))/qc)*100;
                        d.setVisibility(View.VISIBLE);
                        ansD.setVisibility(View.VISIBLE);
                        dp.setVisibility(View.VISIBLE);
                        dp.setText(Integer.toString(ddp)+"%");
                        ansD.setText(Ans[3]);
                    }
                    if(jsonArray.length()==5){
                        ccp = ((Integer.parseInt(cnt[2]))/qc)*100;
                        c.setVisibility(View.VISIBLE);
                        ansC.setVisibility(View.VISIBLE);
                        ansC.setText(Ans[2]);
                        cp.setVisibility(View.VISIBLE);
                        cp.setText(Integer.toString(ccp)+"%");

                        ddp = ((Integer.parseInt(cnt[3]))/qc)*100;
                        d.setVisibility(View.VISIBLE);
                        ansD.setVisibility(View.VISIBLE);
                        dp.setVisibility(View.VISIBLE);
                        dp.setText(Integer.toString(ddp)+"%");
                        ansD.setText(Ans[3]);

                        eep = ((Integer.parseInt(cnt[4]))/qc)*100;
                        e.setVisibility(View.VISIBLE);
                        ansE.setVisibility(View.VISIBLE);
                        ep.setVisibility(View.VISIBLE);
                        ep.setText(Integer.toString(eep)+"%");
                        ansE.setText(Ans[4]);
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
        dbManager.execute(pollMcqLecURL);
    }

    public void Back(View view){
        Intent n = new Intent(this,LecViewSession.class);
        n.putExtra("sID",sID);
        n.putExtra("sName",sName);
        n.putExtra("cID",cID);
        n.putExtra("cName",cName);
        n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(n);
        finish();finish();
    }

    public void Refresh(View view){
        Toast.makeText(this, "Refreshing..", Toast.LENGTH_SHORT).show();
        Intent LecSessionViewPollsMcq = new Intent(getApplicationContext(),LecSessionViewPollsMcq.class);
        LecSessionViewPollsMcq.putExtra("sID",sID);
        LecSessionViewPollsMcq.putExtra("sName",sName);
        LecSessionViewPollsMcq.putExtra("cID",cID);
        LecSessionViewPollsMcq.putExtra("cName",cName);
        LecSessionViewPollsMcq.putExtra("qNo",qNo);
        LecSessionViewPollsMcq.putExtra("question",question);
        LecSessionViewPollsMcq.putExtra("qCnt",qCnt);
        LecSessionViewPollsMcq.putExtra("qAns",qAns);
        LecSessionViewPollsMcq.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(LecSessionViewPollsMcq);
        overridePendingTransition(0, 0);
        finish();
    }

    public void ManagePollMcq(View view){
        String type = "ManagePollStatus";
        BackgroundWorkerManageSession BgWCheckPoints = new BackgroundWorkerManageSession(this);
        String result;
        try {
            result = BgWCheckPoints.execute(type, sID, qNo).get();

            if(result != "Error"){
                Intent LecSessionViewPollsMcq = new Intent(getApplicationContext(),LecSessionViewPollsMcq.class);
                LecSessionViewPollsMcq.putExtra("sID",sID);
                LecSessionViewPollsMcq.putExtra("sName",sName);
                LecSessionViewPollsMcq.putExtra("cID",cID);
                LecSessionViewPollsMcq.putExtra("cName",cName);
                LecSessionViewPollsMcq.putExtra("qNo",qNo);
                LecSessionViewPollsMcq.putExtra("question",question);
                LecSessionViewPollsMcq.putExtra("qCnt",qCnt);
                LecSessionViewPollsMcq.putExtra("qAns",qAns);
                LecSessionViewPollsMcq.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LecSessionViewPollsMcq);
                overridePendingTransition(0, 0);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}