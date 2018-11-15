package com.example.bob.codladzieci;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder>{

    private List<Card> cards;
    private Context context;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCardDatabaseReference;

    public CardAdapter(List<Card> cards, Context context) {
        this.cards = cards;
        this.context = context;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_card,parent,false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, final int position) {
        Card card = cards.get(position);

    TextView itemCardOrganizerName = holder.itemCardOrganizerName;
    itemCardOrganizerName.setText(card.getOrganizerName());

    TextView itemCardOrganizerAddress = holder.itemCardOrganizerAddress;
    itemCardOrganizerAddress.setText(card.getOrganizerAddress());

    ImageView inputCardPhoto = holder.itemCardPhoto;
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(inputCardPhoto.getContext())
                .load(card.getCardPhotoUrl())
                .apply(options)
                .into(inputCardPhoto);

    TextView itemCardTitle = holder.itemCardTitle;
    itemCardTitle.setText(card.getCardTitle());

    TextView itemCardShortInfo = holder.itemCardShortInfo;
    itemCardShortInfo.setText(card.getCardShortInfo());

    TextView itemCardCategory = holder.itemCardCategory;
    itemCardCategory.setText(card.getCardCategory());

    final TextView itemCardAge = holder.itemCardAge;
    itemCardAge.setText(""+card.getCardKidsAge());

    TextView itemCardDate = holder.itemCardDate;
    itemCardDate.setText(""+card.getCardDate());

    TextView itemCardPrice = holder.itemCardPrice;
    itemCardPrice.setText(""+card.getCardPrice());

    final TextView itemCardLongInfo = holder.itemCardLongInfo;
    itemCardLongInfo.setText(card.getCardLongInfo());

    final ImageView itemDeleteCard = holder.itemDeleteCard;
    itemDeleteCard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendPositionToHomeFragment(position);

        }
    });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.itemCardOrganizerName) TextView itemCardOrganizerName;
        @BindView(R.id.itemCardOrganizerAddress) TextView itemCardOrganizerAddress;
        @BindView(R.id.itemCardPhoto) ImageView itemCardPhoto;
        @BindView(R.id.itemCardTitle) TextView itemCardTitle;
        @BindView(R.id.itemCardShortInfo) TextView itemCardShortInfo;
        @BindView(R.id.itemCardCategory) TextView itemCardCategory;
        @BindView(R.id.itemCardAge) TextView itemCardAge;
        @BindView(R.id.itemCardDate) TextView itemCardDate;
        @BindView(R.id.itemCardPrice) TextView itemCardPrice;
        @BindView(R.id.itemCardLongInfo) TextView itemCardLongInfo;
        @BindView(R.id.itemDeleteCard) ImageView itemDeleteCard;


        public CardHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mCardDatabaseReference = mFirebaseDatabase.getReference();
        }
    }

    public void sendPositionToHomeFragment (int position) {
        Toast.makeText(context,"USUNIĘTO " + position,Toast.LENGTH_LONG).show();
        clickevent.clickEventItem(position);

    }

    public interface ClickEvent {
        void clickEventItem(int position);
    }

    private ClickEvent clickevent;

    public void setClickEvent(ClickEvent event) {
        this.clickevent = event;
    }
}
