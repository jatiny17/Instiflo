package com.rohg007.android.instiflo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.rohg007.android.instiflo.R;
import com.rohg007.android.instiflo.adapters.CartAdapter;
import com.rohg007.android.instiflo.adapters.MyProductsAdapter;
import com.rohg007.android.instiflo.models.Product;

public class MyProducts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);
        
        RecyclerView recyclerView = findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MyProductsAdapter(Product.getTestProducts()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
}
