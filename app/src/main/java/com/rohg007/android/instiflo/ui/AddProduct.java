package com.rohg007.android.instiflo.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rohg007.android.instiflo.R;
import com.rohg007.android.instiflo.models.Event;
import com.rohg007.android.instiflo.models.Product;

public class AddProduct extends AppCompatActivity {

    private ImageView browseImageView;
    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int REQUEST_CODE = 500;
    private Uri mImageUri;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

    private TextInputEditText product_title,product_price,product_unit,product_description;
    private MaterialCheckBox rent,buy;
    private MaterialButton clear_product_button,add_product_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Toolbar toolbar = findViewById(R.id.add_product_toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Product");
        }

//        Toast.makeText(this, "Add product", Toast.LENGTH_SHORT).show();

        FloatingActionButton floatingActionButton = findViewById(R.id.product_browse_fab);
        browseImageView = findViewById(R.id.product_browse_image_view);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        product_title=(TextInputEditText)findViewById(R.id.product_title_edt);
        product_price=(TextInputEditText)findViewById(R.id.product_price_edt);
        product_unit=(TextInputEditText)findViewById(R.id.product_unit_edt);
        product_description=(TextInputEditText)findViewById(R.id.product_description_edt);

        rent=(MaterialCheckBox)findViewById(R.id.rent_checkbox);
        buy=(MaterialCheckBox)findViewById(R.id.buy_checkbox);

        clear_product_button=(MaterialButton)findViewById(R.id.product_clear_button);
        add_product_button=(MaterialButton)findViewById(R.id.add_product_button);

        clear_product_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(AddProduct.this, "Clear", Toast.LENGTH_SHORT).show();
                product_title.getText().clear();
                product_price.getText().clear();
                product_unit.getText().clear();
                product_description.getText().clear();

                rent.setChecked(false);
                buy.setChecked(false);
            }
        });

        add_product_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(AddProduct.this, "Add clicked", Toast.LENGTH_SHORT).show();

                String title=product_title.getText().toString().trim();
                String price=product_price.getText().toString().trim();
                String unit=product_unit.getText().toString().trim();
                String description=product_description.getText().toString();
//
                Boolean flag=true;

                product_title.setError(null);
                product_price.setError(null);
                product_unit.setError(null);
                product_description.setError(null);
                buy.setError(null);
                rent.setError(null);
//
                if(title.isEmpty()) {
                    product_title.setError("Product title can't be empty");
                    flag= false;
                }
//
                if(price.isEmpty()) {
                    product_price.setError("Product price can't be empty");
                    flag= false;
                }
//
                if(unit.isEmpty()) {
                    product_unit.setError("Product units can't be empty");
                    flag = false;
                }
//
                if(description.isEmpty()) {
                    product_description.setError("Product desription can't be empty");
                    flag = false;
                }
//
                if(!buy.isChecked()&&!rent.isChecked())
                {
                    buy.setError("Category should no be empty");
                    rent.setError("Category should no be empty");
                    flag=false;
                }

                if(flag)
                {
                   Product product=new Product(title, Integer.parseInt(price));

                    if(buy.isChecked()&&rent.isChecked())
                        product.setCategory(3);

                    else if(buy.isChecked())
                        product.setCategory(1);

                    else
                        product.setCategory(2);

                    databaseReference.child("products").push().setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddProduct.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddProduct.this, "Product Adding Failed", Toast.LENGTH_SHORT).show();
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
