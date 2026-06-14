package com.example.worldinspace;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//клас вікна для показу/зміни/видалення певного об'єкта
public class SpaceshipChangeDlg {
    public static void display() {
        //створюємо саме вікно
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Вибір Spaceship");
        window.setMinWidth(250);
        VBox layout = new VBox(11);
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("Оберіть об'єкт для зміни"); //напис
        ComboBox<String> namesBox = new ComboBox<>(); //комбо бокс - має список String-ів
        for (com.example.worldinspace.Spaceship ship: Main.getSpaceships()) { //ітеруємося по масиву наявних Spaceship-ів
            String str = ship.toString(); //отримуємо інфо про об'єкт за допомогою метода його класу toString()
            namesBox.getItems().add(str); //додаємо пункт списку з цим інфо(str) в комбо бокс
        }

        RadioButton showAllButton = new RadioButton("Вивести всі"); //створюємо радіо кнопку
        RadioButton redactButton = new RadioButton("Редагувати");
        RadioButton deleteButton = new RadioButton("Видалити");
        ToggleGroup toggleGroup = new ToggleGroup(); //створюємо групу для радіо кнопок(р.к.)
        showAllButton.setToggleGroup(toggleGroup); //додаємо першу радіо кнопку showAllButton до групи р.к.
        showAllButton.setSelected(true); //встановлюємо початкое значення р.к. showAllButton - true (вона зразу буде вибраною)
        redactButton.setToggleGroup(toggleGroup);
        deleteButton.setToggleGroup(toggleGroup);

        Button okButton = new Button("OK"); //просто кнопка
        //обробка нажаття на okButton
        okButton.setOnAction(e->{
            int index = namesBox.getSelectionModel().getSelectedIndex(); //отримуємо індекс(номер) вибраного пункту зі списку чек бокс
            //для нас цей індекс == номеру вибраного об'єкта із масиву Spaceships
            if (toggleGroup.getSelectedToggle().equals(showAllButton)) { // якщо вибрана радіо кнопка(р.к.) == showAllButton
                SpaceshipShowAllDlg.display(); //то відкриваємо вікно з описом усіх об'єктів
            } else if (toggleGroup.getSelectedToggle().equals(redactButton)) { //р.к. == redactButton
                SpaceshipChangeParamDlg.display(index); //то відкриваємо вікно для редагування
            } else { //р.к. == deleteButton
                Main.getSpaceships().get(index).removeSpaceship(); //видаляємо всі графічні штучки даного об'єкта
                Main.getSpaceships().remove(index); //а потім ивдаляємо даний об'єкт з масиву об'єктів Spaceships в Main
            }
            window.close();
        });

        //додаємо всі об'єкти в контейнер цього вікна
        layout.getChildren().addAll(label, namesBox, showAllButton, redactButton, deleteButton, okButton);

        //демонструємо вікно, очікуючи нажаття кнопки okButton
        Scene scene = new Scene(layout, 200, 200);
        window.setScene(scene);
        window.showAndWait();
    }
}
