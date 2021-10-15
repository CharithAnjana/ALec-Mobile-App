package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;

public class LecAddQuizSetDetails extends AppCompatActivity {

    String UserID, course, cID;
    Spinner spTopic;
    EditText qName, dur;
    TextView cName;

    ArrayList<String> topicList = new ArrayList<>();
    ArrayList<String> topicIDList = new ArrayList<>();
    ArrayAdapter<String> topicAdapter;

    String topicURL = "http://10.0.2.2/ALec/public/api/V1/coursetopic.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_add_quiz_set_details);

        Intent intent =getIntent();

        course = intent.getStringExtra("course");
        cID = intent.getStringExtra("courseId");
        topicURL = "http://10.0.2.2/ALec/public/api/V1/coursetopic.php?course_ID="+cID;

        qName = findViewById(R.id.quizName);
        dur = findViewById(R.id.qTimeHr);

        cName = findViewById(R.id.cName);
        cName.setText(course);

        spTopic =findViewById(R.id.spTopic);

        topicAdapter = new ArrayAdapter<String>(this,R.layout.spinner_layout_topic,
                R.id.spntxt, topicList);
        spTopic.setAdapter(topicAdapter);

        SessionManagement sm = new SessionManagement(this);
        UserID = sm.getSessionId();

    }

    @Override
    protected void onStart() {
        super.onStart();
        BackTopic bt = new BackTopic();
        bt.execute();
    }


    //<--get data to the spinner
    private class BackTopic extends AsyncTask<Void, Void, Void> {

        ArrayList<String> tlist, tidlist;
        protected void onPreExecute() {
            super.onPreExecute();
            tlist = new ArrayList<>();
            tidlist = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String result = null;
            try {
                URL url = new URL(topicURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                StringBuffer sb = new StringBuffer();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                bufferedInputStream.close();
                result = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = null;

                for (int i=0; i<jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    tidlist.add(jsonObject.getString("topic_id"));
                    tlist.add(jsonObject.getString("topic_name"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            topicList.addAll(tlist);
            topicIDList.addAll(tidlist);
            topicAdapter.notifyDataSetChanged();
        }
    }
    //end of the spinner-->

    public void Back(View view){
        finish();
    }

    public void Next(View view){
        if(validateTopic(topicList) && validateQName(qName) && validateTime(dur)) {
            long id = spTopic.getSelectedItemId();
            String tID = topicIDList.get((int) id);
            String quizName = qName.getText().toString();
            String duHr = dur.getText().toString();
            String type = "NewQuiz";

            BackgroundWorkerAddQuiz backgroundWorkerAddQuiz = new BackgroundWorkerAddQuiz(this);
            String result;
            try {
                result = backgroundWorkerAddQuiz.execute(type, tID, UserID, quizName, duHr).get();

                if(!(result.equals("Error"))){

                    Intent LecAddQuizQuestion = new Intent(this,LecAddQuizQuestion.class);
                    LecAddQuizQuestion.putExtra("qID",result);
                    LecAddQuizQuestion.putExtra("cName",course);
                    LecAddQuizQuestion.putExtra("qName",quizName);
                    LecAddQuizQuestion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(LecAddQuizQuestion);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }




    //validations
    private boolean validateTopic(ArrayList<String> Topics){
        if(!Topics.isEmpty()){
            return true;
        }
        else {
            Toast.makeText(this, "No Topics Available", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean validateQName(EditText Name){
        String name = Name.getText().toString();
        if(!name.isEmpty()){
            return true;
        }
        else {
            Toast.makeText(this, "Invalid Topic Name", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean validateTime(EditText Hr){
        String hr = Hr.getText().toString();
        if(!hr.isEmpty()){
            return true;
        }
        else {
            Toast.makeText(this, "Invalid Time Duration", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}