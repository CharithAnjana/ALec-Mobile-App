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

    AlertDialog alertDialog;
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


        }else if(type.equals("NewQuestShort")){
            try {
                String quiz_ID = params[1];
                String question = params[2];
                String question_type = params[3];
                String answer = params[4];
                String point = params[4];
                String new_question_URL = "http://10.0.2.2/ALec/public/api/V1/addnewquizquestionshort.php";

                URL url = new URL(new_question_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("quiz_ID", "UTF-8") + "=" + URLEncoder.encode(quiz_ID, "UTF-8") + "&"
                        + URLEncoder.encode("question", "UTF-8") + "=" + URLEncoder.encode(question, "UTF-8") + "&"
                        + URLEncoder.encode("question_type", "UTF-8") + "=" + URLEncoder.encode(question_type, "UTF-8") + "&"
                        + URLEncoder.encode("answer", "UTF-8") + "=" + URLEncoder.encode(answer, "UTF-8") + "&"
                        + URLEncoder.encode("point", "UTF-8") + "=" + URLEncoder.encode(point, "UTF-8");

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

        }else if(type.equals("EditTopic")) {
            try {
                String quiz_ID = params[1];
                String question = params[2];
                String question_type = params[3];
                String answer = params[4];
                String point = params[4];
                String Topic_ID = params[5];
                String Topic_Name = params[6];
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
