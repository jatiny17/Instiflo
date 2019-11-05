package com.rohg007.android.instiflo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rohg007.android.instiflo.adapters.CartAdapter;
import com.rohg007.android.instiflo.models.User;
import com.rohg007.android.instiflo.ui.BuyFragment;
import com.rohg007.android.instiflo.ui.EventsFragment;
import com.rohg007.android.instiflo.ui.LoginActivity;
import com.rohg007.android.instiflo.ui.LoginFragment;
import com.rohg007.android.instiflo.ui.ProductDetails;
import com.rohg007.android.instiflo.ui.ShoppingCartFragment;
import com.rohg007.android.instiflo.ui.UserDetailsActivity;
import com.rohg007.android.instiflo.utils.ScrollHandler;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String email;
    String name;
    Uri photoUrl;

    private static boolean FLAG= false;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private GoogleSignInOptions gso;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount account;
    private FirebaseUser user;

    private FirebaseDatabase database;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private User globalUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //////////Toolbar Here///////////////
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        //////Navigation Drawer Here///////////
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_main);

        /////////Navigation View Header////////////
        View headerView = navigationView.getHeaderView(0);
        ImageView headerImage = headerView.findViewById(R.id.header_img);
        TextView navEmail = headerView.findViewById(R.id.header_email);

//        database = FirebaseDatabase.getInstance();
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        gso = new GoogleSignInOptions
//                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .requestProfile()
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .build();
//
//        googleSignInClient = GoogleSignIn.getClient(this,gso);
//
//        account = GoogleSignIn.getLastSignedInAccount(this);

        //////////retrieving user details////////////
//        getUserDetails();

//        email= user.getEmail();

//        navEmail.setText(email);

        Picasso.get()
                .load(photoUrl)
                .centerCrop()
                .error(R.drawable.instiflo_light)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(headerImage);

        getSupportFragmentManager().beginTransaction().add(R.id.container_main,new EventsFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_events:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new EventsFragment()).commit();
                        break;
                    case R.id.menu_buy:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new BuyFragment()).commit();
                        break;
                    case R.id.menu_rent:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new BuyFragment()).commit();
                        break;
                    case R.id.menu_shopping_cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new ShoppingCartFragment()).commit();
                        break;
                }
                return true;
            }
        });

        headerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHeaderImageClick();
            }
        });
    }

    private void onHeaderImageClick(){
        Intent intent = new Intent(MainActivity.this, UserDetailsActivity.class);
        intent.putExtra("email",email);
        intent.putExtra("id",globalUser.getUserId());
        intent.putExtra("firstName",globalUser.getFirstName());
        intent.putExtra("lastName",globalUser.getLastName());
        intent.putExtra("address",globalUser.getAddress());
        intent.putExtra("phone",globalUser.getPhoneNumber());
        intent.putExtra("imageUrl",globalUser.getUserImageUrl());
        startActivity(intent);
    }

    private void getUserDetails(){
        String path = "users/".concat(user.getUid());
       DatabaseReference databaseReference = database.getReference(path);
       databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               globalUser = dataSnapshot.getValue(User.class);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Log.e(LOG_TAG,"Cannot retrieve user details");
           }
       });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_my_events:
                Toast.makeText(this,"My Events Clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_my_purchases:
                Intent intent = new Intent(MainActivity.this, ProductDetails.class);
                startActivity(intent);
                break;
            case R.id.menu_my_products:
                Toast.makeText(this,"My Products Clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_settings:
                Toast.makeText(this,"Settings Clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_about:
                Toast.makeText(this,"About Us Clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(this,"Log Out Clicked",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }



    private void logOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
