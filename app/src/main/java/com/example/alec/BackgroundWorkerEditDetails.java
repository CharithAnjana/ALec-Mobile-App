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

public class BackgroundWorkerEditDetails extends AsyncTask<String, Void, String> {

    AlertDialog alertDialog;
    Context context;
    public BackgroundWorkerEditDetails(Context ctx){
        context = ctx;
    }


    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        if(type.equals("Editemail")) {
            try {


                String firstname = params[1] ;
                String lastname = params[2];
                String regno = params[3];
                String user_ID = params[4];
               String user_Type = params[5];
               // String Email = params[6];




                String edit_topic_URL = "http://10.0.2.2/ALec/public/api/V1/edituserdetails.php";

                URL url = new URL(edit_topic_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("fName", "UTF-8") + "=" + URLEncoder.encode(firstname, "UTF-8")+ "&"
                        + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode(lastname, "UTF-8") + "&"
                        + URLEncoder.encode("regNo", "UTF-8") + "=" + URLEncoder.encode(regno, "UTF-8")+ "&"
                        + URLEncoder.encode("user_ID", "UTF-8") + "=" + URLEncoder.encode(user_ID, "UTF-8")
                        + "&"
                        + URLEncoder.encode("user_Type", "UTF-8") + "=" + URLEncoder.encode(user_Type, "UTF-8");
                   //   + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8")  ;


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

