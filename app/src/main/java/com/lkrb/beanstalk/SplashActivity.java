package com.lkrb.beanstalk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    private final int mSleepTime = 3000;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    protected void onStart() {
        super.onStart();
        new InitiateLoader().execute();
    }


    private class InitiateLoader extends AsyncTask {
        /**
         * Holds the main thread upto mSleepTime
         * @param objects
         * @return
         */
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Thread.sleep(mSleepTime);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return null;
        }


        /**
         * Observes the SplashVM.getSessionState method which returns live data
         * If Session State is SIGNEDIN then call navigateToMainNavigation else call navigateToWalkThrough
         * @param o
         */
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(mAuth.getCurrentUser()!=null){
                navigateToMainNavigation();
            } else {
                navigateToLogin();
            }

        }
    }

    private void navigateToMainNavigation() {
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void navigateToLogin() {
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
