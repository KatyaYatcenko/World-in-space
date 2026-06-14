package com.example.worldinspace;

public class Baggage {

    private int countOfMeteors;

    public Baggage() {
        countOfMeteors = 0;
    }

    public void addCountOfMeteors() {
        countOfMeteors++;
    }

    public int getCountOfMeteors() {
        return countOfMeteors;
    }

    public void setCountOfMeteors(int count) {
        countOfMeteors = count;
    }
}
