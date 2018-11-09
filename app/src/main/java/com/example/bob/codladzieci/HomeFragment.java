package com.example.bob.codladzieci;

import android.content.Context;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
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
    private Context context;
    private List<Card> cardList2;
    private List<String> listKeys2;
    private CardAdapter cardAdapter2;
    private ChildEventListener childEventListener2;


    @BindView(R.id.cardHomeRecyclerView)
    RecyclerView cardHomeRecyclerView;
    @BindView(R.id.fragmentHomeProgressBar)
    ProgressBar fragmentHomeProgressBar;
    @BindView(R.id.cardHomeRecyclerView2)
    RecyclerView cardHomeRecyclerView2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        fragmentHomeProgressBar.setVisibility(View.VISIBLE);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCardDatabaseReference = mFirebaseDatabase.getReference().child("cards");
        mCardDatabaseReference.getDatabase().getReference("cards").orderByChild("cardLongInfo");

        mFirebaseStorage = FirebaseStorage.getInstance();
        mCardPhotosStorageReference = mFirebaseStorage.getReference().child("card_photos");

        cardList = new ArrayList<>();
        listKeys = new ArrayList<>();
        cardAdapter = new CardAdapter(cardList, this.getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // reverse view in recycler
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        cardHomeRecyclerView.setLayoutManager(linearLayoutManager);
        cardHomeRecyclerView.setAdapter(cardAdapter);
        cardAdapter.setClickEvent(HomeFragment.this);
        attachDatabaseReadListener();

        cardList2 = new ArrayList<>();
        listKeys2 = new ArrayList<>();
        cardAdapter2 = new CardAdapter(cardList2, this.getActivity());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this.getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        // reverse view in recycler
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        cardHomeRecyclerView2.setLayoutManager(linearLayoutManager2);
        cardHomeRecyclerView2.setAdapter(cardAdapter2);
        cardAdapter2.setClickEvent(HomeFragment.this);
        attachDatabaseReadListener2();

        return view;
    }

    private void attachDatabaseReadListener() {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Card card = dataSnapshot.getValue(Card.class);
                    if (card.getCardCategory().equals("sport1")) {
                        cardList.add(card);
                        listKeys.add(dataSnapshot.getKey());
                    } else {
                        cardList.add(card);
                        listKeys.add(dataSnapshot.getKey());
                    }
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

                        Card card = dataSnapshot.getValue(Card.class);
                        String url = card.getCardPhotoUrl();
                        if (url != null) {
                            StorageReference photoRef = mFirebaseStorage.getReferenceFromUrl(url);
                            //Toast.makeText(context,"Usuwanie " + photoRef, Toast.LENGTH_LONG).show();
                            photoRef.delete();
                        }
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
        detachDatabaseReadListener2();
    }

    private void detachDatabaseReadListener() {
        if (childEventListener != null) {
            mCardDatabaseReference.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    private void detachDatabaseReadListener2() {
        if (childEventListener2 != null) {
            mCardDatabaseReference.removeEventListener(childEventListener2);
            childEventListener2 = null;
        }
    }

    @Override
    public void clickEventItem(int position) {
        mCardDatabaseReference.child(listKeys.get(position)).removeValue();
    }


    private void attachDatabaseReadListener2() {
        if (childEventListener2 == null) {
            childEventListener2 = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Card card = dataSnapshot.getValue(Card.class);
                    if (card.getCardCategory().equals("sport1")) {
                        cardList2.add(card);
                        listKeys2.add(dataSnapshot.getKey());
                    } else {
                        cardList2.add(card);
                        listKeys2.add(dataSnapshot.getKey());
                    }
                    cardAdapter2.notifyDataSetChanged();
                    fragmentHomeProgressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                    String key = dataSnapshot.getKey();
                    int index = listKeys2.indexOf(key);

                    if (index != -1) {
                        cardList2.remove(index);
                        listKeys2.remove(index);
                        cardAdapter2.notifyDataSetChanged();

                        Card card = dataSnapshot.getValue(Card.class);
                        String url = card.getCardPhotoUrl();
                        if (url != null) {
                            StorageReference photoRef = mFirebaseStorage.getReferenceFromUrl(url);
                            //Toast.makeText(context,"Usuwanie " + photoRef, Toast.LENGTH_LONG).show();
                            photoRef.delete();
                        }
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mCardDatabaseReference.addChildEventListener(childEventListener2);
        }
    }

}
