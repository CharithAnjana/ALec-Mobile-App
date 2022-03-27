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

public class StuViewQuizList extends AppCompatActivity {

    String quizURL = "http://10.0.2.2/ALec/public/api/V1/quizliststu.php";
    ListView quizListView;

    private static String[] qID;
    private static String[] qName;
    private static String[] dur;
    private static String[] qAtm;
    private static String[] mks;

    TextView Topic;
    String tID,tName,cID,cName,User_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_view_quiz_list);

        Intent intent =getIntent();
        tID = intent.getStringExtra("tID");
        tName = intent.getStringExtra("tName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("UserID");

        Topic = findViewById(R.id.qList);
        Topic.setText("Quiz - "+tName);

        quizURL = "http://10.0.2.2/ALec/public/api/V1/quizliststu.php?topic_ID="+tID+"&user_ID="+User_ID;
        quizListView = (ListView)findViewById(R.id.quizListStu);
        fetch_data_into_array(quizListView);

        quizListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent StuViewQuizDetails = new Intent(getApplicationContext(), StuViewQuizDetails.class);
                StuViewQuizDetails.putExtra("qID",qID[i]);
                StuViewQuizDetails.putExtra("qName",qName[i]);
                StuViewQuizDetails.putExtra("dur",dur[i]);
                StuViewQuizDetails.putExtra("qAtm",qAtm[i]);
                StuViewQuizDetails.putExtra("mks",mks[i]);
                StuViewQuizDetails.putExtra("cName",cName);
                //StuViewQuizDetails.putExtra("UserID",User_ID);
                startActivity(StuViewQuizDetails);
            }
        });
    }

    private void fetch_data_into_array(ListView topicListView) {
        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    qID = new String[jsonArray.length()];
                    qName = new String[jsonArray.length()];
                    dur = new String[jsonArray.length()];
                    qAtm = new String[jsonArray.length()];
                    mks = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        qID[i] = jsonObject.getString("quiz_id");
                        qName[i] = jsonObject.getString("quiz_name");
                        dur[i] = jsonObject.getString("duration");
                        qAtm[i] = jsonObject.getString("attempt_time");
                        mks[i] = jsonObject.getString("marks");
                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),qID,qName,dur,qAtm,mks);
                    topicListView.setAdapter(myAdepter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                    StringBuffer sb = new StringBuffer();
                    String line = null;
                    String result = null;
                    while ((line = bufferedReader.readLine()) != null){
                        sb.append(line+"\n");
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
        String[] qDu;
        String[] qAt;
        String[] qM;

        MyAdepter(Context context, String[] qID, String[] qName,String[] qDu, String[] qAt,String[] qM) {
            super(context, R.layout.layout_quiz_list,R.id.tvQI,qID);
            this.context = context;
            this.qID = qID;
            this.qName = qName;
            this.qDu = qDu;
            this.qAt = qAt;
            this.qM = qM;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_quiz_list,parent,false);;

            TextView tvQI = row.findViewById(R.id.tvQI);
            TextView tvQN = row.findViewById(R.id.tvQN);
            TextView tvQDur = row.findViewById(R.id.tvQDur);
            TextView tvQAt = row.findViewById(R.id.tvQAt);
            TextView tvQMk = row.findViewById(R.id.tvQMks);

            tvQI.setText(qID[position]);
            tvQN.setText(qName[position]);
            tvQDur.setText(qDu[position]);
            tvQAt.setText(qAt[position]);
            tvQMk.setText(qM[position]);

            return row;
        }

    }

    public void Back(View view){
        finish();
    }

}