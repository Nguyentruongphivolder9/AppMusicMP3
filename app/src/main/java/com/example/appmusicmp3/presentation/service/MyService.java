package com.example.appmusicmp3.presentation.service;

import static com.example.appmusicmp3.presentation.service.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.models.BaiHat;
import com.example.appmusicmp3.presentation.fragment.DianhacFragment;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.Random;

public class MyService extends Service {

    private MediaPlayer mediaPlayer;
    private ArrayList<BaiHat> baiHats;
    private Looper serviceLooper;
    public ServiceHanler serviceHandler;
    private NotificationManager notificationManager;
    private boolean isPlaying = false;
    private int RESUME_MUSIC_CODE = 0;
    private int PAUSE_MUSIC_CODE = 1;
    private int CLEAR_MUSIC_CODE = 2;
    private int PRE_MUSIC_CODE = 3;
    private int NEXT_MUSIC_CODE = 4;

    private OnListenDuration onListenDuration;
    private long duration = 0;

    private int position = 0;

    boolean next = false;
    boolean repeat = false;
    boolean random = false;
    private boolean stopService = false;

    public class ServiceHanler extends Handler {
        DianhacFragment dianhacFragment;
        private int currentTime = 0;
        private Handler handler;
        String  tenbaihat,hinhbaihat,linkbaihat,casi;

        public ServiceHanler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            ArrayList<BaiHat> baiHats1 = (ArrayList<BaiHat>) bundle.get("baihatstart");

            if (baiHats1 != null) {
                baiHats = baiHats1;
            }
            if (baiHats1 != null){
                tenbaihat = baiHats1.get(0).getTenbaihat();
                casi = baiHats1.get(0).getCasi();
                hinhbaihat = baiHats1.get(0).getHinhbaihat();
                linkbaihat = baiHats1.get(0).getLinkbaihat();
            }

            Log.d("BBB","action" + msg.what);
            switch (msg.what) {
                case -1:
                    startMp3(baiHats,0);
                    break;
                case 0:
                    resumeMp3(baiHats);
                    break;
                case 1:
                    pauseMp3(baiHats);
                    break;
                case 2:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        stopForeground(STOP_FOREGROUND_REMOVE);
                        if (mediaPlayer != null) {
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                    }
                    break;
                case 3:
                    previewMp3(baiHats);
                    break;
                case 4:
                    nextMp3(baiHats);
                    break;
            }
        }
        public void setFalseRepeat(){
            repeat = false;
        }
        public void setTrueRepeat(){
            repeat = true;
        }
        public void setFalseRandom(){
            random = false;
        }
        public void setTrueRandom(){
            random = true;
        }

        private void getUpdateCurrentTime(){
            if (mediaPlayer != null) {
                if (isPlaying) {
                    handler = new Handler();
                    handler.postDelayed(runnableTime, 500);
                }
            }
        }

