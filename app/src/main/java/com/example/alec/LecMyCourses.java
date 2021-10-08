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

public class LecMyCourses extends AppCompatActivity {

    String courseURL = "http://10.0.2.2/ALec/public/api/V1/mycourse.php";
    ListView courseListView;

    private static String[] cID;
    private static String[] cName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_my_courses);

        SessionManagement sessionManagement = new SessionManagement(this);
        String user_ID = sessionManagement.getSessionId();
        String user_Type = sessionManagement.getSessionKey();
        courseURL = "http://10.0.2.2/ALec/public/api/V1/mycourse.php?user_ID="+user_ID+"&user_Type="+user_Type;

        courseListView = (ListView)findViewById(R.id.courseList);
        fetch_data_into_array(courseListView);

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent LecCourseTopics = new Intent(getApplicationContext(), LecCourseTopics.class);
                LecCourseTopics.putExtra("cID",cID[i]);
                LecCourseTopics.putExtra("cName",cName[i]);
                LecCourseTopics.putExtra("UserID",user_ID);
                startActivity(LecCourseTopics);
            }
        });
    }

    private void fetch_data_into_array(View View) {

        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    cID = new String[jsonArray.length()];
                    cName = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        cID[i] = jsonObject.getString("course_id");
                        cName[i] = jsonObject.getString("course_name");

                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),cID,cName);
                    courseListView.setAdapter(myAdepter);

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
        dbManager.execute(courseURL);
    }


    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] cID;
        String[] cName;

        MyAdepter(Context context, String[] cID, String[] cName) {
            super(context, R.layout.layout_my_courses,R.id.tvCI,cID);
            this.context = context;
            this.cID = cID;
            this.cName = cName;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_my_courses,parent,false);;

            TextView tvCI = row.findViewById(R.id.tvCI);
            TextView tvCN = row.findViewById(R.id.tvCN);

            tvCI.setText(cID[position]);
            tvCN.setText(cName[position]);

            return row;
        }

    }

    public void Back(View view){
        Intent LecHomeActivity = new Intent(this,LecHomeActivity.class);
        startActivity(LecHomeActivity);
        finish();
    }
}