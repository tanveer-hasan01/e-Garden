package com.example.egarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.egarden.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.Map;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ActivityMainBinding binding;
    Home homeFragment;
    Toolbar toolbarr;
    TextView toolbarTitle;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private GoogleSignInClient mGoogleSignInClient;

    SharedPreference sharedPreference = SharedPreference.getPreferences(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        toolbarr = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarr);

        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(R.string.app_name);

        setSupportActionBar(binding.include.toolbar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout, binding.include.toolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));


        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        binding.navigationView.setNavigationItemSelectedListener(this);
        binding.help.setOnClickListener((View.OnClickListener) this);
        initFragmentHome();

        Glide.with(MainActivity.this)
                .load(sharedPreference.getImage())
                .into(binding.profileImage);

        binding.name.setText(sharedPreference.getName());
        binding.email.setText(sharedPreference.getEmail());

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreference.setName("none");
                sharedPreference.setEmail("none");
                sharedPreference.setImage("none");
                signOut();
                revokeAccess();
                Toast.makeText(MainActivity.this, "Successfully Logout", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(MainActivity.this, Splash.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();


            }
        });


        /*binding.autoWatering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    binding.waterPump.setClickable(false);
                    binding.waterPump.setChecked(false);
                    Tools.snackInfo(MainActivity.this, "Water pump is automatically On/OFF");

                } else {
                    binding.waterPump.setClickable(true);
                }
            }
        });*/

        binding.autoWatering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {


                    Tools.snackInfo(MainActivity.this, "Water pump is automatically On/OFF");
                    binding.waterPump.setChecked(false);

                } else {


                }
            }
        });


        binding.waterPump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (binding.autoWatering.isChecked()) {

                    binding.waterPump.setChecked(false);
                    Tools.snackErrInfo(MainActivity.this, "Turn off Automatic watering !", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                } else {


                }

            }
        });

    }


    private void initFragmentHome() {
        homeFragment = new Home();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(binding.include.contentMain.fragmentContainer.getId(), homeFragment, "HomeFragment").commit();

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.help:


        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
}