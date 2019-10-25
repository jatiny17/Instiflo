package com.rohg007.android.instiflo.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rohg007.android.instiflo.R;
import com.rohg007.android.instiflo.utils.DateSetter;
import com.rohg007.android.instiflo.utils.TimeSetter;

public class AddEvent extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int REQUEST_CODE = 500;
    private Uri mImageUri;
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
        TextInputEditText timeEdt = findViewById(R.id.event_time_edt);

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
