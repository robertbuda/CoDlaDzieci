package com.example.bob.codladzieci;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCardActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCardDatabaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCardDatabaseReference = mFirebaseDatabase.getReference().child("cards");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