        private Runnable runnableTime = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() < mediaPlayer.getDuration()) {
                    onListenDuration.onCurrentDuration(mediaPlayer.getCurrentPosition());
                }
                if (isPlaying) {
                    handler.postDelayed(this,500);
                }
            }
        };

        public void pauseMp3(ArrayList<BaiHat> baiHats) {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                currentTime = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
                isPlaying = false;
                notificationManager.notify(1, sendNotification(baiHats,position,isPlaying ));
            }
        }

        public void resumeMp3(ArrayList<BaiHat> baiHats) {
            if (mediaPlayer != null){
                isPlaying = true;
                mediaPlayer.seekTo(currentTime);
                mediaPlayer.start();
                AutoPlayNext(baiHats);
                notificationManager.notify(1, sendNotification(baiHats,position,isPlaying));
            }
        }

        public void previewMp3(ArrayList<BaiHat> baiHats) {
            if (baiHats.size() > 0) {
                if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if (position < baiHats.size()) {
                    position--;
                    if (position < 0) {
                        position = baiHats.size() - 1;
                    }
                    if (repeat == true) {
                        position += 1;
                    }
                    if (random == true) {
                        Random random = new Random();
                        int index = random.nextInt(baiHats.size());
                        if (index == position) {
                            position = index - 1;
                        }
                    }
                }
                serviceHandler.startMp3(baiHats,position);
                getUpdateCurrentTime();
            }
        }

        public void nextMp3(ArrayList<BaiHat> baiHats) {
            if (baiHats.size() > 0) {
                if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    if (position < baiHats.size()) {
                        position++;
                        if (repeat) {
                            if (position == 0) {
                                position = baiHats.size();
                            }
                            position -= 1;
                        }

                        if (random == true) {
                            Random random = new Random();
                            int index = random.nextInt(baiHats.size());
                            if (index == position) {
                                position = index - 1;
                            }
                        }
                        if (position > (baiHats.size() - 1)) {
                            position = 0;
                        }
                    }
                    serviceHandler.startMp3(baiHats,position);
                    getUpdateCurrentTime();
                }
            }
        }

        public void startMp3(ArrayList<BaiHat> baiHats, int position) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(baiHats.get(position).getLinkbaihat()));
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    notificationManager.notify(1, sendNotification(baiHats, position, isPlaying));
                    isPlaying = true;
                    mediaPlayer.start();
                    duration = mediaPlayer.getDuration();
                    AutoPlayNext(baiHats);
                    getUpdateCurrentTime();
                }
            });
        }
        private void AutoPlayNext(ArrayList<BaiHat> baiHats){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null){
                        handler.postDelayed(this,300);
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                next = true;
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            },300);
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (next == true){
                        if (position < baiHats.size()) {
                            position++;
                            if (repeat == true) {
                                if (position == 0) {
                                    position = baiHats.size();
                                }
                                position -= 1;
                            }
                            if (random == true) {
                                Random random = new Random();
                                int index = random.nextInt(baiHats.size());
                                if (index == position) {
                                    position = index - 1;
                                }
                            }
                            if (position > (baiHats.size() - 1) ){
                                position = 0;
                            }
                            serviceHandler.startMp3(baiHats,0);
                        }
                        next = false;
                        handler1.removeCallbacks(this);
                    }else {
                        handler1.postDelayed(this, 1000);
                    }
                }
            },1000);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("BBB"," onBind");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("BBB"," onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        stopService = false;
        Log.d("BBB"," onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
//        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUN);
        thread.start();
        Log.d("BBB"," onCreate");
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHanler(serviceLooper);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BBB"," onStartCommand");
        Bundle bundle = intent.getExtras();
        ArrayList<BaiHat> baiHatArrayList = (ArrayList<BaiHat>) bundle.getSerializable("baihatservice");
        int requestcode = intent.getIntExtra("requestcode", -1);
        Message msg = serviceHandler.obtainMessage();
        stopService = false;

        switch (requestcode) {
            case -1:
                bundle.putSerializable("baihatstart", baiHatArrayList);
                msg.what = -1;
                msg.setData(bundle);
                serviceHandler.sendMessage(msg);
                break;
            case 0:
                bundle.putSerializable("baihatstart", baiHatArrayList);
                msg.setData(bundle);
                msg.what = 0;
                serviceHandler.sendMessage(msg);
                break;
            case 1:
                bundle.putSerializable("baihatstart", baiHatArrayList);
                msg.setData(bundle);
                msg.what = 1;
                serviceHandler.sendMessage(msg);
                break;
            case 2:
                msg.setData(bundle);
                msg.what = 2;
                serviceHandler.sendMessage(msg);
                break;
            case 3:
                msg.setData(bundle);
                msg.what = 3;
                serviceHandler.sendMessage(msg);
                break;
            case 4:
                msg.setData(bundle);
                msg.what = 4;
                serviceHandler.sendMessage(msg);
                break;
        }
        return START_NOT_STICKY;
    }

    public Notification sendNotification(ArrayList<BaiHat> baiHats, int position, boolean isPlaying) {
        Intent intent = new Intent(this, MyService.class);

        int action = -1;
        intent.putExtra("requestcode", action);

        PendingIntent pendingIntent = PendingIntent.getService(this
                , 0
                , intent
                , PendingIntent.FLAG_UPDATE_CURRENT);


        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.foreground_service);
        remoteViews.setTextViewText(R.id.textviewTitleSong, baiHats.get(position).getTenbaihat());
        remoteViews.setTextViewText(R.id.textviewSingleSong, baiHats.get(position).getCasi());


        remoteViews.setImageViewResource(R.id.imageviewPauseSong, R.drawable.pause_button);
        remoteViews.setImageViewResource(R.id.imageviewCloseSong, R.drawable.close);
        if (isPlaying){
            remoteViews.setOnClickPendingIntent(R.id.imageviewPauseSong,getPendingIntent(this,PAUSE_MUSIC_CODE));
            remoteViews.setImageViewResource(R.id.imageviewPauseSong,R.drawable.pause_button);
        }else {
            remoteViews.setOnClickPendingIntent(R.id.imageviewPauseSong,getPendingIntent(this,RESUME_MUSIC_CODE));
            remoteViews.setImageViewResource(R.id.imageviewPauseSong,R.drawable.iconplay);
        }
        remoteViews.setOnClickPendingIntent(R.id.imageviewCloseSong,getPendingIntent(this,CLEAR_MUSIC_CODE));
        remoteViews.setOnClickPendingIntent(R.id.imageviewPreviewSong,getPendingIntent(this,PRE_MUSIC_CODE));
        remoteViews.setOnClickPendingIntent(R.id.imageviewNextSong,getPendingIntent(this,NEXT_MUSIC_CODE));


        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews);

        startForeground(1, notification.build());
        return notification.build();
    }
    private PendingIntent getPendingIntent(Context context, int action){
        Intent intent = new Intent(context, MyService.class);
        intent.putExtra("requestcode", action);
        return PendingIntent.getService(context, action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    @Override
    public void onDestroy() {
        Log.d("BBB"," onDestroy");
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }


    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    public void setOnListenDuration(OnListenDuration onListenDuration) {
        this.onListenDuration = onListenDuration;
    }

    public interface OnListenDuration {
        void onCurrentDuration(long time);

    }

    public long getDuration() {
        return duration;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }


    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isNext() {
        return next;
    }

    public boolean isRandom() {
        return random;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isStopService() {
        return stopService;
    }

    public int getPosition() {
        return position;
    }

}

