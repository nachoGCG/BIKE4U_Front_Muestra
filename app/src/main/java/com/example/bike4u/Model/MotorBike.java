package com.example.bike4u.Model;

public class MotorBike {
    private String model;
    private String brand;
    private int ph;

    public MotorBike(String model, String brand, int ph) {
        this.model = model;
        this.brand = brand;
        this.ph = ph;
    }

    public MotorBike() {

    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPh() {
        return ph;
    }

    public void setPh(int ph) {
        this.ph = ph;
    }
}
