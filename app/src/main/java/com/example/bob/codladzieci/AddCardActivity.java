package com.example.bob.codladzieci;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCardActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCardDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mCardPhotosStorageReference;
    private Context context;
    private String photoUrl;

    private static final int RC_PHOTO_PICKER =  2;

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
                    Card card = new Card(textInputCardTitle.getText().toString(), textInputCardCategory.getText().toString(), textInputKidsAge.getText().toString(), textInputDate.getText().toString(), textInputPrice.getText().toString(), textInputShortInfo.getText().toString(), textInputLongInfo.getText().toString(), textInputOrganizerName.getText().toString(), textInputOrganizerAddress.getText().toString(), photoUrl);
                    mCardDatabaseReference.push().setValue(card);
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }

        });

        addCardPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.CATEGORY_APP_GALLERY,true);
                startActivityForResult(Intent.createChooser(intent,"Add photo to card"),RC_PHOTO_PICKER);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            StorageReference photoRef = mCardPhotosStorageReference.child(selectedImageUri.getLastPathSegment());
            photoRef.putFile(selectedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            photoUrl = uri.toString();
                            //inputCardPhoto.setImageBitmap(BitmapFactory.decodeFile(photoUrl));
                            RequestOptions options = new RequestOptions();
                            options.centerCrop();
                            Glide.with(inputCardPhoto.getContext())
                                    .load(uri)
                                    .apply(options)
                                    .into(inputCardPhoto);
                        }
                    });
                }
            });
        }
    }
}
