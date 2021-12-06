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
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorkerForum extends AsyncTask<String, Void, String> {

    AlertDialog alertDialog;
    Context context;
    public BackgroundWorkerForum(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if(type.equals("NewTopicF")) {
            try {
                String Subject = params[1];
                String Question = params[2];
                String Forum_ID = params[3];
                String User_ID = params[4];
                String new_topic_URL = "http://10.0.2.2/ALec/public/api/V1/addnewcoursetopicforum.php";

                URL url = new URL(new_topic_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("subject", "UTF-8") + "=" + URLEncoder.encode(Subject, "UTF-8") + "&"
                        + URLEncoder.encode("question", "UTF-8") + "=" + URLEncoder.encode(Question, "UTF-8") + "&"
                        + URLEncoder.encode("forum_ID", "UTF-8") + "=" + URLEncoder.encode(Forum_ID, "UTF-8") + "&"
                        + URLEncoder.encode("user_ID", "UTF-8") + "=" + URLEncoder.encode(User_ID, "UTF-8");

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
        else if(type.equals("NewTopicReply")) {
            try {
                String tID = params[1];
                String Reply = params[2];
                String User_ID = params[3];
                String new_topic_URL = "http://10.0.2.2/ALec/public/api/V1/addnewforumreply.php";

                URL url = new URL(new_topic_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("topic_ID", "UTF-8") + "=" + URLEncoder.encode(tID, "UTF-8") + "&"
                        + URLEncoder.encode("reply", "UTF-8") + "=" + URLEncoder.encode(Reply, "UTF-8") + "&"
                        + URLEncoder.encode("user_ID", "UTF-8") + "=" + URLEncoder.encode(User_ID, "UTF-8");

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
        else if(type.equals("DeleteTopic")){
            try {
                String topic_ID = params[1];
                String delete_topic_URL = "http://10.0.2.2/ALec/public/api/V1/deleteforumreply.php?topic_ID="+topic_ID;
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
