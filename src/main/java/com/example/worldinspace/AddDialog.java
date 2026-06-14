package com.example.worldinspace;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

//клас вікна для додавання об'єкта з можливістю вибрати параметри
public class AddDialog {
    public static void display() {
        //створюємо саме вікно
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Додавання Ship");
        window.setMinWidth(250);
        VBox layout = new VBox(11);
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("Введіть параметри");// для Ship");

        //ім'я
        Label nameLabel = new Label();
        nameLabel.setText("Ім'я:");
        TextField nameText = new TextField(); //поле для вводу імені

        //рівень об'єкту
        Label levelLabel = new Label("Рівень об'єкту:");
        RadioButton level00RB = new RadioButton("1   ");
        RadioButton level01RB = new RadioButton("2   ");
        RadioButton level02RB = new RadioButton("3");
        ToggleGroup toggleGroup = new ToggleGroup();
        level00RB.setToggleGroup(toggleGroup);
        level01RB.setToggleGroup(toggleGroup);
        level02RB.setToggleGroup(toggleGroup);
        level00RB.setSelected(true);
        HBox radioButtons = new HBox(level00RB, level01RB, level02RB);
        radioButtons.setAlignment(Pos.CENTER);

        //енергія
        Label energyLabel = new Label("Енергія:");
        ComboBox<String> energyCB = new ComboBox<>();
        energyCB.getItems().addAll("0", "25", "50", "75", "100");
        energyCB.getSelectionModel().select(0);

        //координати
        Label xLabel = new Label("Координата X:");
        TextField xText = new TextField();
        Label yLabel = new Label("Координата Y:");
        TextField yText = new TextField();

        Button okButton = new Button("OK");
        //обробка нажаття на okButton
        okButton.setOnAction(e->{

            //витягуємо номер рівня об'єкту із радіо кнопок
            int numOfShip = 2;
            if (toggleGroup.getSelectedToggle().equals(toggleGroup.getToggles().get(0)))
                numOfShip = 0;
            else if (toggleGroup.getSelectedToggle().equals(toggleGroup.getToggles().get(1)))
                numOfShip = 1;

            //витягуємо значення із поля імені
            String nameStr = nameText.getText();
            if (nameStr.equals(""))
                nameStr = "Ship0" + numOfShip;

            //витягуємо значення із поля енергії
            int energyInt = 0;
            try {
                energyInt = Integer.parseInt(energyCB.getSelectionModel().getSelectedItem().toString());
            } catch (NullPointerException exc) {}

            //витягуємо значення координат
            double xDouble = 0.0;
            try {
                xDouble = Double.parseDouble(xText.getText());
            } catch (Exception exc) {}
            double yDouble = 0.0;
            try {
                yDouble = Double.parseDouble(yText.getText());
            } catch (Exception exc) {}
            if ((0.0 <= xDouble && xDouble <= Main.WINDOW_WIDTH)&&(0.0 <= yDouble && yDouble <= Main.WINDOW_HEIGHT)) {}
            else {
                xDouble = 0.0;
                yDouble = 0.0;
            }

            //додавання мікро за заданими параметрами
            World.addParamShip(numOfShip, nameStr, energyInt, xDouble, yDouble);

            window.close();
        });

        layout.getChildren().addAll(label, nameLabel, nameText, levelLabel, radioButtons, energyLabel, energyCB, xLabel, xText, yLabel, yText, okButton);

        Scene scene = new Scene(layout, 200, 400);
        window.setScene(scene);
        window.showAndWait();
    }
}
