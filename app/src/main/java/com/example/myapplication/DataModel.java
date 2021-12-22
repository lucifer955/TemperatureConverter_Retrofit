package com.example.myapplication;

public class DataModel {
    private String temperature;

    DataModel(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
