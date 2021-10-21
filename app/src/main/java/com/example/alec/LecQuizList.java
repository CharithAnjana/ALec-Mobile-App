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

public class LecQuizList extends AppCompatActivity {


    String quizURL = "http://10.0.2.2/ALec/public/api/V1/quizlist.php";
    ListView quizListView;

    private static String[] qID;
    private static String[] qName;

    TextView Topic;
    String tID,tName,cID,cName,User_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_quiz_list);

        Topic = findViewById(R.id.qList);

        Intent intent =getIntent();
        tID = intent.getStringExtra("tID");
        tName = intent.getStringExtra("tName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("UserID");
        Topic.setText("Quiz - "+tName);

        quizURL = "http://10.0.2.2/ALec/public/api/V1/quizlist.php?topic_ID="+tID+"&type=Create";
        quizListView = (ListView)findViewById(R.id.quizList);
        fetch_data_into_array(quizListView);

        quizListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent LecViewQuizDetails = new Intent(getApplicationContext(), LecViewQuizDetails.class);
                LecViewQuizDetails.putExtra("qID",qID[i]);
                LecViewQuizDetails.putExtra("qName",qName[i]);
                LecViewQuizDetails.putExtra("tID",tID);
                LecViewQuizDetails.putExtra("tName",tName);
                LecViewQuizDetails.putExtra("cID",cID);
                LecViewQuizDetails.putExtra("cName",cName);
                LecViewQuizDetails.putExtra("UserID",User_ID);
                startActivity(LecViewQuizDetails);
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

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        qID[i] = jsonObject.getString("quiz_id");
                        qName[i] = jsonObject.getString("quiz_name");

                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),qID,qName);
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

        MyAdepter(Context context, String[] qID, String[] qName) {
            super(context, R.layout.layout_quiz_list,R.id.tvQI,qID);
            this.context = context;
            this.qID = qID;
            this.qName = qName;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_quiz_list,parent,false);;

            TextView tvQI = row.findViewById(R.id.tvQI);
            TextView tvQN = row.findViewById(R.id.tvQN);

            tvQI.setText(qID[position]);
            tvQN.setText(qName[position]);

            return row;
        }

    }

    public void Back(View view){

        Intent LecCourseTopics = new Intent(getApplicationContext(), LecCourseTopics.class);
        LecCourseTopics.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        LecCourseTopics.putExtra("cID",cID);
        LecCourseTopics.putExtra("cName",cName);
        LecCourseTopics.putExtra("UserID",User_ID);
        startActivity(LecCourseTopics);
        finish();
    }

    public void EditTopic(View view){
        Intent LecEditTopic = new Intent(this,LecEditTopic.class);
        LecEditTopic.putExtra("tID",tID);
        LecEditTopic.putExtra("tName",tName);
        LecEditTopic.putExtra("cID",cID);
        LecEditTopic.putExtra("cName",cName);
        LecEditTopic.putExtra("UserID",User_ID);
        startActivity(LecEditTopic);
    }

    public void DeleteTopic(View view){
        //finish();
        Intent LecDeleteTopic = new Intent(this,LecDeleteTopicPop.class);
        LecDeleteTopic.putExtra("tID",tID);
        LecDeleteTopic.putExtra("tName",tName);
        LecDeleteTopic.putExtra("cID",cID);
        LecDeleteTopic.putExtra("cName",cName);
        LecDeleteTopic.putExtra("UserID",User_ID);
        startActivity(LecDeleteTopic);
    }

    public void AddNewQuiz(View view){
        Intent LecAddQuizSetDetails = new Intent(this,LecAddQuizSetDetails.class);
        LecAddQuizSetDetails.putExtra("course",cName);
        LecAddQuizSetDetails.putExtra("courseId",cID);
        startActivity(LecAddQuizSetDetails);
    }
}