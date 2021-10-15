package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LecAddQuizSeltCurs extends AppCompatActivity {

    Spinner spCourse;
    ArrayList<String> courseList = new ArrayList<>();
    ArrayList<String> courseIDList = new ArrayList<>();
    ArrayAdapter courseAdapter;

    String courseURL = "http://10.0.2.2/ALec/public/api/V1/mycourse.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_add_quiz_selt_curs);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.5));

        spCourse = findViewById(R.id.spCourse);

        SessionManagement sessionManagement = new SessionManagement(this);
        String user_ID = sessionManagement.getSessionId();
        String user_Type = sessionManagement.getSessionKey();
        courseURL = "http://10.0.2.2/ALec/public/api/V1/mycourse.php?user_ID="+user_ID+"&user_Type="+user_Type;

        courseAdapter = new ArrayAdapter<String>(this,R.layout.spinner_layout_course,
                R.id.spntxt, courseList);
        spCourse.setAdapter(courseAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        BackCourse bc = new BackCourse();
        bc.execute();
    }

    private class BackCourse extends AsyncTask<Void, Void, Void> {

        ArrayList<String> clist, cidlist;
        protected void onPreExecute() {
            super.onPreExecute();
            clist = new ArrayList<>();
            cidlist = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String result = null;
            try {
                URL url = new URL(courseURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                StringBuffer sb = new StringBuffer();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                bufferedInputStream.close();
                result = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = null;

                for (int i=0; i<jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    cidlist.add(jsonObject.getString("course_id"));
                    clist.add(jsonObject.getString("course_name"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            courseList.addAll(clist);
            courseIDList.addAll(cidlist);
            courseAdapter.notifyDataSetChanged();
        }
    }


    public void Cancel(View view){
        finish();
    }

    public void Next(View view){
        if(validateCourse(courseList)) {
            String course = spCourse.getSelectedItem().toString();
            long id = spCourse.getSelectedItemId();
            String cID = courseIDList.get((int) id);

            Intent LecAddQuizSetDetails = new Intent(this, LecAddQuizSetDetails.class);
            LecAddQuizSetDetails.putExtra("course", course);
            LecAddQuizSetDetails.putExtra("courseId", cID);
            startActivity(LecAddQuizSetDetails);
            finish();
        }
    }

    private boolean validateCourse(ArrayList<String> Course){
        if(!Course.isEmpty()){
            return true;
        }
        else {
            Toast.makeText(this, "No Courses Available", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}