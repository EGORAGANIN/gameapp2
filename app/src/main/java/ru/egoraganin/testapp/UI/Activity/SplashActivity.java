package ru.egoraganin.testapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import ru.egoraganin.testapp.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startAdvantageViewPageActivity();
    }

    private void startAdvantageViewPageActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentAdvantageActivity = new Intent(SplashActivity.this, GameActivity.class);
                startActivity(intentAdvantageActivity);
                finish();
            }
        }, 1250);

    }
}
