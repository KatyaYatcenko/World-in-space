package com.example.worldinspace;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URISyntaxException;
import java.util.LinkedList;

public class SpaceshipPlusPlus extends SpaceshipPlus {

    public SpaceshipPlusPlus(String name, int energy, double x, double y) {
        super(name, energy, x, y);

        World.getGroup().getChildren().remove(energyL);
    }

    @Override
    public void setImgShip() {
        try {
            super.imgShip = new Image(SpaceshipPlusPlus.class.getResource("SpaceshipPlusPlus.png").toURI().toString(), shipImgSizeX, shipImgSizeY, false, false);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setNumOfShip() {
        super.numOfShip = 2;
    }

    @Override
    public void setSpeed() {
        super.speed = 10.0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SpaceshipPlusPlus cloneSpaceshipPlusPlus = new SpaceshipPlusPlus(this.getName(), this.getEnergy(), this.getX(), this.getY());
        cloneSpaceshipPlusPlus.baggage.setCountOfMeteors(this.baggage.getCountOfMeteors());
        cloneSpaceshipPlusPlus.setName(cloneSpaceshipPlusPlus.getName());
        cloneSpaceshipPlusPlus.setHealth(this.health);
        return cloneSpaceshipPlusPlus;
    }
}
