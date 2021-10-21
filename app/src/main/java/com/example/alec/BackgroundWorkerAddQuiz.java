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

public class BackgroundWorkerAddQuiz extends AsyncTask<String, Void, String> {


    Context context;
    public BackgroundWorkerAddQuiz(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if(type.equals("NewQuiz")) {
            try {
                String topic_ID = params[1];
                String lecturer_ID = params[2];
                String quizName = params[3];
                String dur = params[4];
                String new_quiz_URL = "http://10.0.2.2/ALec/public/api/V1/addnewquiz.php";

                URL url = new URL(new_quiz_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("topic_ID", "UTF-8") + "=" + URLEncoder.encode(topic_ID, "UTF-8") + "&"
                        + URLEncoder.encode("lecturer_ID", "UTF-8") + "=" + URLEncoder.encode(lecturer_ID, "UTF-8") + "&"
                        + URLEncoder.encode("quiz_Name", "UTF-8") + "=" + URLEncoder.encode(quizName, "UTF-8") + "&"
                        + URLEncoder.encode("dur", "UTF-8") + "=" + URLEncoder.encode(dur, "UTF-8");

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
    }

    @Override
    protected void onPostExecute(String result) {
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
