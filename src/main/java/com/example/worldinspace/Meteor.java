package com.example.worldinspace;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;

public class Meteor {
    private Image imgMeteor;
    private ImageView iMeteor;

    private static Random rand;
    static {
        rand = new Random(System.currentTimeMillis());
    }

    public Meteor(double screenWX, double screenWY) {
        imgMeteor = new Image(Main.class.getResource("meteor.png").toString(), 10, 10, false, false);
        iMeteor = new ImageView(imgMeteor);
        iMeteor.setX(rand.nextDouble() * screenWX);
        iMeteor.setY(rand.nextDouble() * screenWY);
//        World.getGroup().getChildren().add(iMeteor);
        World.getGroup().getChildren().add(iMeteor);
    }

    //повернення графічного примітиву у вигляді координат силуета мікрооб'єкта
    public Bounds getBoundsInParent() {
        return iMeteor.getBoundsInParent();
    }

    //функція для видалення даного графічного мікрооб'єкту
    public void remove() {
        World.getGroup().getChildren().remove(this.iMeteor);
    }
}
