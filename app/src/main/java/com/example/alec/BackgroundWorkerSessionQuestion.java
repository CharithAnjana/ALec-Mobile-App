package com.example.alec;

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

public class BackgroundWorkerSessionQuestion extends AsyncTask<String, Void, String> {

    Context context;
    public BackgroundWorkerSessionQuestion(Context ctx){ context = ctx; }
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
        else if(type.equals("sessionStatus")){
            try {
                String session_id = params[1];
                String delete_topic_URL = "http://10.0.2.2/ALec/public/api/V1/managesession.php?session_id="+session_id;
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
        else if(type.equals("PollStatus")){
            try {
                String question_id = params[1];
                String delete_topic_URL = "http://10.0.2.2/ALec/public/api/V1/managesessionpoll.php?question_id="+question_id;
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
        else if(type.equals("ShortAnsAtmp")){
            try {
                String question_id = params[1];
                String user_id = params[2];
                String ans = params[3];
                String attempt_poll_URL = "http://10.0.2.2/ALec/public/api/V1/attemptsessionpoll.php?question_id="+question_id;
                URL url = new URL(attempt_poll_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("user_ID", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&"
                        + URLEncoder.encode("question_id", "UTF-8") + "=" + URLEncoder.encode(question_id, "UTF-8") + "&"
                        + URLEncoder.encode("ans", "UTF-8") + "=" + URLEncoder.encode(ans, "UTF-8") + "&"
                        + URLEncoder.encode("f", "UTF-8") + "=" + URLEncoder.encode("open", "UTF-8");

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
        else if(type.equals("McqAnsAtmp")){
            try {
                String question_id = params[1];
                String user_id = params[2];
                String ans = params[3];
                String attempt_poll_URL = "http://10.0.2.2/ALec/public/api/V1/attemptsessionpoll.php?question_id="+question_id;
                URL url = new URL(attempt_poll_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("user_ID", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&"
                        + URLEncoder.encode("question_id", "UTF-8") + "=" + URLEncoder.encode(question_id, "UTF-8") + "&"
                        + URLEncoder.encode("ans", "UTF-8") + "=" + URLEncoder.encode(ans, "UTF-8") + "&"
                        + URLEncoder.encode("f", "UTF-8") + "=" + URLEncoder.encode("mcq", "UTF-8");

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
