package com.example.worldinspace;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//клас вікна для зміни параметрів у певного об'єкта
public class ChangeParamDialog {
    public static void display(int index) {
        //створюємо саме вікно
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Зміна параметрів Ship");
        window.setMinWidth(250);
        VBox layout = new VBox(11);
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("Введіть нові параметри");

        //ім'я
        Label nameLabel = new Label();
        nameLabel.setText("Ім'я:");
        TextField nameText = new TextField(); //поле для вводу імені

        //енергія
        Label energyLabel = new Label();
        energyLabel.setText("Енергія:");
        TextField energyText = new TextField(); //поле для вводу енергії

        //координати
        Label xLabel = new Label();
        xLabel.setText("Координата X:");
        TextField xText = new TextField(); //поле для вводу координати Х
        Label yLabel = new Label();
        yLabel.setText("Координата Y:");
        TextField yText = new TextField(); //поле для вводу координати Х

        Button okButton = new Button("OK");
        //обробка нажаття на okButton
        okButton.setOnAction(e->{
            Spaceship relShip = World.getarrayShip().get(index);

            //витягуємо значення із поля імені
            String changeName = relShip.getName();
            String nameStr = nameText.getText();
            if (!nameStr.equals(""))
                changeName = nameStr;

            //витягуємо значення із поля енергії
            int changeEnergy = relShip.getEnergy();
            String energyStr = energyText.getText();
            int energyInt;
            try {
                energyInt = Integer.parseInt(energyStr);
                if (0 <= energyInt && energyInt <= 100)
                    changeEnergy = energyInt;
            } catch (Exception exc) {
                System.out.println("Exc = " + exc);
            }

            //витягуємо значення із полів координат
            double changeX = relShip.getX();
            String xStr = xText.getText();
            double xDouble;
            try {
                xDouble = Double.parseDouble(xStr);
                if (0.0f <= xDouble && xDouble <= Main.WINDOW_WIDTH)
                    changeX = xDouble;
            } catch (Exception exc) {
                System.out.println("Exc = " + exc);
            }
            double changeY = relShip.getY();
            String yStr = yText.getText();
            double yDouble;
            try {
                yDouble = Double.parseDouble(yStr);
                if (0.0f <= yDouble && yDouble <= 700.0f)
                    changeY = yDouble;
            } catch (Exception exc) {
                System.out.println("Exc = " + exc);
            }

            //виконуємо зміну параметрів
            World.changeParamShip(index, changeName, changeEnergy, changeX, changeY);

            window.close();
        });

        //додаємо граф об'єкти до групи
        layout.getChildren().addAll(label, nameLabel, nameText, energyLabel, energyText, xLabel, xText, yLabel, yText, okButton);

        Scene scene = new Scene(layout, 200, 400);
        window.setScene(scene);
        window.showAndWait();
    }
}
