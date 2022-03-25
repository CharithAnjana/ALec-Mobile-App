package com.example.alec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserDeatails extends AppCompatActivity {

    String profileURL = "http://10.0.2.2/ALec/public/api/V1/getuserdetails.php";
    String user_ID,user_Type,e_mail ;
    ListView  profileListView;
    private static String[] user_id;
    private static String[] user_type;
    private static String[] email;
    private static String[] reg_no;
    private static String[] first_name;
    private static String[] last_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_deatails);

        SessionManagement sessionManagement = new SessionManagement(this);
         user_ID = sessionManagement.getSessionId();
         user_Type = sessionManagement.getSessionKey();
         //e_mail = sessionManagement.getSessionKey();




        profileListView = (ListView)findViewById(R.id.Userd);
        profileURL = "http://10.0.2.2/ALec/public/api/V1/getuserdetails.php?user_ID="+user_ID+"&user_Type="+user_Type;
        fetch_data_into_array(profileListView);

    }

    private void fetch_data_into_array(ListView topicListView) {
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    user_id = new String[jsonArray.length()];
                    user_type = new String[jsonArray.length()];
                    email = new String[jsonArray.length()];
                    reg_no = new String[jsonArray.length()];
                    first_name = new String[jsonArray.length()];
                    last_name = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        user_id[i] = jsonObject.getString("user_id");
                        user_type[i] = jsonObject.getString("user_type");
                        email[i] = jsonObject.getString("email");
                        reg_no[i] = jsonObject.getString("reg_no");
                        first_name[i] = jsonObject.getString("first_name");
                        last_name[i] = jsonObject.getString("last_name");


                    }

                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(), user_id, user_type, email, reg_no, first_name,last_name);
                    topicListView.setAdapter(myAdepter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                    StringBuffer sb = new StringBuffer();
                    String line = null;
                    String result = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    bufferedInputStream.close();
                    result = sb.toString();

                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        dbManager dbManager = new dbManager();
        dbManager.execute(profileURL);

    }

    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] user_id;
        String[] user_type;
        String[] email;
        String[] reg_no;
        String[] first_name;
        String[] last_name;



        MyAdepter(Context context, String[] user_id, String[] user_type, String[] email, String[] reg_no, String[] first_name,String[] last_name) {
            super(context, R.layout.layout_getuserdetails, R.id.tvUI, user_id);
            this.context = context;
            this.user_id = user_id;
            this.user_type = user_type;
            this.email = email;
            this.reg_no = reg_no;
            this.first_name = first_name;
            this.last_name=last_name;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_getuserdetails, parent, false);
            ;

            TextView tvUI = row.findViewById(R.id.tvUI);
            TextView tvUT = row.findViewById(R.id.tvUT);
            TextView tvEM = row.findViewById(R.id.tvEM);
            TextView tvRN = row.findViewById(R.id.tvRN);
            TextView tvFN = row.findViewById(R.id.tvFN);
            TextView tvLN = row.findViewById(R.id.tvLN);



            tvUI.setText(user_id[position]);
            tvUT.setText(user_type[position]);
            tvEM.setText(email[position]);
            tvRN.setText(reg_no[position]);
            tvFN.setText(first_name[position]);
            tvLN.setText(last_name[position]);



            return row;
        }

    }
    public void EditProfile(View view){
        Intent EditProfiledetails = new Intent(this, EditProfiledetails.class);
        EditProfiledetails.putExtra("email",email);
        EditProfiledetails.putExtra("user_ID",user_ID);
        EditProfiledetails.putExtra("user_Type",user_Type);
        EditProfiledetails.putExtra("reg_no",reg_no);
        EditProfiledetails.putExtra("first_name",first_name);
        EditProfiledetails.putExtra("last_name",last_name);



        startActivity(EditProfiledetails);
    }




    public void Back(View view){
        finish();
    }
}