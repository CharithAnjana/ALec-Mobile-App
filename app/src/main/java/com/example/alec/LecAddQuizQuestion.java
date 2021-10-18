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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LecAddQuizQuestion extends AppCompatActivity {

    TextView qID, cName, quizname;
    String quizID, quizName, courseName, qtCount;
    ListView quizListView;
    String questionURL = "http://10.0.2.2/ALec/public/api/V1/viewquizquestion.php";

    private static String[] qtNo;
    private static String[] qtID;
    private static String[] question;
    private static String[] chCount;

    private static String[] chName1;
    private static String[] chPoint1;
    private static String[] chName2;
    private static String[] chPoint2;
    private static String[] chName3;
    private static String[] chPoint3;
    private static String[] chName4;
    private static String[] chPoint4;
    private static String[] chName5;
    private static String[] chPoint5;

    private static String[] ap;
    private static String[] bp;
    private static String[] cp;
    private static String[] dp;
    private static String[] ep;

    private static String[] chN;
    private static String[] chP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_add_quiz_question);

        qID = findViewById(R.id.qID);
        cName = findViewById(R.id.cName);
        quizname = findViewById(R.id.quizName);
        quizListView = findViewById(R.id.quizList);

        Intent intent =getIntent();
        quizID = intent.getStringExtra("qID");
        courseName = intent.getStringExtra("cName");
        quizName = intent.getStringExtra("qName");
        qID.setText(quizID);
        cName.setText(courseName);
        quizname.setText(quizName);

        questionURL = "http://10.0.2.2/ALec/public/api/V1/viewquizquestion.php?quiz_ID="+quizID;
        fetch_data_into_array(quizListView);
    }

    public void Back(View view){
        finish();
    }
    public void Mcq(View view){
        Intent LecAddQuizMcq = new Intent(this,LecAddQuizMcq.class);
        LecAddQuizMcq.putExtra("quizID",quizID);
        LecAddQuizMcq.putExtra("cName",courseName);
        LecAddQuizMcq.putExtra("quizName",quizName);
        startActivity(LecAddQuizMcq);
    }
    public void ShortAns(View view){
        Intent LecAddQuizShort = new Intent(this,LecAddQuizShort.class);
        LecAddQuizShort.putExtra("quizID",quizID);
        LecAddQuizShort.putExtra("cName",courseName);
        LecAddQuizShort.putExtra("quizName",quizName);
        startActivity(LecAddQuizShort);
    }
    public void Submit(View view){
        if(ValidateQuiz(quizListView)){
            String quiz_id = quizID;
            String type= "Create";

            BackgroundWorkerQuiz backgroundWorkerQuiz = new BackgroundWorkerQuiz(this);
            String result;
            try {
                result = backgroundWorkerQuiz.execute(type, quiz_id).get();

                if(result.equals("Success")){

                    Intent LecQuizScheduleOption = new Intent(this, LecQuizScheduleOption.class);
                    LecQuizScheduleOption.putExtra("qID",quizID);
                    LecQuizScheduleOption.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(LecQuizScheduleOption);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public Boolean ValidateQuiz(ListView listView){
        int c = listView.getAdapter().getCount();
        if(c>0){
            return true;
        }
        else {
            Toast.makeText(this, "Can't create an empty quiz!", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    //load quiz question to the list view
    private void fetch_data_into_array(ListView topicListView) {
        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;

                    jsonObject = jsonArray.getJSONObject(0);
                    qtCount = jsonObject.getString("count");
                    int c = Integer.parseInt(qtCount);

                    qtNo = new String[c];
                    qtID = new String[c];
                    question = new String[c];
                    chCount = new String[c];
                    chName1 = new String[c];
                    chPoint1 = new String[c];
                    chName2 = new String[c];
                    chPoint2 = new String[c];
                    chName3 = new String[c];
                    chPoint3 = new String[c];
                    chName4 = new String[c];
                    chPoint4 = new String[c];
                    chName5 = new String[c];
                    chPoint5 = new String[c];

                    ap = new String[c];
                    bp = new String[c];
                    cp = new String[c];
                    dp = new String[c];
                    ep = new String[c];

                    chN = new String[jsonArray.length()];
                    chP = new String[jsonArray.length()];


                    int x = 0;
                    for (int i=1; i <= c; i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        qtNo[x] = Integer.toString(i);
                        qtID[x] = jsonObject.getString("id");
                        question[x] = jsonObject.getString("question");
                        chCount[x] = jsonObject.getString("count");
                        x++;
                    }

                    int qu = 0, chi = 0 , y, s = c+1, p = 0;
                    for (int j = 0; j < c; j++){
                        qu = Integer.parseInt(chCount[j]);
                        y = 0;
                        for (int i = 0; i < qu; i++) {
                            jsonObject = jsonArray.getJSONObject(s);
                            chN[chi] = jsonObject.getString("question");
                            chP[chi] = jsonObject.getString("count");
                            if(y == 0){
                                chName1[p] = chN[chi];
                                chPoint1[p] = chP[chi];
                                ap[p] = "a.";
                            }
                            if(y == 1){
                                chName2[p] = chN[chi];
                                chPoint2[p] = chP[chi];
                                bp[p] = "b.";
                            }
                            if(y == 2){
                                chName3[p] = chN[chi];
                                chPoint3[p] = chP[chi];
                                cp[p] = "c.";
                            }
                            if(y == 3){
                                chName4[p] = chN[chi];
                                chPoint4[p] = chP[chi];
                                dp[p] = "d.";
                            }
                            if(y == 4){
                                chName5[p] = chN[chi];
                                chPoint5[p] = chP[chi];
                                ep[p] = "e.";
                            }
                            y++;
                            chi++;
                            s++;
                        }
                        p++;
                    }


                    MyAdepter myAdepter = new MyAdepter(getApplicationContext(),qtNo,qtID,question,
                            chName1,chName2,chName3,chName4,chName5,
                            ap,bp,cp,dp,ep,
                            chPoint1,chPoint2,chPoint3,chPoint4,chPoint5);
                    topicListView.setAdapter(myAdepter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    //get question
                    URL urlQ = new URL(strings[0]);
                    HttpURLConnection connQ = (HttpURLConnection)urlQ.openConnection();
                    BufferedInputStream bufferedInputStreamQ = new BufferedInputStream(connQ.getInputStream());

                    BufferedReader bufferedReaderQ = new BufferedReader(new InputStreamReader(bufferedInputStreamQ));
                    StringBuffer sbQ = new StringBuffer();
                    String lineQ = null;
                    String resultQ = null;
                    while ((lineQ = bufferedReaderQ.readLine()) != null){
                        sbQ.append(lineQ+"\n");
                    }
                    bufferedInputStreamQ.close();
                    resultQ = sbQ.toString();

                    return resultQ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        dbManager dbManager = new dbManager();
        dbManager.execute(questionURL);

    }


    class MyAdepter extends ArrayAdapter<String> {

        Context context;
        String[] qtNo;
        String[] qtID;
        String[] question;

        String[] chName1;
        String[] chName2;
        String[] chName3;
        String[] chName4;
        String[] chName5;

        String[] ap;
        String[] bp;
        String[] cp;
        String[] dp;
        String[] ep;

        String[] chPoint1;
        String[] chPoint2;
        String[] chPoint3;
        String[] chPoint4;
        String[] chPoint5;

        MyAdepter(Context context, String[] qtNo, String[] qtID, String[] question,
                  String[] chName1, String[] chName2, String[] chName3, String[] chName4, String[] chName5,
                  String[] a, String[] b, String[] c, String[] d, String[] e,
                  String[] chPoint1, String[] chPoint2, String[] chPoint3, String[] chPoint4, String[] chPoint5) {

            super(context, R.layout.layout_question_view,R.id.tvTI,qtID);
            this.context = context;
            this.qtID = qtID;
            this.qtNo = qtNo;
            this.question = question;

            this.chName1 = chName1;
            this.chName2 = chName2;
            this.chName3 = chName3;
            this.chName4 = chName4;
            this.chName5 = chName5;

            this.ap = a;
            this.bp = b;
            this.cp = c;
            this.dp = d;
            this.ep = e;

            this.chPoint1 = chPoint1;
            this.chPoint2 = chPoint2;
            this.chPoint3 = chPoint3;
            this.chPoint4 = chPoint4;
            this.chPoint5 = chPoint5;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.layout_question_view,parent,false);;

            TextView QI = row.findViewById(R.id.tvQI);
            TextView QN = row.findViewById(R.id.tvQN);

            TextView C1 = row.findViewById(R.id.tvC1);
            TextView C2 = row.findViewById(R.id.tvC2);
            TextView C3 = row.findViewById(R.id.tvC3);
            TextView C4 = row.findViewById(R.id.tvC4);
            TextView C5 = row.findViewById(R.id.tvC5);
            TextView C1P = row.findViewById(R.id.chm1);
            TextView C2P = row.findViewById(R.id.chm2);
            TextView C3P = row.findViewById(R.id.chm3);
            TextView C4P = row.findViewById(R.id.chm4);
            TextView C5P = row.findViewById(R.id.chm5);

            TextView N = row.findViewById(R.id.qnum);
            TextView a = row.findViewById(R.id.a);
            TextView b = row.findViewById(R.id.b);
            TextView c = row.findViewById(R.id.c);
            TextView d = row.findViewById(R.id.d);
            TextView e = row.findViewById(R.id.e);

            QI.setText(qtID[position]);
            QN.setText(question[position]);
            N.setText(qtNo[position]);

            C1.setText(chName1[position]);
            C2.setText(chName2[position]);
            C3.setText(chName3[position]);
            C4.setText(chName4[position]);
            C5.setText(chName5[position]);

            a.setText(ap[position]);
            b.setText(bp[position]);
            c.setText(cp[position]);
            d.setText(dp[position]);
            e.setText(ep[position]);

            C1P.setText(chPoint1[position]);
            C2P.setText(chPoint2[position]);
            C3P.setText(chPoint3[position]);
            C4P.setText(chPoint4[position]);
            C5P.setText(chPoint5[position]);

            return row;
        }
    }
}