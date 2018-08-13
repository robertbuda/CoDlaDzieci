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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private CardAdapter cardAdapter;
    private Context context;
    private ChildEventListener childEventListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCardDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mCardPhotosStorageReference;
    private List<Card> cardList;

    @BindView(R.id.cardHomeRecyclerView) RecyclerView cardHomeRecyclerView;
    @BindView(R.id.fragmentHomeProgressBar) ProgressBar fragmentHomeProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);
        //cardHomeRecyclerView = (RecyclerView) view.findViewById(R.id.cardHomeRecyclerView);

        fragmentHomeProgressBar.setVisibility(View.VISIBLE);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCardDatabaseReference = mFirebaseDatabase.getReference().child("cards");
        mFirebaseStorage = FirebaseStorage.getInstance();
        mCardPhotosStorageReference = mFirebaseStorage.getReference().child("card_photos");

        //Card cardTest = new Card("Tytuł","Kategoria",1,22,49,"Info","Info długie","Organizator","Adres","URL");

        cardList = new ArrayList<>();
        cardAdapter = new CardAdapter(cardList,this.getActivity());
        cardHomeRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        cardHomeRecyclerView.setAdapter(cardAdapter);

        attachDatabaseReadListener();

        //cardList.add(cardTest);
        //cardAdapter.notifyItemInserted(cardList.size() - 1);

      /*  mCardDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Card card = dataSnapshot.getValue(Card.class);
                cardList.add(card);
                cardAdapter.notifyItemInserted(cardList.size() - 1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("ERROR", "Failed to read value.", databaseError.toException());

            }
        });*/

        return view;
    }

    private void attachDatabaseReadListener () {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Card card = dataSnapshot.getValue(Card.class);
                    cardList.add(card);
                    //cardAdapter.notifyItemInserted(cardList.size() - 1);
                    cardAdapter.notifyItemInserted(0);
                    cardAdapter.notifyDataSetChanged();
                    fragmentHomeProgressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
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
}
