package com.example.appmusicmp3.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.models.BaiHat;
import com.example.appmusicmp3.presentation.adapter.ViewPagerPlaylistnhacAdapter;
import com.example.appmusicmp3.presentation.fragment.DianhacFragment;
import com.example.appmusicmp3.presentation.fragment.PlaydanhbaihatFragment;
import com.example.appmusicmp3.presentation.service.MyService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

//package com.example.appmusicmp3.Activity;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.viewpager.widget.ViewPager;
//
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.graphics.Color;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.StrictMode;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.example.appmusicmp3.R;
//import com.example.appmusicmp3.data.models.BaiHat;
//import com.example.appmusicmp3.presentation.adapter.ViewPagerPlaylistnhacAdapter;
//import com.example.appmusicmp3.presentation.fragment.DianhacFragment;
//import com.example.appmusicmp3.presentation.fragment.PlaydanhbaihatFragment;
//import com.example.appmusicmp3.presentation.service.MyService;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//
public class PlayNhacActivity extends AppCompatActivity {

    Toolbar toolbarPlaynhac;
    TextView tvTimesong, tvTotalTimesong;
    SeekBar sktime;
    ImageView imgPlay, imgRepeat, imgNext, imgPreview, imgRandom;
    ViewPager viewPagerPlaynhac;
    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();
    public static ViewPagerPlaylistnhacAdapter adapternhac;
    DianhacFragment dianhacFragment;
    PlaydanhbaihatFragment playdanhbaihatFragment;
    MediaPlayer mediaPlayer = new MediaPlayer();
    int currentTime = 0;
    private MyService myService;
    boolean isServiceConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getData();
        init();
        eventClick();

