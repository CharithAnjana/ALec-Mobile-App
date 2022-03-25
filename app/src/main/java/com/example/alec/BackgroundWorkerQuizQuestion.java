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

public class BackgroundWorkerQuizQuestion extends AsyncTask<String, Void, String> {

    AlertDialog alertDialog;
    Context context;

    public BackgroundWorkerQuizQuestion(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if (type.equals("NewQuestShort")) {
            try {
                String quiz_ID = params[1];
                String question = params[2];
                String question_type = params[3];
                String answer = params[4];
                String point = params[5];
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

        } else if (type.equals("NewQuestMcq")) {
            try {
                String quiz_ID = params[1];
                String question = params[2];
                String question_type = params[3];
                String ans1 = params[4];
                String ans2 = params[5];
                String ans3 = params[6];
                String ans4 = params[7];
                String ans5 = params[8];
                String pnt1 = params[9];
                String pnt2 = params[10];
                String pnt3 = params[11];
                String pnt4 = params[12];
                String pnt5 = params[13];
                String ans_count = params[14];
                String edit_topic_URL = "http://10.0.2.2/ALec/public/api/V1/addnewquizquestionmcq.php";

                URL url = new URL(edit_topic_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("quiz_ID", "UTF-8") + "=" + URLEncoder.encode(quiz_ID, "UTF-8") + "&"
                        + URLEncoder.encode("question", "UTF-8") + "=" + URLEncoder.encode(question, "UTF-8") + "&"
                        + URLEncoder.encode("question_type", "UTF-8") + "=" + URLEncoder.encode(question_type, "UTF-8") + "&"
                        + URLEncoder.encode("ans1", "UTF-8") + "=" + URLEncoder.encode(ans1, "UTF-8") + "&"
                        + URLEncoder.encode("ans2", "UTF-8") + "=" + URLEncoder.encode(ans2, "UTF-8") + "&"
                        + URLEncoder.encode("ans3", "UTF-8") + "=" + URLEncoder.encode(ans3, "UTF-8") + "&"
                        + URLEncoder.encode("ans4", "UTF-8") + "=" + URLEncoder.encode(ans4, "UTF-8") + "&"
                        + URLEncoder.encode("ans5", "UTF-8") + "=" + URLEncoder.encode(ans5, "UTF-8") + "&"
                        + URLEncoder.encode("pnt1", "UTF-8") + "=" + URLEncoder.encode(pnt1, "UTF-8") + "&"
                        + URLEncoder.encode("pnt2", "UTF-8") + "=" + URLEncoder.encode(pnt2, "UTF-8") + "&"
                        + URLEncoder.encode("pnt3", "UTF-8") + "=" + URLEncoder.encode(pnt3, "UTF-8") + "&"
                        + URLEncoder.encode("pnt4", "UTF-8") + "=" + URLEncoder.encode(pnt4, "UTF-8") + "&"
                        + URLEncoder.encode("pnt5", "UTF-8") + "=" + URLEncoder.encode(pnt5, "UTF-8") + "&"
                        + URLEncoder.encode("ans_count", "UTF-8") + "=" + URLEncoder.encode(ans_count, "UTF-8");

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
        } else if (type.equals("EditQuestShort")) {
            try {
                String quiz_ID = params[1];
                String question = params[2];
                String question_type = params[3];
                String answer = params[4];
                String point = params[5];
                String new_question_URL = "http://10.0.2.2/ALec/public/api/V1/editquizshort.php";

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

        } else if (type.equals("EditQuestMcq")) {
            try {
                String quiz_ID = params[1];
                String question_ID = params[2];
                String question = params[3];
                String question_type = params[4];
                String ans1 = params[5];
                String ans2 = params[6];
                String ans3 = params[7];
                String ans4 = params[8];
                String ans5 = params[9];
                String pnt1 = params[10];
                String pnt2 = params[11];
                String pnt3 = params[12];
                String pnt4 = params[13];
                String pnt5 = params[14];
                String ans_count = params[15];
                String edit_topic_URL = "http://10.0.2.2/ALec/public/api/V1/editquizmcq.php";

                URL url = new URL(edit_topic_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("quiz_ID", "UTF-8") + "=" + URLEncoder.encode(quiz_ID, "UTF-8") + "&"
                        + URLEncoder.encode("question", "UTF-8") + "=" + URLEncoder.encode(question, "UTF-8") + "&"
                        + URLEncoder.encode("question_no", "UTF-8") + "=" + URLEncoder.encode(question_ID, "UTF-8") + "&"
                        + URLEncoder.encode("question_type", "UTF-8") + "=" + URLEncoder.encode(question_type, "UTF-8") + "&"
                        + URLEncoder.encode("ans1", "UTF-8") + "=" + URLEncoder.encode(ans1, "UTF-8") + "&"
                        + URLEncoder.encode("ans2", "UTF-8") + "=" + URLEncoder.encode(ans2, "UTF-8") + "&"
                        + URLEncoder.encode("ans3", "UTF-8") + "=" + URLEncoder.encode(ans3, "UTF-8") + "&"
                        + URLEncoder.encode("ans4", "UTF-8") + "=" + URLEncoder.encode(ans4, "UTF-8") + "&"
                        + URLEncoder.encode("ans5", "UTF-8") + "=" + URLEncoder.encode(ans5, "UTF-8") + "&"
                        + URLEncoder.encode("pnt1", "UTF-8") + "=" + URLEncoder.encode(pnt1, "UTF-8") + "&"
                        + URLEncoder.encode("pnt2", "UTF-8") + "=" + URLEncoder.encode(pnt2, "UTF-8") + "&"
                        + URLEncoder.encode("pnt3", "UTF-8") + "=" + URLEncoder.encode(pnt3, "UTF-8") + "&"
                        + URLEncoder.encode("pnt4", "UTF-8") + "=" + URLEncoder.encode(pnt4, "UTF-8") + "&"
                        + URLEncoder.encode("pnt5", "UTF-8") + "=" + URLEncoder.encode(pnt5, "UTF-8") + "&"
                        + URLEncoder.encode("ans_count", "UTF-8") + "=" + URLEncoder.encode(ans_count, "UTF-8");

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
