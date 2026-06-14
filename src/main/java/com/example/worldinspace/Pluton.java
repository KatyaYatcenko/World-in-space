package com.example.worldinspace;

import javafx.scene.image.Image;

public class Pluton extends Location {
    final private int time_spawn = 60;

    public Pluton (double x, double y, int num) {
        super(x, y, "Pluton", num);
    }

    @Override
    protected void setImage() {
        imgLoc = new Image(Main.class.getResource("pluton.png").toString(), width, high, false, false);
    }

    public void lifeCycle() {
        current_time++;
        if (current_time == time_spawn) {
            this.addResources();
        }
        for (Spaceship relShip: World.getarrayShip()) {
            if (!(relShip instanceof SpaceshipPlusPlus)) {
                if (relShip.getBoundsInParent().intersects(this.getBoundsInParent())) {
                    addShip(relShip);
                    relShip.setEnergy(relShip.getEnergy() + resources);
                    resources = 0;
                } else {
                    delShip(relShip);
                }
                this.setNameRes(resources);
            }
        }
    }

    private void addResources() {
        resources++;
        this.setNameRes(resources);
        current_time = 0;
    }
}
