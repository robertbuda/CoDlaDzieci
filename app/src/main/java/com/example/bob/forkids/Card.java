package com.example.bob.forkids;

public class Card {

    private String cardTitle;
    private String cardCategory;
    private int cardKidsAgeFrom;
    private int cardKidsAgeTo;
    private int cardPrice;
    private String cardShortInfo;
    private String cardLongInfo;
    private String organizerName;
    private String organizatorAddress;
    private String cardPhotoUrl;

    public Card() {
    }

    public Card(String cardTitle, String cardCategory, int cardKidsAgeFrom, int cardKidsAgeTo, int cardPrice, String cardShortInfo, String cardLongInfo, String organizerName, String organizatorAddress, String cardPhotoUrl) {
        this.cardTitle = cardTitle;
        this.cardCategory = cardCategory;
        this.cardKidsAgeFrom = cardKidsAgeFrom;
        this.cardKidsAgeTo = cardKidsAgeTo;
        this.cardPrice = cardPrice;
        this.cardShortInfo = cardShortInfo;
        this.cardLongInfo = cardLongInfo;
        this.organizerName = organizerName;
        this.organizatorAddress = organizatorAddress;
        this.cardPhotoUrl = cardPhotoUrl;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardCategory() {
        return cardCategory;
    }

    public void setCardCategory(String cardCategory) {
        this.cardCategory = cardCategory;
    }

    public int getCardKidsAgeFrom() {
        return cardKidsAgeFrom;
    }

    public void setCardKidsAgeFrom(int cardKidsAgeFrom) {
        this.cardKidsAgeFrom = cardKidsAgeFrom;
    }

    public int getCardKidsAgeTo() {
        return cardKidsAgeTo;
    }

    public void setCardKidsAgeTo(int cardKidsAgeTo) {
        this.cardKidsAgeTo = cardKidsAgeTo;
    }

    public int getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(int cardPrice) {
        this.cardPrice = cardPrice;
    }

    public String getCardShortInfo() {
        return cardShortInfo;
    }

    public void setCardShortInfo(String cardShortInfo) {
        this.cardShortInfo = cardShortInfo;
    }

    public String getCardLongInfo() {
        return cardLongInfo;
    }

    public void setCardLongInfo(String cardLongInfo) {
        this.cardLongInfo = cardLongInfo;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getOrganizatorAddress() {
        return organizatorAddress;
    }

    public void setOrganizatorAddress(String organizatorAddress) {
        this.organizatorAddress = organizatorAddress;
    }

    public String getCardPhotoUrl() {
        return cardPhotoUrl;
    }

    public void setCardPhotoUrl(String cardPhotoUrl) {
        this.cardPhotoUrl = cardPhotoUrl;
    }
}
