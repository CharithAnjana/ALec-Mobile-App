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

public class LecViewDraftQuiz extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String course, cID, tId;
    Spinner spTopic;
    TextView cName;
    ListView quizListView;

    private static String[] qID;
    private static String[] qName;

    ArrayList<String> topicList = new ArrayList<>();
    ArrayList<String> topicIDList = new ArrayList<>();
    ArrayAdapter<String> topicAdapter;

    String topicURL = "http://10.0.2.2/ALec/public/api/V1/coursetopic.php";
    String quizURL = "http://10.0.2.2/ALec/public/api/V1/quizlist.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_view_draft_quiz);

        BackTopic bt = new BackTopic();
        bt.execute();

        Intent intent = getIntent();

        course = intent.getStringExtra("course");
        cID = intent.getStringExtra("courseId");
        tId = intent.getStringExtra("topicId");
        topicURL = "http://10.0.2.2/ALec/public/api/V1/coursetopic.php?course_ID=" + cID;

        cName = findViewById(R.id.cName);
        cName.setText(course);

        spTopic = findViewById(R.id.spTopic);

        topicAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout_topic,
                R.id.spntxt, topicList);
        spTopic.setAdapter(topicAdapter);
        spTopic.setOnItemSelectedListener(this);

    }


    public void Back(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        id = spTopic.getSelectedItemId();
        String tID = topicIDList.get((int) id);

        quizURL = "http://10.0.2.2/ALec/public/api/V1/quizlist.php?topic_ID=" + tID + "&type=Draft";
        quizListView = (ListView) findViewById(R.id.quizList);
        fetch_data_into_array(quizListView);

        quizListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent LecViewQuizDetailsDraft = new Intent(getApplicationContext(), LecViewQuizDetailsDraft.class);
                LecViewQuizDetailsDraft.putExtra("qID",qID[i]);
                LecViewQuizDetailsDraft.putExtra("qName",qName[i]);
                LecViewQuizDetailsDraft.putExtra("cID",cID);
                LecViewQuizDetailsDraft.putExtra("cName",course);
                startActivity(LecViewQuizDetailsDraft);
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

                for (int i = 0; i < jsonArray.length(); i++) {
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

    //<--get data to the List
    private void fetch_data_into_array(ListView topicListView) {
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    qID = new String[jsonArray.length()];
                    qName = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        qID[i] = jsonObject.getString("quiz_id");
                        qName[i] = jsonObject.getString("quiz_name");

                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(), qID, qName);
                    topicListView.setAdapter(myAdepter);

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

    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] qID;
        String[] qName;

        MyAdepter(Context context, String[] qID, String[] qName) {
            super(context, R.layout.layout_quiz_list, R.id.tvQI, qID);
            this.context = context;
            this.qID = qID;
            this.qName = qName;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_quiz_list, parent, false);
            ;

            TextView tvQI = row.findViewById(R.id.tvQI);
            TextView tvQN = row.findViewById(R.id.tvQN);

            tvQI.setText(qID[position]);
            tvQN.setText(qName[position]);

            return row;
        }
    }
    //end of the List-->
}