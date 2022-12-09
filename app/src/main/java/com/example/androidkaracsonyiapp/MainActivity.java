package com.example.androidkaracsonyiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private TextView countdown;
    private Timer timer;
    private Date karacsony;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.zene);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        init();

    }

    private void init(){
        countdown=findViewById(R.id.countdown);
        Calendar most=Calendar.getInstance();
        int ev=most.get(Calendar.YEAR);
        int honap=most.get(Calendar.MONTH);
        int nap=most.get(Calendar.DATE);
        if (honap==11 && nap>24){
            ev++;
        }
        Calendar karacsony =Calendar.getInstance();
        karacsony.set(ev,11,25,0,0,0);
        this.karacsony=karacsony.getTime();
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Date most=Calendar.getInstance().getTime();
                long hatralevoido=karacsony.getTime() - most.getTime();

                long masodpercmili=1000;
                long percmili=masodpercmili*60;
                long oramili=percmili*60;
                long napmili=oramili*24;

                long nap =hatralevoido/napmili;
                hatralevoido %= napmili;
                long ora=hatralevoido / oramili;
                hatralevoido%=oramili;
                long perc=hatralevoido / percmili;
                hatralevoido%=percmili;
                long masodperc=hatralevoido / masodpercmili;

                String hatralevoszoveg=String.format("%d nap \n%2d óra\n %2d perc \n %2d mp",nap,ora,perc,masodperc);
                runOnUiThread(()->{
                    countdown.setText(hatralevoszoveg);
                });
            }
        };
        timer.schedule(task,0,500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}