package com.example.kks.info.alarm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.kks.MainActivity;
import com.example.kks.R;


public class AlarmBroadcastReceiver extends BroadcastReceiver {

    NotificationManager manager;
    NotificationCompat.Builder builder;

    private static int NOTIFICATION_ID = 1;
    private static String CHANNEL_ID = "Notification_Channel";
    private static String CHANNEL_NAME = "Channel_Name";

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        builder = null;
        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.CYAN);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("채널 상세정보");

            manager.createNotificationChannel(notificationChannel);

            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        //알림창 클릭 시 activity 화면 부름
        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID,intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setSmallIcon(R.drawable.calendarimage);
        builder.setContentTitle("컬쳐 레코드");
        builder.setContentText("오늘의 문화를 기록하세요");
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        manager.notify(NOTIFICATION_ID,builder.build());
    }
}
