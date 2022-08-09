package com.coder.myjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.coder.myjob.fragment2.Akun;
import com.coder.myjob.fragment2.Chat;
import com.coder.myjob.fragment2.Homeseeker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardSeeker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_seeker);
        loadFragment(new Homeseeker());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomMenuSeeker);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home_menu_seeker:
                        fragment = new Homeseeker();
                        break;
                    case R.id.chat:
                        fragment = new Chat();
                        break;
                    case R.id.akun_menu_seeker:
                        fragment = new Akun();
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