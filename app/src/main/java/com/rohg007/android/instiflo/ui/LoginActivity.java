package com.rohg007.android.instiflo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rohg007.android.instiflo.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager().beginTransaction().add(R.id.login_container,new LoginFragment()).commit();

    }
}
