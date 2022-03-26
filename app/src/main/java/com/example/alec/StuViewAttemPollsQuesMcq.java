package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StuViewAttemPollsQuesMcq extends AppCompatActivity {

    String qNo,question,qAns;
    TextView ques, answer,a,ansA,b,ansB,c,ansC,d,ansD,e,ansE;
    String pollMcqStuURL = "http://10.0.2.2/ALec/public/api/V1/pollmcqansstu.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_view_attem_polls_ques_mcq);

        Intent intent = getIntent();
        qNo = intent.getStringExtra("qNo");
        question = intent.getStringExtra("question");
        //qCnt = intent.getStringExtra("qCnt");
        qAns = intent.getStringExtra("qAns");

        ques = findViewById(R.id.textViewq);
        answer = findViewById(R.id.mcqAnswer);
        ques.setText(question);
        answer.setText(qAns);

        a = findViewById(R.id.a);
        ansA = findViewById(R.id.ansA);
        b = findViewById(R.id.b);
        ansB = findViewById(R.id.ansB);
        c = findViewById(R.id.c);
        ansC = findViewById(R.id.ansC);
        d = findViewById(R.id.d);
        ansD = findViewById(R.id.ansD);
        e = findViewById(R.id.e);
        ansE = findViewById(R.id.ansE);

        pollMcqStuURL = "http://10.0.2.2/ALec/public/api/V1/pollmcqansstu.php?ques_ID="+qNo;
        fetch_data_into_textviews();
    }

    private void fetch_data_into_textviews() {
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;

                    String[] Ans = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        Ans[i] = jsonObject.getString("choice_name");
                    }

                    ansA.setText(Ans[0]);
                    ansB.setText(Ans[1]);

                    if(jsonArray.length()==3){
                        c.setVisibility(View.VISIBLE);
                        ansC.setVisibility(View.VISIBLE);
                        ansC.setText(Ans[2]);
                    }
                    if(jsonArray.length()==4){
                        c.setVisibility(View.VISIBLE);
                        ansC.setVisibility(View.VISIBLE);
                        ansC.setText(Ans[2]);
                        d.setVisibility(View.VISIBLE);
                        ansD.setVisibility(View.VISIBLE);
                        ansD.setText(Ans[3]);
                    }
                    if(jsonArray.length()==5){
                        c.setVisibility(View.VISIBLE);
                        ansC.setVisibility(View.VISIBLE);
                        ansC.setText(Ans[2]);
                        d.setVisibility(View.VISIBLE);
                        ansD.setVisibility(View.VISIBLE);
                        ansD.setText(Ans[3]);
                        e.setVisibility(View.VISIBLE);
                        ansE.setVisibility(View.VISIBLE);
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
        dbManager.execute(pollMcqStuURL);
    }

    public void Back(View view){
        finish();
    }
}