package com.example.bob.codladzieci;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCardActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCardDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mCardPhotosStorageReference;
    private Context context;

    @BindView(R.id.buttonAddCard) Button buttonAddCard;
    @BindView(R.id.textInputOrganizerName) TextView textInputOrganizerName;
    @BindView(R.id.textInputOrganizerAddress) TextView textInputOrganizerAddress;
    @BindView(R.id.inputCardPhoto) ImageView inputCardPhoto;
    @BindView(R.id.addCardPhoto) Button addCardPhoto;
    @BindView(R.id.textInputCardTitle) TextView textInputCardTitle;
    @BindView(R.id.textInputShortInfo) TextView textInputShortInfo;
    @BindView(R.id.textInputCardCategory) TextView textInputCardCategory;
    @BindView(R.id.textInputKidsAge) TextView textInputKidsAge;
    @BindView(R.id.textInputDate) TextView textInputDate;
    @BindView(R.id.textInputPrice) TextView textInputPrice;
    @BindView(R.id.textInputLongInfo) TextView textInputLongInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCardDatabaseReference = mFirebaseDatabase.getReference().child("cards");
        mFirebaseStorage = FirebaseStorage.getInstance();
        mCardPhotosStorageReference = mFirebaseStorage.getReference().child("card_photos");


        buttonAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if (textInputCardTitle != null && textInputCardCategory != null && textInputOrganizerName != null) {
                    Card card = new Card(textInputCardTitle.getText().toString(), textInputCardCategory.getText().toString(), Integer.parseInt(textInputKidsAge.getText().toString()), Integer.parseInt(textInputDate.getText().toString()), Integer.parseInt(textInputPrice.getText().toString()), textInputShortInfo.getText().toString(), textInputLongInfo.getText().toString(), textInputOrganizerName.getText().toString(), textInputOrganizerAddress.getText().toString(), null);
                    mCardDatabaseReference.push().setValue(card);
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }

        });
    }

}
