package com.coder.myjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.coder.myjob.fragment1.Akunfrelancer;
import com.coder.myjob.fragment1.Helpfrelancer;
import com.coder.myjob.fragment1.Homefrelancer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardFreelancer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_freelancer);
        loadFragment(new Homefrelancer());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home_menu:
                        fragment = new Homefrelancer();
                        break;
                    case R.id.riwayat_menu:
                        fragment = new Helpfrelancer();
                        break;
                    case R.id.akun_menu:
                        fragment = new Akunfrelancer();
                        break;
                }
                return loadFragment(fragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}