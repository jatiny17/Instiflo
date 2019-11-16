package com.rohg007.android.instiflo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rohg007.android.instiflo.R;
import com.rohg007.android.instiflo.adapters.CartAdapter;
import com.rohg007.android.instiflo.adapters.MyProductsAdapter;
import com.rohg007.android.instiflo.models.Product;

import java.util.ArrayList;
import java.util.List;

public class MyProducts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = user.getUid(); // user.getUid() will return null if you are not log in
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        Query query = db.child("products").orderByChild("ownerId").equalTo(current_uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            final List<String> lstItems= new ArrayList<String>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = (String) ds.getKey();
                    String values = (String) ds.getValue();
                    Log.e("Values",""+values );
                    lstItems.add(values );

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // do something
            }
        });
        //
        RecyclerView recyclerView = findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MyProductsAdapter(Product.getTestProducts()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
}
