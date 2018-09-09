package com.example.bob.codladzieci;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements CardAdapter.ClickEvent {

    private CardAdapter cardAdapter;
    private ChildEventListener childEventListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCardDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mCardPhotosStorageReference;
    private List<Card> cardList;
    private List<String> listKeys;
    private CardsContract.CardAdapterInterface cardAdapterInterface;

    @BindView(R.id.cardHomeRecyclerView) RecyclerView cardHomeRecyclerView;
    @BindView(R.id.fragmentHomeProgressBar) ProgressBar fragmentHomeProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);

        fragmentHomeProgressBar.setVisibility(View.VISIBLE);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCardDatabaseReference = mFirebaseDatabase.getReference().child("cards");
        mFirebaseStorage = FirebaseStorage.getInstance();
        mCardPhotosStorageReference = mFirebaseStorage.getReference().child("card_photos");

        cardList = new ArrayList<>();
        listKeys = new ArrayList<>();

        cardAdapter = new CardAdapter(cardList,this.getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        cardHomeRecyclerView.setLayoutManager(linearLayoutManager);
        cardHomeRecyclerView.setAdapter(cardAdapter);

        cardAdapter.setClickEvent(HomeFragment.this);

        attachDatabaseReadListener();

        return view;
    }

    private void attachDatabaseReadListener () {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Card card = dataSnapshot.getValue(Card.class);
                    cardList.add(card);
                    listKeys.add(dataSnapshot.getKey());
                    cardAdapter.notifyDataSetChanged();
                    fragmentHomeProgressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                    String key = dataSnapshot.getKey();
                    int index = listKeys.indexOf(key);

                    if (index != -1) {
                        cardList.remove(index);
                        listKeys.remove(index);
                        cardAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mCardDatabaseReference.addChildEventListener(childEventListener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        detachDatabaseReadListener();
    }

    private void detachDatabaseReadListener () {
        if (childEventListener != null) {
            mCardDatabaseReference.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    @Override
    public void clickEventItem(int position) {
        Toast.makeText(getActivity(),"Usuwanie " + position + listKeys.get(position),Toast.LENGTH_LONG).show();
        mCardDatabaseReference.child(listKeys.get(position)).removeValue();
    }
}
