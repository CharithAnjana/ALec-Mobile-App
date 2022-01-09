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

public class StuSessionSelect extends AppCompatActivity {

    String courseSURL = "http://10.0.2.2/ALec/public/api/V1/mycoursesessions.php";
    ListView courseSessionLV;

    private static String[] cID;
    private static String[] sID;
    private static String[] sName;
    private static String[] cName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_session_select);

        SessionManagement sessionManagement = new SessionManagement(this);
        String user_ID = sessionManagement.getSessionId();
        courseSURL = "http://10.0.2.2/ALec/public/api/V1/mycoursesessions.php?user_ID="+user_ID;

        courseSessionLV = (ListView)findViewById(R.id.sessionSelectList);
        fetch_data_into_array(courseSessionLV);

        courseSessionLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent StuSessionPolls = new Intent(getApplicationContext(), StuSessionPolls.class);
                StuSessionPolls.putExtra("cID",cID[i]);
                StuSessionPolls.putExtra("sID",sID[i]);
                StuSessionPolls.putExtra("sName",sName[i]);
                StuSessionPolls.putExtra("cName",cName[i]);
                StuSessionPolls.putExtra("UserID",user_ID);
                startActivity(StuSessionPolls);
                overridePendingTransition(0, 0);
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
                    sID = new String[jsonArray.length()];
                    sName = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        cID[i] = jsonObject.getString("course_id");
                        cName[i] = jsonObject.getString("course_name");
                        sID[i] = jsonObject.getString("session_id");
                        sName[i] = jsonObject.getString("session_name");
                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),cID,sID,cName,sName);
                    courseSessionLV.setAdapter(myAdepter);

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
        dbManager.execute(courseSURL);
    }


    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] cID;
        String[] sID;
        String[] cName;
        String[] sName;

        MyAdepter(Context context, String[] cID, String[] sID, String[] cName, String[] sName) {
            super(context, R.layout.layout_forum_course,R.id.tvCI,cID);
            this.context = context;
            this.cID = cID;
            this.sID = sID;
            this.cName = cName;
            this.sName = sName;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_forum_course,parent,false);;

            TextView tvCI = row.findViewById(R.id.tvCI);
            TextView tvFI = row.findViewById(R.id.tvFI);
            TextView tvCN = row.findViewById(R.id.tvCN);

            tvCI.setText(cID[position]);
            tvFI.setText(sID[position]);
            tvCN.setText(cName[position]);

            return row;
        }

    }

    public void Back(View view){
        finish();
    }
}