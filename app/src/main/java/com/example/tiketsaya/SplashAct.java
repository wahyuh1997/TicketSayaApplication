package com.example.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAct extends AppCompatActivity {

    Animation app_splash, btt;
    ImageView app_logo;
    TextView subtitle;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Load Animation
        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);

        //Load Element
        app_logo = findViewById(R.id.app_logo);
        subtitle = findViewById(R.id.subtitle);

        //Run Animation
        app_logo.startAnimation(app_splash);
        subtitle.startAnimation(btt);

        getUsernameLocal();
    }
    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        if (username_key_new.isEmpty()) {


            //Setting Timer untuk SplashAct
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Merubaqh Activity ke Activity lain
                    Intent gogetstarted = new Intent(SplashAct.this,GetStartedAct.class);
                    startActivity(gogetstarted);
                    finish();

                }
            }, 2000);

        } else {
            //Setting Timer untuk SplashAct
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Merubaqh Activity ke Activity lain
                    Intent gohome = new Intent(SplashAct.this,HomeAct.class);
                    startActivity(gohome);
                    finish();

                }
            }, 2000);
        }
    }
}
