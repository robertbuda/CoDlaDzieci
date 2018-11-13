package com.example.bob.codladzieci;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private HomeFragment homefragment = new HomeFragment();
    private OverviewFragment overviewFragment = new OverviewFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private FavouritesFragment favouritesFragment = new FavouritesFragment();
    private LibraryFragment libraryFragment = new LibraryFragment();

    private CardDialog cardDialog;

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

    public static final String ANONYMOUS = "Nie zalogowano";
    public static final int RC_SIGN_IN = 1;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

    private String mUsername;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final int titleAlwaysVisible = 1;
        navigation.setLabelVisibilityMode(titleAlwaysVisible);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        changeFragment(homefragment);
        showFloatingButton();

        mUsername = ANONYMOUS;
        startAuth();
    }

    private void startAuth() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MainActivity.this,"You are sign in",Toast.LENGTH_SHORT).show();
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    onSignedInInitialize(mUsername);
                }
                if (menu != null){
                    menu.findItem(R.id.user_login_name).setTitle(mUsername);
                }
            }
        };
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayoutFragment, fragment)
                .commit();

    }

    public void showFloatingButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardDialog = new CardDialog();
                cardDialog.setCancelable(true);
                cardDialog.show(getSupportFragmentManager(), TAG);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        /*FirebaseUser user = mFirebaseAuth.getCurrentUser();
        menu.findItem(R.id.user_login_name).setTitle(user.getDisplayName());*/
        menu.findItem(R.id.user_login_name).setTitle(mUsername);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sign_in_menu:
                addLoginAuth();
                return true;
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                mUsername = ANONYMOUS;
                menu.findItem(R.id.user_login_name).setTitle(mUsername);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addLoginAuth() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            Toast.makeText(MainActivity.this,"You are sign in",Toast.LENGTH_SHORT).show();
            onSignedInInitialize(user.getDisplayName());

        } else {
            onSignedOutCleanup();
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }
    }

    private void onSignedInInitialize(String displayName) {
        mUsername = displayName;
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
    }

    public void addActivityForKids(View view) {
        Intent intent = new Intent(this, AddCardActivity.class);
        startActivity(intent);
        cardDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK){
                Toast.makeText(MainActivity.this,"Signed In",Toast.LENGTH_SHORT).show();
                menu.findItem(R.id.user_login_name).setTitle(mUsername);
            } else if (resultCode == RESULT_CANCELED){
                Toast.makeText(MainActivity.this,"Signed In Cancelled",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }



}
