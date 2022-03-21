package com.example.alec;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorkerQuiz extends AsyncTask<String, Void, String> {

    AlertDialog alertDialog;
    Context context;
    public BackgroundWorkerQuiz(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if (type.equals("Create")) {
            try {
                String quiz_ID = params[1];
                String new_question_URL = "http://10.0.2.2/ALec/public/api/V1/createquizstatechange.php";

                URL url = new URL(new_question_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("quiz_ID", "UTF-8") + "=" + URLEncoder.encode(quiz_ID, "UTF-8");

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
        }else if (type.equals("DeleteQuiz")) {
                try {
                    String quiz_ID = params[1];
                    String new_question_URL = "http://10.0.2.2/ALec/public/api/V1/deletequiz.php";

                    URL url = new URL(new_question_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("quiz_ID", "UTF-8") + "=" + URLEncoder.encode(quiz_ID, "UTF-8");

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
        }else if (type.equals("Schedule")) {
            try {
                String quiz_ID = params[1];
                String pub_date = params[2];
                String cls_date = params[3];
                String quiz_dur = params[4];
                String st = params[5];
                String new_question_URL = "http://10.0.2.2/ALec/public/api/V1/schedulequiz.php";

                URL url = new URL(new_question_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("quiz_ID", "UTF-8") + "=" + URLEncoder.encode(quiz_ID, "UTF-8")+ "&"
                        + URLEncoder.encode("pub_date", "UTF-8") + "=" + URLEncoder.encode(pub_date, "UTF-8") + "&"
                        + URLEncoder.encode("cls_date", "UTF-8") + "=" + URLEncoder.encode(cls_date, "UTF-8") + "&"
                        + URLEncoder.encode("quiz_dur", "UTF-8") + "=" + URLEncoder.encode(quiz_dur, "UTF-8") + "&"
                        + URLEncoder.encode("st", "UTF-8") + "=" + URLEncoder.encode(st, "UTF-8");;

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
        }else if (type.equals("DeleteQuestion")) {
            try {
                String quiz_ID = params[1];
                String new_question_URL = "http://10.0.2.2/ALec/public/api/V1/deletequizquestion.php";

                URL url = new URL(new_question_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("question_NO", "UTF-8") + "=" + URLEncoder.encode(quiz_ID, "UTF-8");

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
