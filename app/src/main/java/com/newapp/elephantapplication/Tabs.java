package com.newapp.elephantapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

/**
 * The type Tabs.
 */
public class Tabs extends AppCompatActivity {

    // initialize variable

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private LiveLocationFragment liveLocationFragment;
    private MyLocationFragment myLocationFragment;
    private StatFragment statFragment;
    private HistoryFragment historyFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_nav);
        frameLayout = (FrameLayout) findViewById(R.id.main_frame);
        liveLocationFragment = new LiveLocationFragment();
        myLocationFragment = new MyLocationFragment();
        statFragment = new StatFragment();
        historyFragment = new HistoryFragment();
        profileFragment = new ProfileFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_liveLocation:
                        bottomNavigationView.setItemBackgroundResource(R.color.white);
                        setFragment(liveLocationFragment);
                        return true;

                    case R.id.na_myLocation:
                        bottomNavigationView.setItemBackgroundResource(R.color.white);

                        setFragment(myLocationFragment);
                        return true;

                    case R.id.nav_start:
                        bottomNavigationView.setItemBackgroundResource(R.color.white);

                        setFragment(statFragment);
                        return true;


                    case R.id.nav_history:
                        bottomNavigationView.setItemBackgroundResource(R.color.white);

                        setFragment(historyFragment);
                        return true;

                    case R.id.nav_profile:
                        bottomNavigationView.setItemBackgroundResource(R.color.white);
                        setFragment(profileFragment);

                    default:
                        return false;

                }

            }
        });

    }

    /**
     * @param fragment fragment
     */
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}