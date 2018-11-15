package no.hvl.dat159.Entities;

public class Heat {
    boolean poweredOn;

    public Heat (boolean poweredOn){
        this.poweredOn = poweredOn;
    }

    public boolean getState() {
        return poweredOn;
    }

}
