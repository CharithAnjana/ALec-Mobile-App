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

public class LecViewQuizDetailsDraft extends AppCompatActivity {

    String qID,qName,cID,cName;
    String cDate="", dur="", nofQues="";
    TextView quizName, courseName, createDate, duration, noQuestions;

    String quizURL = "http://10.0.2.2/ALec/public/api/V1/quizlist.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_view_quiz_details_draft);

        Intent intent =getIntent();
        qID = intent.getStringExtra("qID");
        qName = intent.getStringExtra("qName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");

        quizName = findViewById(R.id.quizName);
        courseName = findViewById(R.id.courseName);

        quizName = findViewById(R.id.quizName);
        courseName = findViewById(R.id.courseName);
        createDate = findViewById(R.id.createDate);
        duration = findViewById(R.id.duration);
        noQuestions = findViewById(R.id.noQuestions);

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

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        cDate = jsonObject.getString("create_date");
                        dur = jsonObject.getString("duration");
                        nofQues = jsonObject.getString("count");
                    }

                    createDate.setText(cDate);
                    duration.setText(dur);
                    noQuestions.setText(nofQues);

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

    public void Back(View view) {
        finish();
    }
    public void EditQuiz(View view) {
        Intent LecAddQuizQuestion = new Intent(this,LecAddQuizQuestion.class);
        LecAddQuizQuestion.putExtra("qID",qID);
        LecAddQuizQuestion.putExtra("cName",cName);
        LecAddQuizQuestion.putExtra("qName",qName);
        LecAddQuizQuestion.putExtra("qDuration",dur);
        startActivity(LecAddQuizQuestion);
    }
    public void DeleteQuiz(View view) {
        Intent LecQuizDeletePop2 = new Intent(this,LecQuizDeletePop2.class);
        LecQuizDeletePop2.putExtra("qID",qID);
        LecQuizDeletePop2.putExtra("cID",cID);
        LecQuizDeletePop2.putExtra("cName",cName);
        startActivity(LecQuizDeletePop2);
    }
}