package com.rohg007.android.instiflo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rohg007.android.instiflo.R;

public class ProductDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent i = getIntent();
        String title = i.getStringExtra("Title");
        int price = i.getIntExtra("Price",0);

        TextView titleTextView = findViewById(R.id.product_detail_title);
        TextView priceTextView = findViewById(R.id.product_detail_price);

        String stringPrice = "Rs."+price;

        titleTextView.setText(title);
        priceTextView.setText(stringPrice);
    }
}
