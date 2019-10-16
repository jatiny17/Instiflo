package com.rohg007.android.instiflo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rohg007.android.instiflo.ui.EventsFragment;
import com.rohg007.android.instiflo.ui.LoginActivity;
import com.rohg007.android.instiflo.ui.LoginFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String email;
    String name;
    private static boolean FLAG= false;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_main);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(user!=null){
            email=user.getEmail();
            name=user.getDisplayName();
        } else {
            email = account.getEmail();
            name = account.getDisplayName();
        }

        getSupportFragmentManager().beginTransaction().add(R.id.container_main,new EventsFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_events:
                        getSupportFragmentManager().beginTransaction().add(R.id.container_main, new EventsFragment()).commit();
                        break;
                    case R.id.menu_buy:
                        Toast.makeText(getApplicationContext(), "Buy Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_rent:
                        Toast.makeText(getApplicationContext(), "Rent Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_shopping_cart:
                        Toast.makeText(getApplicationContext(), "Shopping Cart", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
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
                Toast.makeText(this,"My Purchases Clicked",Toast.LENGTH_SHORT).show();
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
                logOut();
        }
        return true;
    }



    private void logOut(){
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().
                build();

        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account==null) {
            FirebaseAuth.getInstance().signOut();
        } else {
            googleSignInClient.signOut();
        }
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
