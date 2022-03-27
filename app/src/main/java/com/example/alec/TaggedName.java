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

public class TaggedName extends AppCompatActivity {

    String TagURL = "http://10.0.2.2/ALec/public/api/V1/viewtags.php";
    ListView TagList;
    String Student_id,fID,cName,User_ID;
    TextView FTopic;

    private static String[] tag_id;
    private static String[] tag_name;
    private static String[] student_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagged_name);

        SessionManagement sessionManagement = new SessionManagement(this);
         Student_id = sessionManagement.getSessionId();
        TagList = (ListView)findViewById(R.id.tagList);
        TagURL = "http://10.0.2.2/ALec/public/api/V1/viewtags.php?student_id="+Student_id;
        fetch_data_into_array(TagList);

        TagList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent TaggedTopicList = new Intent(getApplicationContext(), TaggedTopicList.class);
                TaggedTopicList.putExtra("student_id",student_id[i]);
                TaggedTopicList.putExtra("tag_name",tag_name[i]);
                TaggedTopicList.putExtra("tag_id",tag_id[i]);


                startActivity(TaggedTopicList);
            }
        });
    }
    private void fetch_data_into_array(ListView TagList) {
        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    tag_id = new String[jsonArray.length()];
                    tag_name = new String[jsonArray.length()];
                    student_id = new String[jsonArray.length()];


                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        tag_id[i] = jsonObject.getString("tag_id");
                        tag_name[i] = jsonObject.getString("tag_name");
                        student_id[i] = jsonObject.getString("student_id");


                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),tag_id,tag_name,student_id);
                    TagList.setAdapter(myAdepter);

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
        dbManager.execute(TagURL);

    }

    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] tag_id;
        String[] tag_name;
        String[] student_id;




        MyAdepter(Context context, String[] tag_id, String[] tag_name, String[] student_id) {
            super(context, R.layout.layout_tagged_topic_list,R.id.tvTI,tag_id);
            this.context = context;
            this.tag_id = tag_id;
            this.tag_name = tag_name;
            this.student_id = student_id;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_tagged_topic_list,parent,false);;

            TextView tvTN = row.findViewById(R.id.tvTN);
            TextView tvTI = row.findViewById(R.id.tvTI);
            TextView tvSI = row.findViewById(R.id.tvSI);


            tvTN.setText(tag_name[position]);
            tvTI.setText(tag_id[position]);
            tvSI.setText(student_id[position]);


            return row;
        }

    }

    public void NewDiscTopic(View view){
        Intent Createnewtag = new Intent(this, Createnewtag.class);
        Createnewtag.putExtra("Student_id",Student_id);
        startActivity(Createnewtag);
    }

    public void Back(View view){
        finish();
    }
}