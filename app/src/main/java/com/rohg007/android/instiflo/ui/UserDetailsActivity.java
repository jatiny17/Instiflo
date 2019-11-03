package com.rohg007.android.instiflo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rohg007.android.instiflo.R;
import com.rohg007.android.instiflo.models.User;

public class UserDetailsActivity extends AppCompatActivity {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String userId;
    private DatabaseReference databaseReference;
    private static final String LOG_TAG = UserDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Toolbar toolbar = findViewById(R.id.user_details_toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        userId = intent.getStringExtra("id");

        final EditText firstNameEdt = findViewById(R.id.first_name_edt);
        final EditText lastNameEdt = findViewById(R.id.last_name_edt);
        EditText emailEdt = findViewById(R.id.user_details_email_edt);
        final EditText addressEdt = findViewById(R.id.user_address_edt);
        final EditText phoneEdt = findViewById(R.id.phone_number_edt);
        FloatingActionButton saveFab = findViewById(R.id.save_user_fab);

        emailEdt.setText(email);

        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = firstNameEdt.getText().toString();
                lastName = lastNameEdt.getText().toString();
                address = addressEdt.getText().toString();
                phone = phoneEdt.getText().toString();
                addDetailsToFirebase();
            }
        });
    }

    private void addDetailsToFirebase(){
        try {
            User user = new User(userId, firstName, lastName, email, address, phone);
            databaseReference.child("users").child(userId).setValue(user);
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"Couldn't save data",Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG,e.getMessage());
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
