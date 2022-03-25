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
import java.net.URL;

public class TaggedTopicDiscussion extends AppCompatActivity {

    String tagURL = "http://10.0.2.2/ALec/public/api/V1/viewalltagedtopicdiscussion.php";
    String  student_id,tag_name,tag_id;
    TextView TagName;
    ListView TaggedTopicList;

    private static String[] topic_id;
    private static String[] subject;
    private static String[] post_time;
    private static String[] name;
    private static String[] random_status;
    private static String[] random_name;
    private static String[] user_id;
    private static String[] user_type;
    private static String[] course_name;
    private static String[] Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagged_topic_list);

        Intent intent =getIntent();
        student_id = intent.getStringExtra("student_id");
        tag_name = intent.getStringExtra("tag_name");
        tag_id = intent.getStringExtra("tag_id");

        TaggedTopicList = (ListView)findViewById(R.id.tagList);
     //   tagURL = "http://10.0.2.2/ALec/public/api/V1/tagSingleView.php?userId="+student_id+"|topicSearchValues="+topicSearchValues+"|";
        fetch_data_into_array(TaggedTopicList);

        TagName = findViewById(R.id.tagname);
        TagName.setText(tag_name);

    }
    private void fetch_data_into_array(ListView view) {
        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    topic_id = new String[jsonArray.length()];
                    subject = new String[jsonArray.length()];
                    post_time = new String[jsonArray.length()];
                    name = new String[jsonArray.length()];
                    random_status = new String[jsonArray.length()];
                    random_name = new String[jsonArray.length()];
                    user_id = new String[jsonArray.length()];
                    user_type = new String[jsonArray.length()];
                    course_name = new String[jsonArray.length()];
                    Name = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        topic_id[i] = jsonObject.getString("topic_id");
                        subject[i] = jsonObject.getString("subject");
                        post_time[i] = jsonObject.getString("post_time");
                        name[i] = jsonObject.getString("name");
                        random_status[i] = jsonObject.getString("random_status");
                        random_name[i] = jsonObject.getString("random_name");
                        user_id[i] = jsonObject.getString("user_id");
                        user_type[i] = jsonObject.getString("user_type");
                        course_name[i] = jsonObject.getString("course_name");


                        if(random_status[i].equals("T")){
                            Name[i] = random_name[i];
                        }
                        else{
                            Name[i] = name[i];
                        }


                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),topic_id,subject,post_time,Name,course_name);
                    TaggedTopicList.setAdapter(myAdepter);

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
        dbManager.execute(tagURL);

    }
    class MyAdepter extends ArrayAdapter<String> {

        Context context;

        String[] topic_id;
        String[] subject;
        String[] post_time;
        String[] Name;
        //String[] user_id;
        //String[] user_type;
        String[] course_name;




        MyAdepter(Context context,String[] topic_id,String[] subject,String[] post_time,String[] Name,String[] course_name) {
            super(context, R.layout.layouttaggedtopiclist,R.id.tvFQ,topic_id);
            this.context = context;
            this.topic_id = topic_id;
            this.subject = subject;
            this.post_time = post_time;
            this.Name = Name;
            this.course_name = course_name;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layouttaggedtopiclist,parent,false);;

            TextView tvFS = row.findViewById(R.id.tvFS);
            TextView courseName = row.findViewById(R.id.courseName);
            TextView userName = row.findViewById(R.id.userName);
            TextView tvDT = row.findViewById(R.id.tvDT);
            TextView tvFQ = row.findViewById(R.id.tvFQ);
            TextView tvTI = row.findViewById(R.id.tvTI);
            TextView points = row.findViewById(R.id.points);





            tvFS.setText(subject[position]);
            courseName.setText(course_name[position]);
            userName.setText(Name[position]);
            tvDT.setText(post_time[position]);
            tvFQ.setText(topic_id[position]);
            // tvTI.setText(user_type[position]);
            // points.setText(user_id[position]);




            return row;
        }

    }

    public void deletetag(View view) {

        String Tag_id = tag_id;
        String type = "Deletetag";

        BackgroundWorkertag backgroundWorkertag = new BackgroundWorkertag(this);
        String result;
        try {
            result = backgroundWorkertag.execute(type, Tag_id).get();

            if(result.equals("Success")){
                Intent LecQuizList = new Intent(this, TaggedName.class);
                LecQuizList.putExtra("Student_id", student_id);

                LecQuizList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LecQuizList);
                finish();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void Back(View view){
        finish();
    }
}