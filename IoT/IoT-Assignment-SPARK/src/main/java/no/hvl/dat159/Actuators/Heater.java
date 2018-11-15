package no.hvl.dat159.Actuators;

public class Heater {
    boolean poweredOn;

    public Heater (boolean poweredOn) {
        this.poweredOn = poweredOn;
    }

    public boolean getState (){
        return poweredOn;
    }

    public void setState(boolean state){
        poweredOn = state;
    }





}
