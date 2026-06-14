package com.example.worldinspace;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.net.URISyntaxException;
import java.util.LinkedList;

public class SpaceshipPlus extends Spaceship {

    public SpaceshipPlus(String name, int energy, double x, double y) {
        super(name, energy, x, y);
    }

    @Override
    public void setImgShip() {
        try {
            super.imgShip = new Image(SpaceshipPlus.class.getResource("SpaceshipPlus.png").toURI().toString(), shipImgSizeX, shipImgSizeY, false, false);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setNumOfShip() {
        super.numOfShip = 1;
    }

    @Override
    public void setSpeed() {
        super.speed = 7.0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SpaceshipPlus cloneSpaceshipPlus = new SpaceshipPlus(this.getName(), this.getEnergy(), this.getX(), this.getY());
        cloneSpaceshipPlus.baggage.setCountOfMeteors(this.baggage.getCountOfMeteors());
        cloneSpaceshipPlus.setName(cloneSpaceshipPlus.getName());
        cloneSpaceshipPlus.setHealth(this.health);
        return cloneSpaceshipPlus;
    }
}
