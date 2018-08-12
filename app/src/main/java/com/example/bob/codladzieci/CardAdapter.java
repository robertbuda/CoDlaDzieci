package com.example.bob.codladzieci;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    private List<Card> cards;
    private Context context;

    public CardAdapter(List<Card> cards, Context context) {
        this.cards = cards;
        this.context = context;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_card,parent,false);
        CardHolder cardHolder = new CardHolder(view);
        return cardHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
    Card card = cards.get(position);

    TextView itemCardOrganizerName = holder.itemCardOrganizerName;
    itemCardOrganizerName.setText(card.getOrganizerName());

    TextView itemCardOrganizerAddress = holder.itemCardOrganizerAddress;
    itemCardOrganizerAddress.setText(card.getOrganizatorAddress());

    ImageView inputCardPhoto = holder.itemCardPhoto;
        Glide.with(inputCardPhoto.getContext())
                .load(card.getCardPhotoUrl())
                .into(inputCardPhoto);

    TextView itemCardTitle = holder.itemCardTitle;
    itemCardTitle.setText(card.getCardTitle());

    TextView itemCardShortInfo = holder.itemCardShortInfo;
    itemCardShortInfo.setText(card.getCardShortInfo());

    TextView itemCardCategory = holder.itemCardCategory;
    itemCardCategory.setText(card.getCardCategory());

    TextView itemCardAge = holder.itemCardAge;
    itemCardAge.setText(""+card.getCardKidsAge());

    TextView itemCardDate = holder.itemCardDate;
    itemCardDate.setText(""+card.getCardDate());

    TextView itemCardPrice = holder.itemCardPrice;
    itemCardPrice.setText(""+card.getCardPrice());

    TextView itemCardLongInfo = holder.itemCardLongInfo;
    itemCardLongInfo.setText(card.getCardLongInfo());

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


        public CardHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }





}
