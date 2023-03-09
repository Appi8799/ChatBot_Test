package com.ap.bot.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;

import com.ap.bot.MainActivity;
import com.ap.bot.R;
import com.ap.bot.Service.ChatBotService;

import java.util.Date;


@RequiresApi(Build.VERSION_CODES.O)
public class Notifications {
    private final Context context;
    private final NotificationManager notificationManager;
    public static String channel = "chatbotchannel_1";

    public Notifications(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationManager = notificationManager;
    }

    public void notificationChannel(NotificationManager notificationManager,String channelId){
        NotificationChannel notificationChannel = new NotificationChannel( "chatbotchannel_1","chatbot channel", NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationManager.createNotificationChannel(notificationChannel);
    }

        public void sendNotification(String title, String content, String date)
        {
            notificationChannel(notificationManager,channel);
            RemoteViews noti = new RemoteViews("com.ap.bot",R.layout.notif_layout);
            noti.setTextViewText(R.id.noti_date,date);
            noti.setTextViewText(R.id.noti_message,content);
            noti.setTextViewText(R.id.noti_title,title);


            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE);
            String headline =  String.format("%s  %s",title, date);

            try{
                Notification notify = new Notification.Builder(context.getApplicationContext())
                        .setSmallIcon(R.drawable.ic_baseline_keyboard_command_key_24)
                        .setCustomBigContentView(noti)
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setChannelId(channel)
                        .setStyle(new Notification.DecoratedCustomViewStyle())
                        .build();

                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(0,notify);
            }catch(IllegalArgumentException e)
            {
                Log.e("notificationException",e.getMessage());

        }



        }
        public void init(){
        notificationChannel(notificationManager,channel);

    }




    }

