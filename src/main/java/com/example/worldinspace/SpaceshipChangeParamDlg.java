package com.example.worldinspace;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//тут операції ідентичні SpaceshipAddDlg. Відмінність - в кінці замість add(додати) ми робимо set(встановити нові данні)
//клас вікна для зміни параметрів у певного об'єкта
public class SpaceshipChangeParamDlg {
    public static void display(int index) {
        //створюємо саме вікно
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Зміна параметрів Spaceship");
        window.setMinWidth(250);
        VBox layout = new VBox(11);
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("Введіть нові параметри");

        Label nameLabel = new Label();
        nameLabel.setText("Ім'я:");
        TextField nameText = new TextField(); //поле для вводу імені

        Label energyLabel = new Label();
        energyLabel.setText("Енергія:");
        TextField energyText = new TextField(); //поле для вводу енергії

        Button okButton = new Button("OK");
        //обробка нажаття на okButton
        okButton.setOnAction(e->{
            //витягуємо значення із поля імені
            String nameStr = nameText.getText();
            if (nameStr.equals(""))
                nameStr = "Spaceship";
            Main.getSpaceships().get(index).setName(nameStr);

            //витягуємо значення із поля енергії
            String energyStr = energyText.getText();
            int tryInt;
            int energyInt;
            try {
                tryInt = Integer.parseInt(energyStr);
            } catch (Exception exc) {
                tryInt = 100;
            }
            if (0 <= tryInt && tryInt <= 100)
                energyInt = tryInt;
            else
                energyInt = 100;
            Main.getSpaceships().get(index).setEnergy(energyInt);

            window.close();
        });

        layout.getChildren().addAll(label, nameLabel, nameText, energyLabel, energyText, okButton);

        //демонструємо вікно, очікуючи нажаття кнопки okButton
        Scene scene = new Scene(layout, 200, 200);
        window.setScene(scene);
        window.showAndWait();
    }
}