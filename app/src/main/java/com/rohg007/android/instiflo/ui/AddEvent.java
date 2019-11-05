package com.rohg007.android.instiflo.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rohg007.android.instiflo.R;
import com.rohg007.android.instiflo.models.Event;
import com.rohg007.android.instiflo.utils.DateSetter;
import com.rohg007.android.instiflo.utils.TimeSetter;

public class AddEvent extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int REQUEST_CODE = 500;
    private Uri mImageUri;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    ImageView browseImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Toolbar toolbar = findViewById(R.id.add_event_toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Event");
        }

        //toolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));

        TextInputEditText dateEdt = findViewById(R.id.event_date_edt);
        final TextInputEditText timeEdt = findViewById(R.id.event_time_edt);

        FloatingActionButton floatingActionButton = findViewById(R.id.event_browse_fab);
        browseImageView = findViewById(R.id.event_browse_image_view);

        new TimeSetter(timeEdt);
        new DateSetter(dateEdt);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        MaterialButton add_event_button=(MaterialButton)findViewById(R.id.add_event_button);
        MaterialButton clear_event_button=(MaterialButton) findViewById(R.id.event_clear_button);

        add_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextInputEditText event_title=(TextInputEditText)findViewById(R.id.event_title_edt);
                TextInputEditText event_date=(TextInputEditText)findViewById(R.id.event_date_edt);
                TextInputEditText event_time=(TextInputEditText)findViewById(R.id.event_time_edt);
                TextInputEditText event_description=(TextInputEditText)findViewById(R.id.event_description_edt);

                //
                String title=event_title.getText().toString().trim();
                String date=event_date.getText().toString().trim();
                String time=event_time.getText().toString().trim();
                String description=event_description.getText().toString();

//                Toast.makeText(AddEvent.this, "Add Event clicked", Toast.LENGTH_SHORT).show();

                Boolean flag=true;

                if(title.isEmpty()) {
                    event_title.setError("Event title can't be empty");
                    flag=false;
                }

                if(date.isEmpty()) {
                    event_date.setError("Event date can't be empty");
                    flag=false;
                }

                if(time.isEmpty()) {
                    event_time.setError("Event time can't be empty");
                    flag=false;
                }

                if(description.isEmpty()) {
                    event_description.setError("Event description can't be empty");
                    flag=false;
                }

                if(flag)
                {
                    Event event=new Event(title, date, time, "", description);
//                    Toast.makeText(AddEvent.this, event.getEventTitle()+" "+event.getEventDate()+" "+event.getEventTime(), Toast.LENGTH_LONG).show();

                    databaseReference.child("events").push().setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddEvent.this, "Event Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddEvent.this, "Event Adding Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE){
            mImageUri = data.getData();
            browseImageView.setImageURI(mImageUri);
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
