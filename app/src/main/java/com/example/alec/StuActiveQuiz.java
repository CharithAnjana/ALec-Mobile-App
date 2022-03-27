package com.example.alec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslCertificate;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class StuActiveQuiz extends AppCompatActivity {

    String quizURL = "http://10.0.2.2/ALec/public/api/V1/quizlistpublish.php";
    ListView quizListView;

    private static String[] qID;
    private static String[] qName;
    private static String[] pubDate;
    private static String[] clsDate;
    private static String[] dur;

    TextView Topic;
    String tID, tName, cID, cName, User_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_active_quiz);

        SessionManagement se = new SessionManagement(this);
        User_ID = se.getSessionId();

        quizURL = "http://10.0.2.2/ALec/public/api/V1/quizlistpublish.php?user_ID=" + User_ID;
        quizListView = (ListView) findViewById(R.id.quizList);
        fetch_data_into_array(quizListView);

        quizListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent StuAttemptQuizDetails = new Intent(getApplicationContext(), StuAttemptQuizDetails.class);
                StuAttemptQuizDetails.putExtra("qID", qID[i]);
                StuAttemptQuizDetails.putExtra("qName", qName[i]);
                StuAttemptQuizDetails.putExtra("pubDate", pubDate[i]);
                StuAttemptQuizDetails.putExtra("clsDate", clsDate[i]);
                StuAttemptQuizDetails.putExtra("userID", User_ID);
                StuAttemptQuizDetails.putExtra("dur", dur[i]);
                startActivity(StuAttemptQuizDetails);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void fetch_data_into_array(ListView topicListView) {
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    qID = new String[jsonArray.length()];
                    qName = new String[jsonArray.length()];
                    pubDate = new String[jsonArray.length()];
                    clsDate = new String[jsonArray.length()];
                    dur = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        qID[i] = jsonObject.getString("quiz_id");
                        qName[i] = jsonObject.getString("quiz_name");
                        pubDate[i] = jsonObject.getString("published_date");
                        clsDate[i] = jsonObject.getString("close_date");
                        dur[i] = jsonObject.getString("duration");
                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(), qID,qName,pubDate,clsDate,dur);
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
        String[] pubDate;
        String[] clsDate;
        String[] dur;

        MyAdepter(Context context, String[] qID, String[] qName, String[] pubDate, String[] clsDate,
                  String[] dur) {
            super(context, R.layout.layout_quiz_list, R.id.tvQI, qID);
            this.context = context;
            this.qID = qID;
            this.qName = qName;
            this.pubDate = pubDate;
            this.clsDate = clsDate;
            this.dur = dur;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_quiz_active, parent, false);
            ;

            TextView tvQI = row.findViewById(R.id.tvQI);
            TextView tvQN = row.findViewById(R.id.tvQN);
            TextView tvQPD = row.findViewById(R.id.tvQPD);
            TextView tvQCD = row.findViewById(R.id.tvQCD);
            TextView tvQD = row.findViewById(R.id.tvQDur);

            tvQI.setText(qID[position]);
            tvQN.setText(qName[position]);
            tvQPD.setText(pubDate[position]);
            tvQCD.setText(clsDate[position]);
            tvQD.setText(dur[position]);

            return row;
        }

    }

    public void Back(View view) {
        finish();
    }

    public void Refresh(View view) {
        Toast.makeText(this, "Refreshing..", Toast.LENGTH_SHORT).show();
        Intent ActQuiz = new Intent(getApplicationContext(), StuActiveQuiz.class);
        ActQuiz.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ActQuiz);
        overridePendingTransition(0, 0);
        finish();
    }

}