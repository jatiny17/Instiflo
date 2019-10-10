package com.rohg007.android.instiflo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rohg007.android.instiflo.ui.LoginActivity;
import com.rohg007.android.instiflo.ui.LoginFragment;

public class MainActivity extends AppCompatActivity {
    String email;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.logout);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            email=user.getEmail();
            name=user.getDisplayName();
        }
        Toast.makeText(getApplicationContext(),"Logged in as"+email+" "+name,Toast.LENGTH_SHORT).show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
