package com.example.worldinspace;

import javafx.scene.image.Image;

public class Venera extends Location {
    final private int time_heal = 10;

    public Venera (double x, double y, int num) {
        super(x, y, "Venera", num);
    }

    @Override
    protected void setImage() {
        imgLoc = new Image(Main.class.getResource("venera.png").toString(), width, high, false, false);
    }

    public void lifeCycle() {
        current_time++;
        for (Spaceship relShip: World.getarrayShip()) {
            if (relShip.getBoundsInParent().intersects(this.getBoundsInParent())) {
                addShip(relShip);
                if (current_time == time_heal) {
                    relShip.healing();
                }
            } else {
                delShip(relShip);
            }
        }
        if (current_time == time_heal) {
            current_time = 0;
        }
    }
}
