package com.example.worldinspace;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//клас вікна для додавання об'єкта з можливістю вибрати параметри
public class SpaceshipAddDlg {
    public static void display(double x, double y) {
        //створюємо саме вікно
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Додавання Spaceship"); //назва вікна
        window.setMinWidth(250);
        VBox layout = new VBox(11); //контейнер, в який складатимемо всі кнопочки
        layout.setAlignment(Pos.CENTER); //позиція всіх кнопочок - центр

        //створюємо об'єкти, що будуть в цьому вікні
        Label label = new Label("Введіть параметри для Spaceship"); //напис

        Label nameLabel = new Label(); //напис
        nameLabel.setText("Ім'я:"); //встановлюємо текст для напису
        TextField nameText = new TextField(); //поле для вводу імені

        Label energyLabel = new Label(); //напис
        energyLabel.setText("Енергія:"); //встановлюємо текст для напису
        TextField energyText = new TextField(); //поле для вводу енергії

        Button okButton = new Button("OK"); //кнопка
        //обробка нажаття на okButton
        okButton.setOnAction(e->{
            //витягуємо значення із поля імені
            String nameStr = nameText.getText();
            //перевірка: якщо пусте, то ім'я буде Spaceship
            if (nameStr.equals(""))
                nameStr = "Spaceship";

            //витягуємо значення із поля енергії
            String energyStr = energyText.getText();
            int tryInt;
            int energyInt;
            //перевірка: спочатку пробуєм перетворити String на int
            //далі перевіряємо, щоб введене значення було між 0 і 100. якщо ні, то ставимо значення 0
            try {
                tryInt = Integer.parseInt(energyStr);
            } catch (Exception exc) {
                tryInt = 100;
            }
            if (0 <= tryInt && tryInt <= 100)
                energyInt = tryInt;
            else
                energyInt = 100;

            //створюємо об'єкт Spaceship з параметрами від користувача
            Main.getSpaceships().add(new com.example.worldinspace.Spaceship(nameStr, energyInt, x, y));

            window.close(); //закриваємо вікно
        });

        //додаємо всі об'єкти в контейнер цього вікна
        layout.getChildren().addAll(label, nameLabel, nameText, energyLabel, energyText, okButton);

        //демонструємо вікно, очікуючи нажаття кнопки okButton
        Scene scene = new Scene(layout, 200, 200);
        window.setScene(scene);
        window.showAndWait();
    }
}
