package com.example.bob.codladzieci;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCardActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCardDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mCardPhotosStorageReference;
    private Context context;
    private String photoUrl;
    private int textEmptyField = R.string.Uzupełnij_pole;

    private static final int RC_PHOTO_PICKER = 2;

    @BindView(R.id.buttonAddCard)
    Button buttonAddCard;
    @BindView(R.id.textInputOrganizerName)
    TextView textInputOrganizerName;
    @BindView(R.id.textInputOrganizerAddress)
    TextView textInputOrganizerAddress;
    @BindView(R.id.inputCardPhoto)
    ImageView inputCardPhoto;
    @BindView(R.id.addCardPhoto)
    Button addCardPhoto;
    @BindView(R.id.textInputCardTitle)
    TextView textInputCardTitle;
    @BindView(R.id.textInputShortInfo)
    TextView textInputShortInfo;
    @BindView(R.id.textInputCardCategory)
    TextView textInputCardCategory;
    @BindView(R.id.textInputKidsAge)
    TextView textInputKidsAge;
    @BindView(R.id.textInputDate)
    TextView textInputDate;
    @BindView(R.id.textInputPrice)
    TextView textInputPrice;
    @BindView(R.id.textInputLongInfo)
    TextView textInputLongInfo;
    @BindView(R.id.photoProgress)
    ProgressBar photoProgress;

    public static final String ANONYMOUS = "Login ->";
    public static final int RC_SIGN_IN = 1;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

    private String mUsername;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCardDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mCardPhotosStorageReference = mFirebaseStorage.getReference().child("card_photos");

        textInputLongInfo.setEnabled(false);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        textInputLongInfo.setText(formattedDate);

        buttonAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(textInputOrganizerName.getText().toString().trim())) {
                    textInputOrganizerName.setError(getString(textEmptyField));
                } else if (TextUtils.isEmpty(textInputOrganizerAddress.getText().toString().trim())) {
                    textInputOrganizerAddress.setError(getString(textEmptyField));
                } else if (TextUtils.isEmpty(textInputCardTitle.getText().toString().trim())) {
                    textInputCardTitle.setError(getString(textEmptyField));
                } else if (TextUtils.isEmpty(textInputShortInfo.getText().toString().trim())) {
                    textInputShortInfo.setError(getString(textEmptyField));
                } else if (TextUtils.isEmpty(textInputKidsAge.getText().toString().trim())) {
                    textInputKidsAge.setError(getString(textEmptyField));
                } else if (TextUtils.isEmpty(textInputCardCategory.getText().toString().trim())) {
                    textInputCardCategory.setError(getString(textEmptyField));
                } else if (TextUtils.isEmpty(textInputDate.getText().toString().trim())) {
                    textInputDate.setError(getString(textEmptyField));
                } else if (TextUtils.isEmpty(textInputPrice.getText().toString().trim())) {
                    textInputPrice.setError(getString(textEmptyField));
                } else {
                    Card card = new Card(textInputCardTitle.getText().toString(), textInputCardCategory.getText().toString(),
                            textInputKidsAge.getText().toString(), textInputDate.getText().toString(), textInputPrice.getText().toString(),
                            textInputShortInfo.getText().toString(), textInputLongInfo.getText().toString(),
                            textInputOrganizerName.getText().toString(), textInputOrganizerAddress.getText().toString(), photoUrl);

                    String key = mCardDatabaseReference.push().getKey();
                    mCardDatabaseReference.child("cards").child(key).setValue(card);
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }
            }

        });

        addCardPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.CATEGORY_APP_GALLERY, true);
                startActivityForResult(Intent.createChooser(intent, "Add photo to card"), RC_PHOTO_PICKER);
            }
        });

        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();

        addLoginAuth();
    }

    private void addLoginAuth() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(AddCardActivity.this, "You are sign in", Toast.LENGTH_SHORT).show();
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
        };
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

    private void onSignedInInitialize(String displayName) {
        mUsername = displayName;
        //attachDatabaseReadListener();
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        //mMessageAdapter.clear();
        //detachDatabaseReadListener();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            photoProgress.setVisibility(View.VISIBLE);

            Bitmap bmp = null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
            byte[] fileInBytes = baos.toByteArray();

            StorageReference photoRef = mCardPhotosStorageReference.child(selectedImageUri.getLastPathSegment());
            photoRef.putBytes(fileInBytes).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            photoUrl = uri.toString();
                            //inputCardPhoto.setImageBitmap(BitmapFactory.decodeFile(photoUrl));
                            RequestOptions options = new RequestOptions();
                            //options.override(100);
                            options.centerCrop();
                            Glide.with(inputCardPhoto.getContext())
                                    .load(uri)
                                    .apply(options)
                                    .into(inputCardPhoto);
                            photoProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            });
        } else if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(AddCardActivity.this, "Signed In", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(AddCardActivity.this, "Signed In Cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
