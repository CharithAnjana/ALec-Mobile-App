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
import android.widget.Button;
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

public class stu_forum_topic_reply_list extends AppCompatActivity {

    String forumReplyURL = "http://10.0.2.2/ALec/public/api/V1/viewforumreply.php";
    ListView questionListView;
    String tID,sName,uName,date,ques;
    TextView FTopic, textViewDate, textViewUserName, textViewQuestion;

    private static String[] rID;
    private static String[] reply;
    private static String[] userName;
    private static String[] postDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_forum_topic_reply_list);



        Intent intent =getIntent();
        tID = intent.getStringExtra("tID");
        sName = intent.getStringExtra("sName");
        uName = intent.getStringExtra("uName");
        date = intent.getStringExtra("date");
        ques = intent.getStringExtra("ques");

        FTopic = findViewById(R.id.ForumTopic);
        textViewDate = findViewById(R.id.date);
        textViewUserName = findViewById(R.id.userName);
        textViewQuestion = findViewById(R.id.ForumQuestion);

        FTopic.setText(sName);
        textViewDate.setText(date);
        textViewUserName.setText(uName);
        textViewQuestion.setText(ques);

        questionListView = (ListView)findViewById(R.id.replyList);
        forumReplyURL = "http://10.0.2.2/ALec/public/api/V1/viewforumreply.php?topic_ID="+tID;
        fetch_data_into_array(questionListView);

        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent StuForumReplyView = new Intent(getApplicationContext(), StuForumReplyView.class);
                StuForumReplyView.putExtra("fName", sName);
                StuForumReplyView.putExtra("uName", uName);
                StuForumReplyView.putExtra("date", date);
                StuForumReplyView.putExtra("ques", ques);
                StuForumReplyView.putExtra("reply", reply[i]);
                startActivity(StuForumReplyView);
            }
        });
    }


    private void fetch_data_into_array(ListView topicListView) {
        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    rID = new String[jsonArray.length()];
                    reply = new String[jsonArray.length()];
                    userName = new String[jsonArray.length()];
                    postDate = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        rID[i] = jsonObject.getString("reply_id");
                        reply[i] = jsonObject.getString("reply");
                        userName[i] = jsonObject.getString("name");
                        postDate[i] = jsonObject.getString("post_time");

                    }

                    stu_forum_topic_reply_list.MyAdepter myAdepter = new stu_forum_topic_reply_list.MyAdepter(getApplicationContext(),rID,reply,userName,postDate);
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
        dbManager.execute(forumReplyURL);

    }

    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] rID;
        String[] reply;
        String[] userName;
        String[] postDate;


        MyAdepter(Context context, String[] rID, String[] reply, String[] userName, String[] postDate) {
            super(context, R.layout.layout_forum_reply,R.id.tvTI,rID);
            this.context = context;
            this.rID = rID;
            this.reply = reply;
            this.userName = userName;
            this.postDate = postDate;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_forum_reply,parent,false);;

            TextView tvRI = row.findViewById(R.id.tvTI);
            TextView tvUN = row.findViewById(R.id.userName);
            TextView tvRP = row.findViewById(R.id.tvFS);
            TextView tvPT = row.findViewById(R.id.tvDT);

            tvRI.setText(rID[position]);
            tvRP.setText(reply[position]);
            tvUN.setText(userName[position]);
            tvPT.setText(postDate[position]);

            return row;
        }

    }

    public void AddReply(View view){
        Intent StuForumAddReply = new Intent(this, stu_forum_add_reply.class);
        StuForumAddReply.putExtra("tID",tID);
        StuForumAddReply.putExtra("sName",sName);
        StuForumAddReply.putExtra("uName",uName);
        StuForumAddReply.putExtra("date",date);
        StuForumAddReply.putExtra("ques",ques);
        startActivity(StuForumAddReply);
    }



    public void Back(View view){
        finish();
    }
}