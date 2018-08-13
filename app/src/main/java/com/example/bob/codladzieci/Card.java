package com.example.bob.codladzieci;

public class Card {

    private String cardTitle;
    private String cardCategory;
    private String cardKidsAge;
    private String cardDate;
    private String cardPrice;
    private String cardShortInfo;
    private String cardLongInfo;
    private String organizerName;
    private String organizerAddress;
    private String cardPhotoUrl;

    public Card() {
    }

    public Card(String cardTitle, String cardCategory, String cardKidsAge, String cardDate, String cardPrice, String cardShortInfo, String cardLongInfo, String organizerName, String organizerAddress, String cardPhotoUrl) {
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

    public String getCardKidsAge() {
        return cardKidsAge;
    }

    public void setCardKidsAge(String cardKidsAge) {
        this.cardKidsAge = cardKidsAge;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public String getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(String cardPrice) {
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

    public String getOrganizerAddress() {
        return organizerAddress;
    }

    public void setOrganizerAddress(String organizerAddress) {
        this.organizerAddress = organizerAddress;
    }

    public String getCardPhotoUrl() {
        return cardPhotoUrl;
    }

    public void setCardPhotoUrl(String cardPhotoUrl) {
        this.cardPhotoUrl = cardPhotoUrl;
    }
}
