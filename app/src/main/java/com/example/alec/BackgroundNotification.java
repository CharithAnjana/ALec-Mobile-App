package com.example.alec;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BackgroundNotification extends BroadcastReceiver {

    private static String[] nID;
    private static String[] nType;
    private static String[] subject;
    private static String[] description;
    private static String[] date;

    String vali = "";
    String notificationURL = "http://10.0.2.2/ALec/public/api/V1/getnotification.php";

    @Override
    public void onReceive(final Context context, Intent intent) {

        final Handler hnd = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        getUnreadNotificationsToday(context);
                    }
                } catch (Exception e) {
                    //
                } finally {
                    hnd.postDelayed(this, 3000);
                }
            }
        };

        hnd.post(runnable);
    }

    public void getUnreadNotificationsToday(Context context) {

        SessionManagement sessionManagement = new SessionManagement(context);
        String userID = sessionManagement.getSessionId();
        notificationURL = "http://10.0.2.2/ALec/public/api/V1/getnotification.php?user_Id=" + userID;

        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = null;
                    nID = new String[jsonArray.length()];
                    nType = new String[jsonArray.length()];
                    subject = new String[jsonArray.length()];
                    description = new String[jsonArray.length()];
                    date = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        nID[i] = jsonObject.getString("notification_id");
                        nType[i] = jsonObject.getString("notification_type");
                        subject[i] = jsonObject.getString("subject");
                        description[i] = jsonObject.getString("description");
                        date[i] = jsonObject.getString("date");

                        vali = Integer.toString(i);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, vali);
                        builder.setSmallIcon(R.drawable.alec_appicon);
                        builder.setContentTitle(description[i]);
                        builder.setContentText(subject[i]);
                        builder.setAutoCancel(true);
                        //builder.setOnlyAlertOnce(true);

                        Intent intent = new Intent(context, Notification.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(pendingIntent);

                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(1, builder.build());

                        BackgroundWorkerNotification backgroundWorkerNotification = new BackgroundWorkerNotification(context);
                        backgroundWorkerNotification.execute("MarkRead",userID, nID[i]);

                    }


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
        dbManager.execute(notificationURL);
    }
}
