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

public class BackgroundWorkerLiveForumVotes extends AsyncTask<String, Void, String> {

    Context context;

    public BackgroundWorkerLiveForumVotes(Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if(type.equals("ManageVotes")) {
            try {
                String user_id = params[1];
                String question_id = params[2];
                String new_topic_URL = "http://10.0.2.2/ALec/public/api/V1/manageliveforumvotes.php";

                URL url = new URL(new_topic_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("user_ID", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&"
                        + URLEncoder.encode("question_ID", "UTF-8") + "=" + URLEncoder.encode(question_id, "UTF-8");

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
        else if(type.equals("AddQuestion")) {
            try {
                String user_id = params[1];
                String question = params[2];
                String session_id = params[3];
                String rst = params[4];
                String new_topic_URL = "http://10.0.2.2/ALec/public/api/V1/addnewsessionforumquestion.php";

                URL url = new URL(new_topic_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("user_ID", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&"
                        + URLEncoder.encode("question", "UTF-8") + "=" + URLEncoder.encode(question, "UTF-8") + "&"
                        + URLEncoder.encode("session_ID", "UTF-8") + "=" + URLEncoder.encode(session_id, "UTF-8") + "&"
                        + URLEncoder.encode("rst", "UTF-8") + "=" + URLEncoder.encode(rst, "UTF-8");

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
        if(type.equals("ManageResolve")) {
            try {
                String question_id = params[1];
                String new_topic_URL = "http://10.0.2.2/ALec/public/api/V1/manageliveforumresolve.php";

                URL url = new URL(new_topic_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("question_ID", "UTF-8") + "=" + URLEncoder.encode(question_id, "UTF-8");

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
    protected void onPreExecute() {}

    @Override
    protected void onPostExecute(String result) {}

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
