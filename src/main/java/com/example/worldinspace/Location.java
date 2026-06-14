package com.example.worldinspace;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Location {
    protected Image imgLoc;
    private ImageView iLoc;
    protected Label nameRes;
    private String name;
    private double x, y;
    protected double width, high;
    {
        width = 200;
        high = 200;
    }
    protected int current_time = 0;
    protected ArrayList<Spaceship> places = new ArrayList<>();
    protected int resources = 0;

    public Location(double x, double y, String name, int num) {
        this.x = x;
        this.y = y;
        setImage();
        iLoc = new ImageView(imgLoc);
        iLoc.setX(x);
        iLoc.setY(y);
        this.name = name;
        this.nameRes = new Label(name + " --- " + Integer.toString(num));
        nameRes.setTextFill(Color.WHITE);
        this.nameRes.setLayoutX(x);
        this.nameRes.setLayoutY(y-15);

        World.getGroup().getChildren().addAll(iLoc, this.nameRes);
    }

    protected void setImage() {}
    public void lifeCycle() {}

    //повернення графічного примітиву у вигляді координат силуета макрооб'єкта
    public Bounds getBoundsInParent() {
        return iLoc.getBoundsInParent();
    }

    public void setNameRes(int num) {
        this.nameRes.setText(name + " --- " + Integer.toString(num));
    }

    public String getName() {
        return name;
    }

    public ArrayList<Spaceship> getPlaces() {
        return places;
    }

    protected void addShip(Spaceship relShip) {
        if (!places.contains(relShip)) {
            places.add(relShip);
            this.setNameRes(places.size());
            relShip.addMacroToShip(this);
        }
    }
    public void delShip(Spaceship relShip) {
        if (places.contains(relShip)) {
            places.remove(relShip);
            this.setNameRes(places.size());
            relShip.delMacroFromShip(this);
        }
    }
}