        Handler handler11 = new Handler();
        handler11.postDelayed(new Runnable() {
            @Override
            public void run() {
                TimeSong((int) myService.getDuration());
                setStatusIButPlay();
                StopService();
                getSupportActionBar().setTitle(mangbaihat.get(myService.getPosition()).getTenbaihat());
                dianhacFragment.PlayNhac(mangbaihat.get(myService.getPosition()).getHinhbaihat());
                UpdateTimeDemo(currentTime);
                handler11.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void eventClick() {
        final Handler handlerStart = new Handler();
        handlerStart.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mangbaihat.size() > 0) {
                    Intent intent = new Intent(PlayNhacActivity.this, MyService.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("baihatservice", mangbaihat);
                    intent.putExtras(bundle);
                    startService(intent);
                    bindService(intent, connection, Context.BIND_AUTO_CREATE);

                    getSupportActionBar().setTitle(mangbaihat.get(0).getTenbaihat());
                    dianhacFragment.PlayNhac(mangbaihat.get(0).getHinhbaihat());
                    handlerStart.removeCallbacks(this);
                } else {
                    handlerStart.postDelayed(this, 200);
                }
            }
        }, 500);

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myService.isPlaying()) {
                    myService.serviceHandler.pauseMp3(mangbaihat);
                }else {
                    myService.serviceHandler.resumeMp3(mangbaihat);
                }
                setStatusIButPlay();
            }
        });
        imgRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myService.isRandom() == false){
                    if (myService.isRepeat() == true){
                        myService.serviceHandler.setFalseRepeat();
                    }
                    myService.serviceHandler.setTrueRandom();
                    imgRandom.setImageResource(R.drawable.iconshuffled);
                    imgRepeat.setImageResource(R.drawable.iconrepeat);
                }else {
                    myService.serviceHandler.setFalseRandom();
                    imgRandom.setImageResource(R.drawable.iconsuffle);
                }
            }
        });
        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (myService.isRepeat() == false){
                    if (myService.isRandom() == true){
                        myService.serviceHandler.setFalseRandom();
                    }
                    myService.serviceHandler.setTrueRepeat();
                    imgRepeat.setImageResource(R.drawable.iconsyned);
                    imgRandom.setImageResource(R.drawable.iconsuffle);
                }else {
                    myService.serviceHandler.setFalseRepeat();
                    imgRepeat.setImageResource(R.drawable.iconrepeat);
                }
            }
        });
        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myService.getMediaPlayer().seekTo(seekBar.getProgress());
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myService.getMediaPlayer() != null) {
                    myService.serviceHandler.nextMp3(mangbaihat);
                }
                dianhacFragment.PlayNhac(mangbaihat.get(myService.getPosition()).getHinhbaihat());
                getSupportActionBar().setTitle(mangbaihat.get(myService.getPosition()).getTenbaihat());
                imgPreview.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPreview.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 3000);
            }
        });
        imgPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myService.getMediaPlayer() != null){
                    myService.serviceHandler.previewMp3(mangbaihat);
                }
                dianhacFragment.PlayNhac(mangbaihat.get(myService.getPosition()).getHinhbaihat());
                getSupportActionBar().setTitle(mangbaihat.get(myService.getPosition()).getTenbaihat());
                imgPreview.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPreview.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 3000);
            }
        });
    }

    private void getData() {
        mangbaihat.clear();
        Intent intent = getIntent();
        if (intent != null) {
            mangbaihat.clear();
            if (intent.hasExtra("cakhuc")) {
                BaiHat baiHat = intent.getParcelableExtra("cakhuc");
                mangbaihat.add(baiHat);
            }
            if (intent.hasExtra("cacbaihat")) {
                ArrayList<BaiHat> baiHatArrayList = intent.getParcelableArrayListExtra("cacbaihat");
                mangbaihat = baiHatArrayList;
            }
        }
    }

    private void init() {
        toolbarPlaynhac = findViewById(R.id.toolbarPlaynhac);
        tvTimesong = findViewById(R.id.textviewTimesong);
        tvTotalTimesong = findViewById(R.id.textviewTotalimesong);
        sktime = findViewById(R.id.seekbarSong);
        imgPlay = findViewById(R.id.imagebuttonPlay);
        imgNext = findViewById(R.id.imagebuttonNext);
        imgPreview = findViewById(R.id.imagebuttonPreview);
        imgRandom = findViewById(R.id.imagebuttonSuffle);
        imgRepeat = findViewById(R.id.imagebuttonRepeat);
        viewPagerPlaynhac = findViewById(R.id.viewpagerPlaynhac);
        setSupportActionBar(toolbarPlaynhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPlaynhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mangbaihat.clear();
                finish();
            }
        });
        toolbarPlaynhac.setTitleTextColor(Color.WHITE);
        dianhacFragment = new DianhacFragment();
        playdanhbaihatFragment = new PlaydanhbaihatFragment();
        adapternhac = new ViewPagerPlaylistnhacAdapter(getSupportFragmentManager());
        adapternhac.AddFragment(playdanhbaihatFragment);
        adapternhac.AddFragment(dianhacFragment);
        viewPagerPlaynhac.setAdapter(adapternhac);
    }

    private void TimeSong(int duration) {
        Log.d("BBB","duration Timesong " + duration);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tvTotalTimesong.setText(simpleDateFormat.format(duration));
        sktime.setMax(duration);
    }

    private void UpdateTimeDemo(int timePlay){
        sktime.setProgress(currentTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tvTimesong.setText(simpleDateFormat.format(currentTime));
    }

    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder sevice) {
            MyService.MyBinder myBinder = (MyService.MyBinder) sevice;
            myService = myBinder.getService();
            isServiceConnected = true;
            myService.setOnListenDuration(new MyService.OnListenDuration() {
                @Override
                public void onCurrentDuration(long time) {
                    currentTime = (int) time;
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myService = null;
            isServiceConnected = true;
        }
    };

    @Override
    protected void onDestroy() {
        StopService();
        super.onDestroy();
    }

    private void setStatusIButPlay(){
        if (myService.isPlaying()){
            imgPlay.setImageResource(R.drawable.pause_button);
        } else {
            imgPlay.setImageResource(R.drawable.iconplay);
        }
    }
    private void StopService(){
        if (myService.isStopService()){
            myService.stopSelf();
        }
    }


}
//public class PlayNhacActivity extends AppCompatActivity {
//
//    Toolbar toolbarPlaynhac;
//    TextView tvTimesong, tvTotalTimesong;
//    SeekBar sktime;
//    ImageView imgPlay, imgRepeat, imgNext, imgPreview, imgRandom;
//    ViewPager viewPagerPlaynhac;
//    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();
//    public static ViewPagerPlaylistnhacAdapter adapternhac;
//    DianhacFragment dianhacFragment;
//    PlaydanhbaihatFragment playdanhbaihatFragment;
//    MediaPlayer mediaPlayer = new MediaPlayer();
//    int position = 0;
//    boolean repeat = false;
//    boolean checkrandom = false;
//    boolean next = false;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_play_nhac);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        getData();
//        init();
//        eventClick();
//    }
//
//    private void eventClick() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (adapternhac.getItem(1) != null) {
//                    if (mangbaihat.size() > 0) {
//                        dianhacFragment.PlayNhac(mangbaihat.get(0).getHinhbaihat());
//                        imgPlay.performClick();
//                        handler.removeCallbacks(this);
//                    }else {
//                        handler.postDelayed(this, 100);
//                    }
//                }
//            }
//        },500);
//        imgPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.pause();
//                    imgPlay.setImageResource(R.drawable.iconplay);
//                }else {
//                    mediaPlayer.start();
//                    imgPlay.setImageResource(R.drawable.pause_button);
//                }
//            }
//        });
//        imgRandom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkrandom == false){
//                    if (repeat == true){
//                        repeat = false;
//                        imgRandom.setImageResource(R.drawable.iconshuffled);
//                        imgRepeat.setImageResource(R.drawable.iconrepeat);
//                    }
//                    imgRandom.setImageResource(R.drawable.iconshuffled);
//                    checkrandom = true;
//                }else {
//                    imgRandom.setImageResource(R.drawable.iconsuffle);
//                    checkrandom = false;
//                }
//            }
//        });
//        imgRepeat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (repeat == false){
//                    if (checkrandom == true){
//                        checkrandom = false;
//                        imgRepeat.setImageResource(R.drawable.iconsyned);
//                        imgRandom.setImageResource(R.drawable.iconsuffle);
//                    }
//                    imgRepeat.setImageResource(R.drawable.iconsyned);
//                    repeat = true;
//                }else {
//                    imgRepeat.setImageResource(R.drawable.iconrepeat);
//                    repeat = false;
//                }
//            }
//        });
//        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                mediaPlayer.seekTo(seekBar.getProgress());
//            }
//        });
//        imgNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mangbaihat.size() > 0) {
//                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
//                        mediaPlayer = null;
//                    }
//                    if (position < mangbaihat.size()) {
//                        imgPlay.setImageResource(R.drawable.pause_button);
//                        position++;
//                        if (repeat == true) {
//                            if (position == 0) {
//                                position = mangbaihat.size();
//                            }
//                            position -= 1;
//                        }
//
//                        if (checkrandom == true) {
//                            Random random = new Random();
//                            int index = random.nextInt(mangbaihat.size());
//                            if (index == position) {
//                                position = index - 1;
//                            }
//                        }
//                        if (position > (mangbaihat.size() - 1)){
//                            position = 0;
//                        }
//                        new PlayMp3().execute(mangbaihat.get(position).getLinkbaihat());
//                        dianhacFragment.PlayNhac(mangbaihat.get(position).getHinhbaihat());
//                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
//                        UpdateTime();
//                    }
//                }
//                imgPreview.setClickable(false);
//                imgNext.setClickable(false);
//                Handler handler1 = new Handler();
//                handler1.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        imgPreview.setClickable(true);
//                        imgNext.setClickable(true);
//                    }
//                },2000);
//            }
//        });
//        imgPreview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mangbaihat.size() > 0) {
//                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
//                        mediaPlayer = null;
//                    }
//                    if (position < mangbaihat.size()) {
//                        imgPlay.setImageResource(R.drawable.pause_button);
//                        position--;
//                        if (position < 0){
//                            position = mangbaihat.size() - 1;
//                        }
//                        if (repeat == true) {
//                            position += 1;
//                        }
//                        if (checkrandom == true) {
//                            Random random = new Random();
//                            int index = random.nextInt(mangbaihat.size());
//                            if (index == index) {
//                                position = index - 1;
//                            }
//                        }
//                        new PlayMp3().execute(mangbaihat.get(position).getLinkbaihat());
//                        dianhacFragment.PlayNhac(mangbaihat.get(position).getHinhbaihat());
//                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
//                        UpdateTime();
//                    }
//                }
//                imgPreview.setClickable(false);
//                imgNext.setClickable(false);
//                Handler handler1 = new Handler();
//                handler1.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        imgPreview.setClickable(true);
//                        imgNext.setClickable(true);
//                    }
//                },2000);
//            }
//        });
//    }
//
//    private void getData() {
//        Intent intent = getIntent();
//        mangbaihat.clear();
//        if (intent != null) {
//            if (intent.hasExtra("cakhuc")) {
//                BaiHat baiHat = intent.getParcelableExtra("cakhuc");
//                mangbaihat.add(baiHat);
//            }
//            if (intent.hasExtra("cacbaihat")) {
//                ArrayList<BaiHat> baiHatArrayList = intent.getParcelableArrayListExtra("cacbaihat");
//                mangbaihat = baiHatArrayList;
//            }
//        }
//    }
//
//    private void init(){
//        toolbarPlaynhac = findViewById(R.id.toolbarPlaynhac);
//        tvTimesong = findViewById(R.id.textviewTimesong);
//        tvTotalTimesong = findViewById(R.id.textviewTotalimesong);
//        sktime = findViewById(R.id.seekbarSong);
//        imgPlay = findViewById(R.id.imagebuttonPlay);
//        imgNext = findViewById(R.id.imagebuttonNext);
//        imgPreview = findViewById(R.id.imagebuttonPreview);
//        imgRandom = findViewById(R.id.imagebuttonSuffle);
//        imgRepeat = findViewById(R.id.imagebuttonRepeat);
//        viewPagerPlaynhac = findViewById(R.id.viewpagerPlaynhac);
//        setSupportActionBar(toolbarPlaynhac);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbarPlaynhac.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mediaPlayer.stop();
//                mangbaihat.clear();
//                finish();
//            }
//        });
//        toolbarPlaynhac.setTitleTextColor(Color.WHITE);
//        dianhacFragment = new DianhacFragment();
//        playdanhbaihatFragment = new PlaydanhbaihatFragment();
//        adapternhac = new ViewPagerPlaylistnhacAdapter(getSupportFragmentManager());
//        adapternhac.AddFragment(playdanhbaihatFragment);
//        adapternhac.AddFragment(dianhacFragment);
//        viewPagerPlaynhac.setAdapter(adapternhac);
//        dianhacFragment = (DianhacFragment) adapternhac.getItem(1);
//        if (mangbaihat.size() > 0) {
//            getSupportActionBar().setTitle(mangbaihat.get(0).getTenbaihat());
//            new PlayMp3().execute(mangbaihat.get(0).getLinkbaihat());
//            imgPlay.setImageResource(R.drawable.pause_button);
//        }
//    }
//
//    class PlayMp3 extends AsyncTask<String,Void,String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            return strings[0];
//        }
//
//        @Override
//        protected void onPostExecute(String baihat) {
//            super.onPostExecute(baihat);
//            try {
//                mediaPlayer = new MediaPlayer();
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        mediaPlayer.stop();
//                        mediaPlayer.reset();
//                    }
//                });
//                mediaPlayer.setDataSource(baihat);
//                mediaPlayer.prepare();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mediaPlayer.start();
//            TimeSong();
//            UpdateTime();
//        }
//    }
//    private void TimeSong() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//        tvTotalTimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
//        sktime.setMax(mediaPlayer.getDuration());
//    }
//
//    private void UpdateTime(){
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(mediaPlayer != null){
//                    sktime.setProgress(mediaPlayer.getCurrentPosition());
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//                    tvTimesong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
//                    handler.postDelayed(this,300);
//                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            next = true;
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }
//        },300);
//        final Handler handler1 = new Handler();
//        handler1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (next == true){
//                    if (position < mangbaihat.size()) {
//                        imgPlay.setImageResource(R.drawable.pause_button);
//                        position++;
//                        if (repeat == true) {
//                            if (position == 0) {
//                                position = mangbaihat.size();
//                            }
//                            position -= 1;
//                        }
//                        if (checkrandom == true) {
//                            Random random = new Random();
////                            positionRandom = position;
//                            int index = random.nextInt(mangbaihat.size());
//                            if (index == position) {
////                                position = positionRandom - 1;
//                                position = index - 1;
//                            }
//                        }
//                        if (position > (mangbaihat.size() - 1) ){
//                            position = 0;
//                        }
//                        new PlayMp3().execute(mangbaihat.get(position).getLinkbaihat());
//                        dianhacFragment.PlayNhac(mangbaihat.get(position).getHinhbaihat());
//                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
//                    }
//                    imgPreview.setClickable(false);
//                    imgNext.setClickable(false);
//                    Handler handler1 = new Handler();
//                    handler1.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            imgPreview.setClickable(true);
//                            imgNext.setClickable(true);
//                        }
//                    },2000);
//                    next = false;
//                    handler1.removeCallbacks(this);
//                }else {
//                    handler1.postDelayed(this, 1000);
//                }
//            }
//        },1000);
//    }
//
//
//}
