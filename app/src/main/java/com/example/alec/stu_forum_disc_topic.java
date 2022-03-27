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

public class stu_forum_disc_topic extends AppCompatActivity {

    String forumTopicURL = "http://10.0.2.2/ALec/public/api/V1/viewforumtopic.php";
    ListView ftopicListView;
    String cID,fID,cName,User_ID;
    TextView FTopic;

    private static String[] tID;
    private static String[] sName;
    private static String[] uName;
    private static String[] date;
    private static String[] ques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_forum_disc_topic);

        Intent intent =getIntent();
        cID = intent.getStringExtra("cID");
        fID = intent.getStringExtra("fID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("UserID");

        FTopic = findViewById(R.id.ForumTopic);
        FTopic.setText("Discussion Forum For\n"+cName);

        ftopicListView = (ListView)findViewById(R.id.topicList);
        forumTopicURL = "http://10.0.2.2/ALec/public/api/V1/viewforumtopic.php?course_ID="+cID;
        fetch_data_into_array(ftopicListView);

        ftopicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent StuForumTopicReplyList = new Intent(getApplicationContext(), stu_forum_topic_reply_list.class);
                StuForumTopicReplyList.putExtra("tID",tID[i]);
                StuForumTopicReplyList.putExtra("sName",sName[i]);
                StuForumTopicReplyList.putExtra("uName",uName[i]);
                StuForumTopicReplyList.putExtra("date",date[i]);
                StuForumTopicReplyList.putExtra("ques",ques[i]);
                startActivity(StuForumTopicReplyList);
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
                    sName = new String[jsonArray.length()];
                    uName = new String[jsonArray.length()];
                    date = new String[jsonArray.length()];
                    ques = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        tID[i] = jsonObject.getString("topic_id");
                        sName[i] = jsonObject.getString("subject");
                        ques[i] = jsonObject.getString("question");
                        uName[i] = jsonObject.getString("user_name");
                        date[i] = jsonObject.getString("post_time");

                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),tID,sName,ques,uName,date);
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
        dbManager.execute(forumTopicURL);

    }

    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] tID;
        String[] sName;
        String[] ques;
        String[] uName;
        String[] date;


        MyAdepter(Context context, String[] tID, String[] sName, String[] ques, String[] uName, String[] date) {
            super(context, R.layout.layout_forum_topic,R.id.tvTI,tID);
            this.context = context;
            this.tID = tID;
            this.sName = sName;
            this.ques = ques;
            this.uName = uName;
            this.date = date;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_forum_topic,parent,false);;

            TextView tvTI = row.findViewById(R.id.tvTI);
            TextView tvUN = row.findViewById(R.id.userName);
            TextView tvFS = row.findViewById(R.id.tvFS);
            TextView tvDT = row.findViewById(R.id.tvDT);
            TextView tvFQ = row.findViewById(R.id.tvFQ);

            tvTI.setText(tID[position]);
            tvUN.setText(uName[position]);
            tvFS.setText(sName[position]);
            tvDT.setText(date[position]);
            tvFQ.setText(ques[position]);

            return row;
        }

    }

    public void NewDiscTopic(View view){
        Intent StuForumAddNewDiscTopic = new Intent(this, stu_forum_add_new_disc_topic.class);
        StuForumAddNewDiscTopic.putExtra("cName",cName);
        StuForumAddNewDiscTopic.putExtra("fID",fID);
        StuForumAddNewDiscTopic.putExtra("cID",cID);
        StuForumAddNewDiscTopic.putExtra("uID",User_ID);
        startActivity(StuForumAddNewDiscTopic);
    }

    public void Back(View view){
        finish();
    }
}