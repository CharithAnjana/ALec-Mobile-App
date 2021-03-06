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

public class LecCourseTopics extends AppCompatActivity {

    String topicURL = "http://10.0.2.2/ALec/public/api/V1/coursetopic.php";
    ListView topicListView;

    String cID,cName,User_ID;

    private static String[] tID;
    private static String[] tName;

    TextView Topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_course_topic);

        Topic = findViewById(R.id.cTopic);

        Intent intent =getIntent();
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("UserID");
        Topic.setText(cName);

        topicURL = "http://10.0.2.2/ALec/public/api/V1/coursetopic.php?course_ID="+cID;
        topicListView = (ListView)findViewById(R.id.topicList);
        fetch_data_into_array(topicListView);

        topicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent LecQuizList = new Intent(getApplicationContext(), LecQuizList.class);
                LecQuizList.putExtra("tID",tID[i]);
                LecQuizList.putExtra("tName",tName[i]);
                LecQuizList.putExtra("cID",cID);
                LecQuizList.putExtra("cName",cName);
                LecQuizList.putExtra("UserID",User_ID);
                startActivity(LecQuizList);
                finish();
            }
        });
    }

    private void fetch_data_into_array(ListView topicListView) {
        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    tID = new String[jsonArray.length()];
                    tName = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        tID[i] = jsonObject.getString("topic_id");
                        tName[i] = jsonObject.getString("topic_name");

                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),tID,tName);
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
        dbManager.execute(topicURL);

    }

    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] tID;
        String[] tName;

        MyAdepter(Context context, String[] tID, String[] tName) {
            super(context, R.layout.layout_course_topic,R.id.tvTI,tID);
            this.context = context;
            this.tID = tID;
            this.tName = tName;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_course_topic,parent,false);;

            TextView tvTI = row.findViewById(R.id.tvTI);
            TextView tvTN = row.findViewById(R.id.tvTN);

            tvTI.setText(tID[position]);
            tvTN.setText(tName[position]);

            return row;
        }

    }

    public void Back(View view){
        Intent LecMyCourse = new Intent(this,LecMyCourses.class);
        LecMyCourse.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(LecMyCourse);
    }

    public void NewTopic(View view){
        Intent intent = new Intent(this,LecAddNewTopic.class);
        intent.putExtra("cName",cName);
        intent.putExtra("cID",cID);
        intent.putExtra("User_ID",User_ID);
        startActivity(intent);
    }
}