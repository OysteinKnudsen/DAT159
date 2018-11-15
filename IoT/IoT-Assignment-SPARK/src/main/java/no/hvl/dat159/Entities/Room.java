package no.hvl.dat159.Entities;

import no.hvl.dat159.Actuators.Heater;
import no.hvl.dat159.Sensors.TemperatureSensor;

public class Room {
    private double maxTemp;
    private double minTemp;
    private Heat heat;
    private Temperature temperature;

    public Room (double initialTemp, double maxTemp, double minTemp) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        heat = new Heat(true);
        temperature = new Temperature(initialTemp);
    }

    public Heat getHeat(){
        return heat;
    }

    public Temperature getTemperature () {
        return temperature;
    }

}
