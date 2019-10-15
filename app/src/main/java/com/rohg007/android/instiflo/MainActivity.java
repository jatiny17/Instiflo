package com.rohg007.android.instiflo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rohg007.android.instiflo.ui.LoginActivity;
import com.rohg007.android.instiflo.ui.LoginFragment;

public class MainActivity extends AppCompatActivity {
    String email;
    String name;
    private static boolean FLAG= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.logout);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().
                build();

        final GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this,gso);
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(user!=null){
            email=user.getEmail();
            name=user.getDisplayName();
        } else {
            email = account.getEmail();
            name = account.getDisplayName();
        }

        Toast.makeText(getApplicationContext(),"Logged in as"+email+" "+name,Toast.LENGTH_SHORT).show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account==null) {
                    FirebaseAuth.getInstance().signOut();
                } else {
                    googleSignInClient.signOut();
                }
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
