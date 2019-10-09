package com.rohg007.android.instiflo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rohg007.android.instiflo.ui.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.container,new LoginFragment()).commit();
    }
}
