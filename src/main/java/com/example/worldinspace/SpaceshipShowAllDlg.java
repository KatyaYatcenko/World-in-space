package com.example.worldinspace;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;

//клас вікна для показу всіх наявних об'єктів
public class SpaceshipShowAllDlg {
    public static void display() {
        //створюємо саме вікно
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Всі Spaceship");
        window.setMinWidth(250);
        VBox layout = new VBox(11);
        layout.setAlignment(Pos.CENTER);

        ArrayList<Spaceship> Spaceships = Main.getSpaceships();
        int v1ToScene = 0;
        for (Spaceship ship: Spaceships) { //ітеруємось по масиву Spaceships і додаємо інфо(toString()) його об'єктів в контейнер label
            layout.getChildren().add(new Label(ship.toString()));
            v1ToScene += 30; //рахуємо, як для кожного напису +30 пікселів висоти вікна
        }

        Button okButton = new Button("OK");
        //обробка нажаття на okButton
        okButton.setOnAction(e->{
            window.close();
        });
        layout.getChildren().add(okButton);

        //демонструємо вікно, очікуючи нажаття кнопки okButton
        Scene scene = new Scene(layout, 500, v1ToScene+30); //використовуємо вираховану нами висоту (і +30 для кнопки ОК)
        window.setScene(scene);
        window.showAndWait();
    }
}