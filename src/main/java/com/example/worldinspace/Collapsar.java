package com.example.worldinspace;

import javafx.scene.image.Image;

public class Collapsar extends Location {
    final private int time_spawn = 1000;

    public Collapsar () {
        super(1100.0, 350.0, "Collapsar", 0);
    }

    @Override
    protected void setImage() {
        imgLoc = new Image(Main.class.getResource("collapsar.png").toString(), width, high, false, false);
    }

    public void lifeCycle() {
        current_time++;
        for (Spaceship relShip : World.getarrayShip()) {
            if (relShip.getBoundsInParent().intersects(this.getBoundsInParent())) {
                this.addShip(relShip);
            } else {
                delShip(relShip);
            }
        }
        if (current_time == time_spawn) {
            if (places.size() == 0) {
                World.addMeteor(10);
            }
            current_time = 0;
        }
    }
}
