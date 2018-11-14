package com.example.bob.codladzieci;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CardCitySpinnerDialog extends DialogFragment{

    private Spinner spinnerCities;
    private List<String> listCities = new ArrayList<>();
    private ChildEventListener childEventListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCardDatabaseReference;
    private String city;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_card_city_dialog, container, false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCardDatabaseReference = mFirebaseDatabase.getReference().child("cards");
        attachDatabaseReadListener();

        //Collections.sort(listCities);
        spinnerCities = (Spinner) view.findViewById(R.id.spinnerCities);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.item_card_city_dialog,R.id.spinner_text_view, listCities);
        adapter.setDropDownViewResource(R.layout.item_card_city_dialog);
        spinnerCities.setAdapter(adapter);

        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = (String)parent.getItemAtPosition(position);
                clickeventCity.clickEventCityItem(city);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void attachDatabaseReadListener() {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Card card = dataSnapshot.getValue(Card.class);
                    if (card.getOrganizerAddress() != null && !listCities.contains(card.getOrganizerAddress())) {
                        listCities.add(card.getOrganizerAddress());
                    }
                    //city = listCities.get(0);
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

    public interface ClickEvent {
        void clickEventCityItem(String city);
    }

    ClickEvent clickeventCity;

    public void setClickEventCity(ClickEvent event) {
        this.clickeventCity = event;
    }

}
