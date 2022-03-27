package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LecViewQuizDetails extends AppCompatActivity {

    String qID,qName,cID,cName,User_ID,tID,tName;
    TextView quizName, courseName, createDate, publishedDate, closeDate, duration, noQuestions;
    Button BtnSchedl;
    String quizURL = "http://10.0.2.2/ALec/public/api/V1/quizlist.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_view_quiz_details);

        Intent intent =getIntent();
        qID = intent.getStringExtra("qID");
        qName = intent.getStringExtra("qName");
        tID = intent.getStringExtra("tID");
        tName = intent.getStringExtra("tName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("UserID");

        quizName = findViewById(R.id.quizName);
        courseName = findViewById(R.id.courseName);
        createDate = findViewById(R.id.createDate);
        publishedDate = findViewById(R.id.publishDate);
        closeDate = findViewById(R.id.closeDate);
        duration = findViewById(R.id.duration);
        noQuestions = findViewById(R.id.noQuestions);

        BtnSchedl = findViewById(R.id.scheduleBtn);

        quizName.setText(qName);
        courseName.setText(cName);

        quizURL = "http://10.0.2.2/ALec/public/api/V1/quizlist.php?topic_ID=" + qID + "&type=QuizD";
        fetch_data_into_textviews();

    }

    private void fetch_data_into_textviews() {
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;

                    String cDate="", pubDate="", clsDate="", dur="", nofQues="";

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        cDate = jsonObject.getString("create_date");
                        pubDate = jsonObject.getString("published_date");
                        clsDate = jsonObject.getString("close_date");
                        dur = jsonObject.getString("duration");
                        nofQues = jsonObject.getString("count");
                    }

                    createDate.setText(cDate);
                    duration.setText(dur);
                    noQuestions.setText(nofQues);

                    if(!(pubDate.equals("null"))){
                        publishedDate.setText(pubDate);
                        closeDate.setText(clsDate);
                        BtnSchedl.setText("Cancel-Schedule");
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
        dbManager.execute(quizURL);
    }



    public void Back(View view){
        Intent LecQuizList = new Intent(this,LecQuizList.class);
        LecQuizList.putExtra("tID",tID);
        LecQuizList.putExtra("tName",tName);
        LecQuizList.putExtra("cID",cID);
        LecQuizList.putExtra("cName",cName);
        LecQuizList.putExtra("UserID",User_ID);
        LecQuizList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(LecQuizList);
    }

    public void PrewQuiz(View view){
        Intent LecViewQuiz = new Intent(getApplicationContext(), LecViewQuiz.class);
        LecViewQuiz.putExtra("qID",qID);
        LecViewQuiz.putExtra("qName",qName);
        LecViewQuiz.putExtra("tID",tID);
        LecViewQuiz.putExtra("tName",tName);
        LecViewQuiz.putExtra("cID",cID);
        LecViewQuiz.putExtra("cName",cName);
        LecViewQuiz.putExtra("UserID",User_ID);
        LecViewQuiz.putExtra("qDuhr",duration.getText().toString());
        startActivity(LecViewQuiz);
    }

    public void ScheduleQuiz(View view){
        String val = BtnSchedl.getText().toString();
        if(val.equals("Cancel-Schedule")){
            Intent LecQuizScheduleCancelPop = new Intent(this,LecQuizScheduleCancelPop.class);
            LecQuizScheduleCancelPop.putExtra("qID",qID);
            LecQuizScheduleCancelPop.putExtra("qName",qName);
            LecQuizScheduleCancelPop.putExtra("qDuHr",duration.getText());
            LecQuizScheduleCancelPop.putExtra("tID",tID);
            LecQuizScheduleCancelPop.putExtra("tName",tName);
            LecQuizScheduleCancelPop.putExtra("cID",cID);
            LecQuizScheduleCancelPop.putExtra("cName",cName);
            LecQuizScheduleCancelPop.putExtra("UserID",User_ID);
            startActivity(LecQuizScheduleCancelPop);
        }
        else{
            Intent LecScheduleQuiz = new Intent(this,LecScheduleQuiz.class);
            LecScheduleQuiz.putExtra("qID",qID);
            LecScheduleQuiz.putExtra("qName",qName);
            LecScheduleQuiz.putExtra("qDuHr",duration.getText());
            LecScheduleQuiz.putExtra("Val","Later");
            LecScheduleQuiz.putExtra("tID",tID);
            LecScheduleQuiz.putExtra("tName",tName);
            LecScheduleQuiz.putExtra("cID",cID);
            LecScheduleQuiz.putExtra("cName",cName);
            LecScheduleQuiz.putExtra("UserID",User_ID);
            startActivity(LecScheduleQuiz);
        }
    }

}