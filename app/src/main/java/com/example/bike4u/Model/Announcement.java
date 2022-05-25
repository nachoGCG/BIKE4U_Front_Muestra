package com.example.bike4u.Model;

import android.widget.TextView;

public class Announcement {
    private int id;
    private String title;
    private String bikeBrand;
    private String bikeModel;
    private int bikePh;
    private int km;
    private Boolean isLimited;
    private String description;
    private String nameUser;
    private int price;

    public Announcement(String title, String bikeBrand,
                        String bikeModel, int bikePh, int km,
                        Boolean isLimited, String description,
                        String nameUser, int price) {
//        this.id = id;
        this.title = title;
        this.bikeBrand = bikeBrand;
        this.bikeModel = bikeModel;
        this.bikePh = bikePh;
        this.km = km;
        this.isLimited = isLimited;
        this.description = description;
        this.nameUser = nameUser;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBikeBrand() {
        return bikeBrand;
    }

    public void setBikeBrand(String bikeBrand) {
        this.bikeBrand = bikeBrand;
    }

    public String getBikeModel() {
        return bikeModel;
    }

    public void setBikeModel(String bikeModel) {
        this.bikeModel = bikeModel;
    }

    public int getBikePh() {
        return bikePh;
    }

    public void setBikePh(int ph) {
        this.bikePh = bikePh;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public Boolean getLimited() {
        return isLimited;
    }

    public void setLimited(Boolean limited) {
        isLimited = limited;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public int getPrice() {
        return price;
    }

    public void setPrecio(int precio) {
        this.price = price;
    }
}
