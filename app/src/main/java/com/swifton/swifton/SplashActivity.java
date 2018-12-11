package com.swifton.swifton;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    TextView txtproductname;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashThread;
    int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);

        txtproductname = findViewById(R.id.productname);

        StartAnimations();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent HomeIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(HomeIntent);
                overridePendingTransition(R.anim.alpha, R.anim.translate);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

    private void StartAnimations(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fade = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        anim.reset();
        fade.reset();
        CardView cv = findViewById(R.id.cardview);
        RelativeLayout ll =  findViewById(R.id.linlay);
        ll.clearAnimation();
        ll.startAnimation(fade);
        cv.clearAnimation();
        cv.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        anim.reset();
        fade.reset();
        ImageView ComLogo =  findViewById(R.id.applogo);

        ComLogo.clearAnimation();
        ComLogo.startAnimation(anim);
        //AppDesc.clearAnimation();
        //AppDesc.startAnimation(anim);
        txtproductname.clearAnimation();
        txtproductname.startAnimation(anim);

        splashThread = new Thread(){
            @Override
            public void run() {
                try {
                    int waited = 0;
                    //Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    SplashActivity.this.finish();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    SplashActivity.this.finish();
                }
            }
        };

    }
}