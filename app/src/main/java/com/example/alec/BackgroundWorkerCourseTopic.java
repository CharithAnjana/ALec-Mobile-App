package com.example.alec;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorkerCourseTopic extends AsyncTask<String, Void, String> {

    AlertDialog alertDialog;
    Context context;
    public BackgroundWorkerCourseTopic(Context ctx){
        context = ctx;
    }


    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if(type.equals("NewTopic")) {
            try {
                String course_ID = params[1];
                String lecturer_ID = params[2];
                String topic_Name = params[3];
                String topic_Descrip = params[4];
                String new_topic_URL = "http://10.0.2.2/ALec/public/api/V1/addnewcoursetopic.php";

                URL url = new URL(new_topic_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("course_ID", "UTF-8") + "=" + URLEncoder.encode(course_ID, "UTF-8") + "&"
                        + URLEncoder.encode("lecturer_ID", "UTF-8") + "=" + URLEncoder.encode(lecturer_ID, "UTF-8") + "&"
                        + URLEncoder.encode("topic_Name", "UTF-8") + "=" + URLEncoder.encode(topic_Name, "UTF-8") + "&"
                        + URLEncoder.encode("topic_Descrip", "UTF-8") + "=" + URLEncoder.encode(topic_Descrip, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(type.equals("DeleteTopic")){
            try {
                String topic_ID = params[1];
                String delete_topic_URL = "http://10.0.2.2/ALec/public/api/V1/deletecoursetopic.php?topic_ID="+topic_ID;
                URL url = new URL(delete_topic_URL);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                //StringBuffer sb = new StringBuffer();
                String line = "";
                String result = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedInputStream.close();
                bufferedReader.close();
                conn.disconnect();
                //result = sb.toString();

                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(type.equals("EditTopic")) {
            try {
                String Topic_ID = params[1];
                String Topic_Name = params[2];
                String edit_topic_URL = "http://10.0.2.2/ALec/public/api/V1/editcoursetopic.php";

                URL url = new URL(edit_topic_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("topic_ID", "UTF-8") + "=" + URLEncoder.encode(Topic_ID, "UTF-8") + "&"
                        + URLEncoder.encode("topic_Name", "UTF-8") + "=" + URLEncoder.encode(Topic_Name, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
