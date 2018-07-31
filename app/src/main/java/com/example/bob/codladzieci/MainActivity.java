package com.example.bob.codladzieci;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private HomeFragment homefragment = new HomeFragment();
    private OverviewFragment overviewFragment = new OverviewFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private FavouritesFragment favouritesFragment = new FavouritesFragment();
    private LibraryFragment libraryFragment = new LibraryFragment();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment(homefragment);
                    return true;
                case R.id.navigation_overview:
                    changeFragment(overviewFragment);
                    return true;
                case R.id.navigation_search:
                    changeFragment(searchFragment);
                    return true;
                case R.id.navigation_notifications:
                    changeFragment(favouritesFragment);
                    return true;
                case R.id.navigation_library:
                    changeFragment(libraryFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        final int titleAlwaysVisible = 1;
        navigation.setLabelVisibilityMode(titleAlwaysVisible);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       /* FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new Fragment();
        fragmentTransaction.replace(R.id.frameLayoutFragment,fragment);
        fragmentTransaction.commit();*/
        changeFragment(homefragment);
    }

    private void changeFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayoutFragment, fragment)
                .commit();
    }

    public void showFloatingButton () {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
