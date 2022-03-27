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

public class LecForumTopicReplyView extends AppCompatActivity {
    String forumReplyURL = "http://10.0.2.2/ALec/public/api/V1/reply.php";

    String tID,sName,uName,date,ques,reply,rID;
    String cID,fID,cName,User_ID,point,ustype,flag = "1";
    TextView Username,Date,Question,ForumTopic,Reply,x;
    TextView FTopic,textViewDate,textViewUserName,textviewPoints;
    Button btn,btnPoint;
    ListView replyListView;
    private static String[] topic_id;
    private static String[] reply_id;
    private static String[] points;
    private static String[] post_time;
    private static String[] user_id;
    private static String[] Reply1;
    private static String[] RID;
    private static String[] Usertype;
    private static String[] userName;
    private static String[] postDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_forum_topic_reply_view);

        btn = findViewById(R.id.deleter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpopupwindow();

            }
        });



        Intent intent = getIntent();
        rID = intent.getStringExtra("rID");
        tID = intent.getStringExtra("tID");
        sName = intent.getStringExtra("sName");
        uName = intent.getStringExtra("uName");
        date = intent.getStringExtra("date");
        ques = intent.getStringExtra("ques");
        cID = intent.getStringExtra("cID");
        fID = intent.getStringExtra("fID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("User_ID");
        point = intent.getStringExtra("point");
        ustype = intent.getStringExtra("ustype");
        reply = intent.getStringExtra("reply");

        Username = findViewById(R.id.userName);
        Username.setText(uName);

        Date = findViewById(R.id.date);
        Date.setText(date);

        Question = findViewById(R.id.ForumQuestion);
        Question.setText(ques);

        ForumTopic = findViewById(R.id.ForumTopic);
        ForumTopic.setText(sName);

         Reply=findViewById(R.id.Reply);
        Reply.setText(reply);

        btnPoint=findViewById(R.id.addPoints);
       // btnPoint.setText(point);

        x=findViewById(R.id.points);
        x.setText(point);
        //To disable point button
        //if(ustype.equals("lec")){
       // btnPoint.setEnabled(false);
      // }





        String type = "TopicPoint";
        BackgroundWorkerReplyCheckPoints BgWCheckPoints = new BackgroundWorkerReplyCheckPoints(this);
        String result;
        try {
            result = BgWCheckPoints.execute(type, rID, User_ID).get();

            if(result.equals("point")){
                flag = "0";
                btnPoint.setText("-");
                btnPoint.setBackgroundColor(0xffffb300);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void openpopupwindow(){
        Intent openpopupwindow = new Intent(LecForumTopicReplyView.this, LecForumReplyDeletePop.class);

        openpopupwindow.putExtra("rID",rID);
        openpopupwindow.putExtra("tID",tID);
       openpopupwindow.putExtra("sName",sName);
       openpopupwindow.putExtra("date",date);
        openpopupwindow.putExtra("ques",ques);
        openpopupwindow.putExtra("cID",cID);
       openpopupwindow.putExtra("fID",fID);
        openpopupwindow.putExtra("User_ID",User_ID);
        openpopupwindow.putExtra("point",point);
        openpopupwindow.putExtra("ustype",ustype);
        openpopupwindow.putExtra("reply",reply);
        openpopupwindow.putExtra("uName",uName);
        openpopupwindow.putExtra("cName",cName);



        startActivity(openpopupwindow);
    }
    private void fetch_data_into_array(ListView topicListView) {
        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    RID = new String[jsonArray.length()];
                    Reply1 = new String[jsonArray.length()];
                    Usertype = new String[jsonArray.length()];
                    points = new String[jsonArray.length()];
                    userName = new String[jsonArray.length()];
                    postDate = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        RID[i] = jsonObject.getString("reply_id");
                        Reply1[i] = jsonObject.getString("reply");
                        Usertype[i] = jsonObject.getString("user_type");
                        points[i] = jsonObject.getString("points");
                        userName[i] = jsonObject.getString("name");
                        postDate[i] = jsonObject.getString("post_time");

                    }

                    LecForumTopicReplyView.MyAdepter myAdepter = new MyAdepter(getApplicationContext(),RID,Reply1,Usertype,
                            points,userName,postDate);
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
        String[] userType;
        String[] points;
        String[] userName;
        String[] postDate;


        MyAdepter(Context context, String[] rID, String[] reply, String[] userType, String[] points,
                  String[] userName, String[] postDate) {
            super(context, R.layout.layout_forum_reply,R.id.tvTI,rID);
            this.context = context;
            this.rID = rID;
            this.reply = reply;
            this.userType = userType;
            this.points = points;
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
            TextView tvP = row.findViewById(R.id.tvP);

            tvRI.setText(rID[position]);
            tvRP.setText(reply[position]);
            tvUN.setText(userName[position]);
            tvPT.setText(postDate[position]);
            tvP.setText(points[position]);

            return row;
        }

    }

    public void AddReply(View view){
        Intent LecForumAddReply = new Intent(this, LecForumAddReply.class);
        LecForumAddReply.putExtra("tID",tID);
        LecForumAddReply.putExtra("sName",sName);
        LecForumAddReply.putExtra("uName",uName);
        LecForumAddReply.putExtra("date",date);
        LecForumAddReply.putExtra("ques",ques);
        startActivity(LecForumAddReply);
    }

    public void DeleteReplyF(View view){

        //Intent LecForumAddReply = new Intent(this, LecDeleteTopicPop.class);
        // LecDeleteTopic.putExtra("tID",tID);
        //  LecDeleteTopic.putExtra("tName",tName);
        //  LecDeleteTopic.putExtra("cID",cID);
        //LecDeleteTopic.putExtra("sName",sName);
        // LecDeleteTopic.putExtra("UserID",User_ID);
        // startActivity(LecForumAddReply);
    }

    public void ManagePoints(View view){
        String type = "ManagePoints";
        BackgroundWorkerReplyCheckPoints BgWCheckPoints = new BackgroundWorkerReplyCheckPoints(this);
        String result;
        try {
            result = BgWCheckPoints.execute(type, rID, User_ID,flag).get();

            //if(result.equals("Success")){
            if(flag.equals("0")){
                point=Integer.toString(Integer.parseInt(point)-1);
            }
            else {
                point=Integer.toString(Integer.parseInt(point)+1);
            }
            Intent LecForumTopicReplyList = new Intent(getApplicationContext(), LecForumTopicReplyView.class);
            finish();
            LecForumTopicReplyList.putExtra("fID",fID);
            LecForumTopicReplyList.putExtra("cID",cID);
            LecForumTopicReplyList.putExtra("cName",cName);
            LecForumTopicReplyList.putExtra("User_ID",User_ID);
            LecForumTopicReplyList.putExtra("tID",tID);
            LecForumTopicReplyList.putExtra("sName",sName);
            LecForumTopicReplyList.putExtra("uName",uName);
            LecForumTopicReplyList.putExtra("date",date);
            LecForumTopicReplyList.putExtra("ques",ques);
            LecForumTopicReplyList.putExtra("point",point);
            LecForumTopicReplyList.putExtra("ustype",ustype);
            startActivity(LecForumTopicReplyList);
            overridePendingTransition(0, 0);
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Back(View view){
        Intent StuForumTopicReplyList = new Intent(getApplicationContext(), LecForumTopicReplyList.class);
        StuForumTopicReplyList.putExtra("fID",fID);
        StuForumTopicReplyList.putExtra("cID",cID);
        StuForumTopicReplyList.putExtra("cName",cName);
        StuForumTopicReplyList.putExtra("User_ID",User_ID);
        StuForumTopicReplyList.putExtra("tID",tID);
        StuForumTopicReplyList.putExtra("sName",sName);
        StuForumTopicReplyList.putExtra("uName",uName);
        StuForumTopicReplyList.putExtra("date",date);
        StuForumTopicReplyList.putExtra("ques",ques);
        StuForumTopicReplyList.putExtra("point",points);
        StuForumTopicReplyList.putExtra("user_type",ustype);
        startActivity(StuForumTopicReplyList);
        finish();
        //overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed()
    {
        Intent LecForumDiscTopic = new Intent(getApplicationContext(), LecForumDiscTopic.class);
        LecForumDiscTopic.putExtra("cID",cID);
        LecForumDiscTopic.putExtra("fID",fID);
        LecForumDiscTopic.putExtra("cName",cName);
        LecForumDiscTopic.putExtra("UserID",User_ID);
        startActivity(LecForumDiscTopic);
        finish();
    }
}