package com.example.bob.codladzieci;

public class Card {

    private String cardTitle;
    private String cardCategory;
    private int cardKidsAge;
    private int cardDate;
    private int cardPrice;
    private String cardShortInfo;
    private String cardLongInfo;
    private String organizerName;
    private String organizerAddress;
    private String cardPhotoUrl;

    public Card() {
    }

    public Card(String cardTitle, String cardCategory, int cardKidsAge, int cardDate, int cardPrice, String cardShortInfo, String cardLongInfo, String organizerName, String organizerAddress, String cardPhotoUrl) {
        this.cardTitle = cardTitle;
        this.cardCategory = cardCategory;
        this.cardKidsAge = cardKidsAge;
        this.cardDate = cardDate;
        this.cardPrice = cardPrice;
        this.cardShortInfo = cardShortInfo;
        this.cardLongInfo = cardLongInfo;
        this.organizerName = organizerName;
        this.organizerAddress = organizerAddress;
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

    public int getCardKidsAge() {
        return cardKidsAge;
    }

    public void setCardKidsAge(int cardKidsAge) {
        this.cardKidsAge = cardKidsAge;
    }

    public int getCardDate() {
        return cardDate;
    }

    public void setCardDate(int cardDate) {
        this.cardDate = cardDate;
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
        return organizerAddress;
    }

    public void setOrganizatorAddress(String organizatorAddress) {
        this.organizerAddress = organizerAddress;
    }

    public String getCardPhotoUrl() {
        return cardPhotoUrl;
    }

    public void setCardPhotoUrl(String cardPhotoUrl) {
        this.cardPhotoUrl = cardPhotoUrl;
    }
}
