package com.example.foodtracker;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.foodtracker.databinding.ActivityRecylerviewrowBinding;

public class recylerviewrow extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRecylerviewrowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding = ActivityRecylerviewrowBinding.inflate(getLayoutInflater());
        //     setContentView(binding.getRoot());

        //     setSupportActionBar(binding.toolbar);

        //     NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_recylerviewrow);
        //     appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //     NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //     binding.fab.setOnClickListener(new View.OnClickListener() {
        //         @Override
        //         public void onClick(View view) {
        //             Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                     .setAnchorView(R.id.fab)
        //                     .setAction("Action", null).show();
        //         }
        //     });
        // }

        // @Override
        // public boolean onSupportNavigateUp() {
        //     NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_recylerviewrow);
        //     return NavigationUI.navigateUp(navController, appBarConfiguration)
        //             || super.onSupportNavigateUp();
        // }
}