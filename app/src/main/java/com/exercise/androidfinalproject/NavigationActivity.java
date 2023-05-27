package com.exercise.androidfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.customview.widget.Openable;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.exercise.androidfinalproject.databinding.FragmentLayoutBinding;

public class NavigationActivity<ActivityNavigationBinding> extends AppCompatActivity {

    private FragmentLayoutBinding binding;
    private NavController navController;
    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_profile, R.id.navigation_change_data, R.id.navigation_sensors_data)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        binding.navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_change_data:
                        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        ChangeDataFragment changeDataFragment = new ChangeDataFragment();
                        // Load ChangeDataFragment into the fragment container
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment, new ChangeDataFragment())
                                .addToBackStack(null)
                                .commit();
                        return true;
                    case R.id.navigation_profile:
                        // Load ChangeDataFragment into the fragment container
                        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        ProfileFragment profileFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment, new ProfileFragment())
                                .addToBackStack(null)
                                .commit();
                        return true;
                    case R.id.navigation_sensors_data:
                        // Load ChangeDataFragment into the fragment container
                        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        SensorsDataFragment sensorsDataFragment = new SensorsDataFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment, new SensorsDataFragment())
                                .addToBackStack(null)
                                .commit();
                        return true;
                    default:
                        return false;
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, (Openable) null);
    }

}