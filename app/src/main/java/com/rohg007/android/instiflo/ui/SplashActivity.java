package com.rohg007.android.instiflo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rohg007.android.instiflo.MainActivity;
import com.rohg007.android.instiflo.R;

public class SplashActivity extends AppCompatActivity {

    ImageView splashBranding;
    private static final int SPLASH_TIMEOUT = 3500;
    private FirebaseAuth mAuth;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        account = GoogleSignIn.getLastSignedInAccount(this);

        splashBranding = findViewById(R.id.splash_branding);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                Intent intent;

                if(currentUser==null && account==null)
                    intent = new Intent(SplashActivity.this,LoginActivity.class);
                else
                    intent = new Intent(SplashActivity.this,MainActivity.class);

                startActivity(intent);
                finish();
            }
        },SPLASH_TIMEOUT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadAnimationOnBranding();

    }

    private void loadAnimationOnBranding(){
        Animation zoomIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        splashBranding.startAnimation(zoomIn);
    }
}
