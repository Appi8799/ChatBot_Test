package com.ap.bot.Service;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ap.bot.Constants;
import com.ap.bot.Notifications.Notifications;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChatBotService extends android.app.Service {

    private NotificationManager notificationManager;
    private PowerManager.WakeLock wakeLock;
    private Notifications notifications;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifications = new Notifications(this,notificationManager);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if(intent!=null){
            Bundle data = intent.getExtras();
            handleData(data);

        }
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        notificationManager.cancelAll();
        super.onDestroy();
    }

    private void handleData(Bundle data) {
        String messageDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm").format(Calendar.getInstance().getTime());
        int command = data.getInt("ChatBot");
        if(command == 10){
            // STOP BROADCAST
            String notify = " CHAT BOT STOPPED : 25";
            stopBroadcast(notify);
            notifications.sendNotification(getPackageName() ,notify, messageDateTime);
        }
        else if( command == 20){
            String name = data.getString("ChatBott");
            //START BROADCAST
            String messageOne = String.format("Hello Apeksha %s !",name);
            String messageTwo = String.format("How are you %s ?",name);
            String messageThree = String.format("Good Bye Apeksha %s !",name);
            sendBroadCast(name,messageOne);
            sendBroadCast(name,messageTwo);
            sendBroadCast(name,messageThree);

            notifications.sendNotification(name,String.format("%s \n  %s\n  %s",messageOne,messageTwo,messageThree),messageDateTime );

        }
        else
        {
            Log.d("Unknown Command","Unknown Command , System Error");
        }

    }

    private void stopBroadcast(String notify) {
        Log.d("New Broadcast Request","broadcasting new message :: ");
        Intent intent = new Intent();
        intent.setAction(Constants.BROADCAST_STOP);

        Bundle data = new Bundle();
        data.putString("BroadcastPackage",getPackageName());
        data.putString("BroadcastNotify",notify);
        intent.putExtras(data);
        sendBroadcast(intent);

    }

    private void sendBroadCast(String name, String message) {
        Log.d("New Broadcast Request","broadcasting new message :: ");
        Intent intent = new Intent();
        intent.setAction(Constants.BROADCAST_NEW_MESSAGE);
        Bundle data = new Bundle();
        data.putString("BroadcastUsername",name);
        data.putString("BroadcastMessage",message);
        intent.putExtras(data);
        sendBroadcast(intent);


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
