package com.example.appmusicmp3.presentation.service;//package com.example.appmusicmp3.presentation.service;
//
//import android.app.Application;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {

    public static final String CHANNEL_ID = "CHANNEL_EXAMPLE";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"channel example",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(null,null);

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
