package com.example.smartt;

public class SensorsValue {

     private  String Temperature;
     private  String Humidity;
     private  String Water;
     private String Distance;

    public SensorsValue(String temperature, String humidity, String water,String distance) {
        Temperature = temperature;
        Humidity = humidity;
        Water = water;
        Distance = distance;
    }

    public String getTemperature() {
        return Temperature;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getWater() {
        return Water;
    }

    public void setWater(String water) {
        Water = water;
    }
}
