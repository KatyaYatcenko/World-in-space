package com.example.worldinspace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
findButton.setOnAction(new EventHandler<ActionEvent>()
toMacro.setOnAction(new EventHandler<ActionEvent>()
notToMacro.setOnAction(new EventHandler<ActionEvent>()
areMacroButton.setOnAction(new EventHandler<ActionEvent>()
countButton.setOnAction(new EventHandler<ActionEvent>()
 */

//клас вікна для зміни параметрів у певного об'єкта
public class WorkWithDialog {
    public static void display() {
        Stage window0 = new Stage();
        window0.initModality(Modality.APPLICATION_MODAL);
        window0.setTitle("Дії з Ship");
        window0.centerOnScreen();
        VBox layout = new VBox(11);
        layout.setAlignment(Pos.CENTER);

        //вивести всі мікро
        Button showAllButton = new Button("Вивести всі");
        showAllButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //створюємо саме вікно
                Stage window1 = new Stage();
                window1.initModality(Modality.APPLICATION_MODAL);
                window1.setTitle("Всі Ship");
                window1.centerOnScreen();
                VBox layout = new VBox(11);
                layout.setAlignment(Pos.CENTER);

                //створення полів із інфо об'єктів
                ArrayList<Spaceship> relShip = World.getarrayShip();
                int v1ToScene = 0;
                for (Spaceship ship: relShip) {
                    layout.getChildren().add(new Label(ship.toString()));
                    v1ToScene += 30;
                }

                Button okButton = new Button("OK");
                //обробка нажаття на okButton
                okButton.setOnAction(e->{
                    window1.close();
                    window0.close();
                });
                layout.getChildren().add(okButton);

                Scene scene = new Scene(layout, 500, v1ToScene+30);
                window1.setScene(scene);
                window1.showAndWait();
            }
        });

        //space
        Label simple1 = new Label("");

        //змінити/видалити/знайти об'єкт
        Label redDelFindLabel = new Label("                 Вибір об'єкту для\nредагування/видалення/знаходження");
        ComboBox<String> namesBox = new ComboBox<>();
        for (Spaceship ship: World.getarrayShip())
            namesBox.getItems().add(ship.toString());
        boolean isSelectionModelRDF = false;
        if (World.getarrayShip().size() != 0) {
            namesBox.getSelectionModel().select(0);
            isSelectionModelRDF = true;
        }

        Button redactButton = new Button("Редагувати");
        Button removeButton = new Button("Видалити");
        Button findButton = new Button("Знайти");
        boolean finalIsSelectionModelRDF = isSelectionModelRDF;
        redactButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (finalIsSelectionModelRDF) {
                    //передавання в функцію індекса вибраного об'єкта із namesBox
                    ChangeParamDialog.display(namesBox.getSelectionModel().getSelectedIndex());
                    window0.close();
                }
            }
        });
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (finalIsSelectionModelRDF) {
                    //індекс вибраного об'єкта із namesBox
                    int index = namesBox.getSelectionModel().getSelectedIndex();
                    //видалення вибраного мікро
                    World.removeShip(World.getarrayShip().get(index));
                    window0.close();
                }
            }
        });
        findButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (finalIsSelectionModelRDF) {
                    //індекс вибраного об'єкта із namesBox
                    int index = namesBox.getSelectionModel().getSelectedIndex();
                    //функція пошуку
                    World.findShipGraph(index);
                    window0.close();
                }
            }
        });

        //space
        Label simple2 = new Label("");
        //додання граф об'єктів в сцену
        layout.getChildren().addAll(showAllButton, simple1, redDelFindLabel, namesBox, redactButton, removeButton, findButton, simple2);

        //вивести перелік належать вказаному/не належить жодному макро
        Button areMacroButton = new Button("Вивести перелік");
        areMacroButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                World.areShipToMacroGraph();
                window0.close();
            }
        });
        Label simple4 = new Label(""); //пропуск

        //сортування за іменем/енергією/місцеположенням
        Button sortButton = new Button("Сортувати");
        sortButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                World.sortShipGraph();
                window0.close();
            }
        });
        Label simple6 = new Label(""); //пропуск

        //підрахунок за к-стю мікро/к-стю акт мікро/к-стб НЕ акт мікро
        Button countButton = new Button("Підрахувати");
        countButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                World.countShipGraph();
                window0.close();
            }
        });

        //space
        Label simple7 = new Label("");
        //додання граф об'єктів в сцену
        layout.getChildren().addAll(areMacroButton, simple4, sortButton, simple6, countButton, simple7);

        //обробка ОК батон
        Button okButton = new Button("OK");
        okButton.setOnAction(e->{
            window0.close();
        });

        //додання граф об'єктів в сцену
        layout.getChildren().add(okButton);

        Scene scene = new Scene(layout, 250, 600);
        window0.setScene(scene);
        window0.showAndWait();
    }
}