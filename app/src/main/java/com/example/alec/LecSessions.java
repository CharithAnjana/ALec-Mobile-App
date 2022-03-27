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

public class LecSessions extends AppCompatActivity {

    String actSessionURL = "http://10.0.2.2/ALec/public/api/V1/viewactsessions.php";
    String rctSessionURL = "http://10.0.2.2/ALec/public/api/V1/viewrcntsessions.php";
    ListView actvSessionLV,recntSessionLV;

    private static String[] sID1;
    private static String[] sName1;
    private static String[] cID1;
    private static String[] cName1;
    private static String[] crDate1;

    private static String[] sID2;
    private static String[] sName2;
    private static String[] cID2;
    private static String[] cName2;
    private static String[] crDate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_sessions);

        SessionManagement sessionManagement = new SessionManagement(this);
        String userID = sessionManagement.getSessionId();

        actSessionURL = "http://10.0.2.2/ALec/public/api/V1/viewactsessions.php?user_ID="+userID;
        rctSessionURL = "http://10.0.2.2/ALec/public/api/V1/viewrcntsessions.php?user_ID="+userID;

        actvSessionLV = (ListView)findViewById(R.id.actSess);
        recntSessionLV = (ListView)findViewById(R.id.rctSess);
        fetch_data_into_array1(actvSessionLV);
        fetch_data_into_array2(recntSessionLV);

        actvSessionLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent LecViewSession = new Intent(getApplicationContext(),LecViewSession.class);
                LecViewSession.putExtra("sID",sID1[position]);
                LecViewSession.putExtra("sName",sName1[position]);
                LecViewSession.putExtra("cID",cID1[position]);
                LecViewSession.putExtra("cName",cName1[position]);
                startActivity(LecViewSession);
                overridePendingTransition(0, 0);
            }
        });

        recntSessionLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent LecViewSession = new Intent(getApplicationContext(),LecViewSession.class);
                LecViewSession.putExtra("sID",sID2[position]);
                LecViewSession.putExtra("sName",sName2[position]);
                LecViewSession.putExtra("cID",cID2[position]);
                LecViewSession.putExtra("cName",cName2[position]);
                startActivity(LecViewSession);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void fetch_data_into_array1(View view) {
        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    sID1 = new String[jsonArray.length()];
                    sName1 = new String[jsonArray.length()];
                    cID1 = new String[jsonArray.length()];
                    cName1 = new String[jsonArray.length()];
                    crDate1 = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        sID1[i] = jsonObject.getString("session_id");
                        sName1[i] = jsonObject.getString("session_name");
                        cID1[i] = jsonObject.getString("course_id");
                        cName1[i] = jsonObject.getString("course_name");
                        crDate1[i] = jsonObject.getString("create_date");
                    }

                    MyAdepter1 myAdepter = new MyAdepter1(getApplicationContext(),sID1,sName1,cID1
                            ,cName1,crDate1);
                    actvSessionLV.setAdapter(myAdepter);

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
        dbManager.execute(actSessionURL);
    }

    private void fetch_data_into_array2(View view) {
        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    sID2 = new String[jsonArray.length()];
                    sName2 = new String[jsonArray.length()];
                    cID2 = new String[jsonArray.length()];
                    cName2 = new String[jsonArray.length()];
                    crDate2 = new String[jsonArray.length()];

                    for (int i=0; i<jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        sID2[i] = jsonObject.getString("session_id");
                        sName2[i] = jsonObject.getString("session_name");
                        cID2[i] = jsonObject.getString("course_id");
                        cName2[i] = jsonObject.getString("course_name");
                        crDate2[i] = jsonObject.getString("create_date");
                    }

                    MyAdepter1 myAdepter = new MyAdepter1(getApplicationContext(),sID2,sName2,cID2
                            ,cName2,crDate2);
                    recntSessionLV.setAdapter(myAdepter);

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
        dbManager.execute(rctSessionURL);
    }

    class MyAdepter1 extends ArrayAdapter<String> {

        Context context;
        String[] sID;
        String[] sName;
        String[] cID;
        String[] cName;
        String[] crDate;

        MyAdepter1(Context context, String[] sID, String[] sName, String[] cID, String[] cName
                , String[] crDate) {
            super(context, R.layout.layout_sessions,R.id.tvSID,sID);
            this.context = context;
            this.sID = sID;
            this.sName = sName;
            this.cID = cID;
            this.cName = cName;
            this.crDate = crDate;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_sessions,parent,false);;

            TextView SID = row.findViewById(R.id.tvSID);
            TextView coName = row.findViewById(R.id.tvCName);
            TextView Session = row.findViewById(R.id.tvSess);
            TextView date = row.findViewById(R.id.tvDT);
            TextView CID = row.findViewById(R.id.tvCID);

            SID.setText(sID[position]);
            coName.setText(cName[position]);
            date.setText(crDate[position]);
            Session.setText(sName[position]);
            CID.setText(cID[position]);

            return row;
        }

    }

    public void Back(View view){
        finish();
    }
}