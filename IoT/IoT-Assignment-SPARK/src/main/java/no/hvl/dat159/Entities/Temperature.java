package no.hvl.dat159.Entities;

public class Temperature {

    private double temperature;

    public Temperature (double initialTemp){
        this.temperature = initialTemp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}   
