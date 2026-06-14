package com.example.worldinspace;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class World {
    private int current_time = 0;

    //головне вікно і група, їх геттери та сеттери
    private static Main window;
    private static Pane group;
    public static Pane getGroup() {
        return group;
    }

    //масив мікрооб'єктів, його геттер
    private static ArrayList<Spaceship> arrayShip = new ArrayList<>();
    public static ArrayList<Spaceship> getarrayShip() {
        return arrayShip;
    }

    //масив об'єктів класу Meteors, його геттер
    private static ArrayList<Meteor> arrayMeteor = new ArrayList<>();
    public static ArrayList<Meteor> getArrayMeteor() {
        return arrayMeteor;
    }

    private static ArrayList<Location> arrayLocation = new ArrayList<>();
    public static ArrayList<Location> getArrayLocation() {
        return arrayLocation;
    }

    //попереднє створення макрооб'єктів
    Venera venera1;
    Venera venera2;
    Pluton pluton1;
    Pluton pluton2;
    Collapsar collapsar;

    //конструктор
    public World(Main main, Pane group, int countOfMeteor) {
        this.window = main;
        this.group = group;

        collapsar = new Collapsar();
        venera1 = new Venera(50.0, 50.0, 0);
        venera2 = new Venera(50.0, 400.0, 0);
        pluton1 = new Pluton(500.0, 200.0, 0);
        pluton2 = new Pluton(500.0, 550.0, 0);
        arrayLocation.add(venera1);
        arrayLocation.add(venera2);
        arrayLocation.add(pluton1);
        arrayLocation.add(pluton2);
        arrayLocation.add(collapsar);

//        addMeteor(countOfMeteor);

        arrayShip.add(new Spaceship("spShip", 0, 700.0, 100.0));
        arrayShip.add(new SpaceshipPlus("plus", 0, 500.0, 500.0));
        arrayShip.add(new SpaceshipPlusPlus("plusPlus", 0, 400.0, 100.0));
    }


    public void lifeCycle() {
        current_time++;

        collapsar.lifeCycle();
        venera1.lifeCycle();
        venera2.lifeCycle();
        pluton1.lifeCycle();
        pluton2.lifeCycle();
    }



    //робота з мікрооб'єктами//////////////////////////

    //додавання мікрооб'єкта
    public static void addShip(Spaceship newShip) {
        arrayShip.add(newShip);
    }
    //додавання мікрооб'єкта з параметрами
    public static void addParamShip(int numOfShip, String name, int energy, double x, double y) {
        switch (numOfShip) {
            case 0:
                arrayShip.add(new Spaceship(name, energy, x, y));
                break;
            case 1:
                arrayShip.add(new SpaceshipPlus(name, energy, x, y));
                break;
            case 2:
                arrayShip.add(new SpaceshipPlusPlus(name, energy, x, y));
                break;
        }
    }
    //зміна параметрів мікрооб'єкта
    public static void changeParamShip(int index, String name, int energy, double x, double y) {
        Spaceship relShip = arrayShip.get(index);
        relShip.setName(name);
        relShip.setEnergy(energy);
        relShip.setCoord(x, y);
    }
    //видалення мікрооб'єкта
    public static void removeShip(Spaceship relShip) {
        relShip.remove();
    }

    //отримання масиву активних мікрооб'єктів
    public static ArrayList<Spaceship> getActiveShip() {
        ArrayList<Spaceship> relshipntShips = new ArrayList<>();
        for (Spaceship ship: arrayShip) {
            if (ship.getIsActivated())
                relshipntShips.add(ship);
        }
        return relshipntShips;
    }
    //видалення активних мікрооб'єктів
    public static void removeActiveShip() {
        for (Spaceship relShip: getActiveShip()) {
            relShip.remove();
        }
    }
    //деактивування активних мікрооб'єктів
    public static void changeActiveShip() {
        for (Spaceship ship : getActiveShip())
            ship.changeIsActivated(false);
    }
    //пересунути активні мікрооб'єкти (його рух)
    public static void moveActiveShip(double changeX, double changeY) {
        for(Spaceship relShip: getActiveShip()) {
            relShip.move(changeX*relShip.getSpeed(), changeY*relShip.getSpeed());
            tryCatchMeteor(relShip);
        }
    }
    //копіювати активні мікрооб'єкти
    public static void copyActiveShip() throws CloneNotSupportedException {
        for (Spaceship relShip: getActiveShip()) {
            switch (relShip.getNumOfShip()) {
                case 0:
                    Spaceship newSpaceship = (Spaceship) relShip.clone();
                    addShip(newSpaceship);
                    break;
                case 1:
                    SpaceshipPlus newSpaceshipPlus = (SpaceshipPlus) relShip.clone();
                    addShip(newSpaceshipPlus);
                    break;
                case 2:
                    SpaceshipPlusPlus newSpaceshipPlusPlus = (SpaceshipPlusPlus) relShip.clone();
                    addShip(newSpaceshipPlusPlus);
                    break;
            }
        }
    }
    //перевірити на перетин з енергією' 
    private static void tryCatchMeteor(Spaceship relShip) {
        ArrayList<Meteor> relMeteors = new ArrayList<>();
        for (Meteor met: arrayMeteor) {
            if (relShip.getBoundsInParent().intersects(met.getBoundsInParent())) {
                if (relShip instanceof SpaceshipPlusPlus) {
                    relShip.addBaggage();
                } else {
                    relShip.damage();
                }
                relMeteors.add(met);
                met.remove();
            }
        }
        arrayMeteor.removeAll(relMeteors);
    }

    //знайти мікро за індексом та вивести його коорд і макро
    private static String findShip(int index) {
        String coordOfFind = arrayShip.get(index).getX() + "; " + World.getarrayShip().get(index).getY();
        String macrosOfFind = "Не належить до жодного";
        for (Location location: arrayShip.get(index).getShipsMacro()) {
            macrosOfFind = (location.getName() + "\n");
        }
        return "Координати об'єкта:\n" + coordOfFind +
                "\nНалежеість до макрооб'єктів:\n" + macrosOfFind;
    }
    public static void findShipGraph(int index) {
        //створюємо саме вікно
        Stage window1 = new Stage();
        window1.initModality(Modality.APPLICATION_MODAL);
        window1.setTitle("Знайдено " + arrayShip.get(index).getName());
        window1.centerOnScreen();
        VBox layout = new VBox(11);
        layout.setAlignment(Pos.CENTER);

        //створення полів із інфо об'єкту
        Label infoLabel = new Label(findShip(index));

        //кнопка ОК
        Button okButton = new Button("OK");
        okButton.setOnAction(e->{
            window1.close();
        });

        //додання об'єктів в сцену
        layout.getChildren().addAll(infoLabel, okButton);

        //завершення сцени
        Scene scene = new Scene(layout, 300, 200);
        window1.setScene(scene);
        window1.showAndWait();
    }
    public static void findShipCons(int index) {
        System.out.println("Знайдено " + arrayShip.get(index).getName());
        System.out.println(findShip(index));
    }

    //вивести перелік належать вказаному/не належить жодному макро
    private static String toMacro(String nameMacro) {
        String listShipToMacro = "";
        //перелік об'єктів, що належать до
        for (Location location: arrayLocation) {
            if (location.getName().equals(nameMacro)) {
                for (Spaceship ship: location.getPlaces()) {
                    listShipToMacro += (ship.toString() + "\n");
                    System.out.println(ship.toString());
                }
            }
        }
        return listShipToMacro;
    }
    private static String notToMacro() {
        String listShipNotToMacro = "";
        //перелік об'єктів, що не належать до жодного
        for (Spaceship relShip: arrayShip) {
            if (relShip.getShipsMacro().size() == 0) {
                listShipNotToMacro += (relShip.toString() + "\n");
            }
        }
        return listShipNotToMacro;
    }
    public static void areShipToMacroGraph() {
        Stage window1 = new Stage();
        window1.initModality(Modality.APPLICATION_MODAL);
        window1.setTitle("Перелік");
        window1.centerOnScreen();
        VBox layout = new VBox(11);
        layout.setAlignment(Pos.CENTER);

        Label areMacroLabel = new Label("Перелік об'єктів");
        ComboBox<String> macrosBox = new ComboBox<>();
        macrosBox.getItems().addAll("Venera", "Pluton", "Collapsar");
        macrosBox.getSelectionModel().select("Venera");
        Button toMacro = new Button("які належать вказаному макро");
        toMacro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String nameMacro = "Venera";
                if (macrosBox.getSelectionModel().isSelected(1)) {
                    nameMacro = "Pluton";
                } else if (macrosBox.getSelectionModel().isSelected(2)) {
                    nameMacro = "Collapsar";
                }
//                String nameMacro = macrosBox.getSelectionModel().toString();
                Stage window2 = new Stage();
                window2.initModality(Modality.APPLICATION_MODAL);
                window2.setTitle("Перелік належних до " + nameMacro);
                window2.centerOnScreen();
                VBox layout = new VBox(11);
                layout.setAlignment(Pos.CENTER);

                Label listShipToMacro = new Label(toMacro(nameMacro));

                Button okButton = new Button("OK");
                okButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        window2.close();
                    }
                });

                layout.getChildren().addAll(listShipToMacro, okButton);

                Scene scene = new Scene(layout, 300, 200);
                window2.setScene(scene);
                window2.showAndWait();
                window1.close();
            }
        });
        Button notToMacro = new Button("які НЕ належать жодному макро");
        notToMacro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage window2 = new Stage();
                window2.initModality(Modality.APPLICATION_MODAL);
                window2.setTitle("Перелік НЕ належних до жодного");
                window2.centerOnScreen();
                VBox layout = new VBox(11);
                layout.setAlignment(Pos.CENTER);

                Label listNotToMacro = new Label(notToMacro());

                Button okButton = new Button("OK");
                okButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        window2.close();
                    }
                });

                layout.getChildren().addAll(listNotToMacro, okButton);

                Scene scene = new Scene(layout, 300, 200);
                window2.setScene(scene);
                window2.showAndWait();
                window1.close();
            }
        });

        layout.getChildren().addAll(areMacroLabel, macrosBox, toMacro, notToMacro);

        Scene scene = new Scene(layout, 300, 200);
        window1.setScene(scene);
        window1.showAndWait();
    }
    public static void shipToMacroCons(String nameMacro) {
        System.out.println("Мікрооб'єкти, що належать до " + nameMacro + ":");
        System.out.println(toMacro(nameMacro));
    }
    public static void shipNotToMacroCons() {
        System.out.println("Мікрооб'єкти, що не належать до жодного:");
        System.out.println(notToMacro());
    }

    //сортування за іменем/енергією/місцеположенням
    private static String listShipForName() {
        class ComparName implements Comparator<Spaceship> {
            @Override
            public int compare(Spaceship obj1, Spaceship obj2) {
                return obj1.getName().compareTo(obj2.getName());
            }
        }
        ArrayList<Spaceship> relarrayShip = (ArrayList<Spaceship>) arrayShip.clone();
        Collections.sort(relarrayShip, new ComparName());

        String listName = relarrayShip.stream()
                .map(Spaceship::toString)
                .collect(Collectors.joining("\n"));
        return listName;
    } //Comparator, nested class, stream API
    private static String listShipForEnergy() {
        ArrayList<Spaceship> relarrayShip = (ArrayList<Spaceship>) arrayShip.clone();
        Collections.sort(relarrayShip);

        String listEnergy = relarrayShip.stream()
                .map(Spaceship::toString)
                .collect(Collectors.joining("\n"));
        return listEnergy;
    } //Comparable
    private static String listShipForCoord() {
        ArrayList<Spaceship> relarrayShip = (ArrayList<Spaceship>) arrayShip.clone();
        Collections.sort(relarrayShip, new Comparator<Spaceship>() {
            @Override
            public int compare(Spaceship obj1, Spaceship obj2) {
                return (int) ((obj1.getX() + obj1.getY()) - (obj2.getX() + obj2.getY()));
            }
        });

        String listCoord = relarrayShip.stream()
                .map(Spaceship::toString)
                .collect(Collectors.joining("\n"));
        return listCoord;

    } //Comparator, анонімний клас, stream API
    public static void sortShipGraph() {
        Stage window1 = new Stage();
        window1.initModality(Modality.APPLICATION_MODAL);
        window1.setTitle("Сортування");
        window1.centerOnScreen();
        VBox layout = new VBox(11);
        layout.setAlignment(Pos.CENTER);

        Label sortLabel = new Label("Сортування об'єктів:");

        RadioButton nameSortRB = new RadioButton("За іменем");
        RadioButton energySortRB = new RadioButton("За к-стю енергії");
        RadioButton coordSortRB = new RadioButton("За місцезнаходженням");

        ToggleGroup toggleGroup = new ToggleGroup();
        nameSortRB.setToggleGroup(toggleGroup);
        nameSortRB.setSelected(true);
        energySortRB.setToggleGroup(toggleGroup);
        coordSortRB.setToggleGroup(toggleGroup);

        Button doSortButton = new Button("Сортувати");
        doSortButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage window2 = new Stage();
                window2.initModality(Modality.APPLICATION_MODAL);
                window2.setTitle("Список сортованих");
                window2.centerOnScreen();
                VBox layout = new VBox(11);
                layout.setAlignment(Pos.CENTER);

                Label listForSort;

                if (toggleGroup.getSelectedToggle().equals(nameSortRB)) {
                    listForSort = new Label(listShipForName());
                } else if (toggleGroup.getSelectedToggle().equals(energySortRB)) {
                    listForSort = new Label(listShipForEnergy());
                } else {
                    listForSort = new Label(listShipForCoord());
                }

                Button okButton = new Button("OK");
                okButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        window2.close();
                    }
                });

                layout.getChildren().addAll(listForSort, okButton);

                Scene scene = new Scene(layout, 400, (World.getarrayShip().size() * 40) + 30);
                window2.setScene(scene);
                window2.showAndWait();
                window1.close();
            }
        });

        layout.getChildren().addAll(sortLabel, nameSortRB, energySortRB, coordSortRB, doSortButton);

        Scene scene = new Scene(layout, 300, 200);
        window1.setScene(scene);
        window1.showAndWait();
    }
    public static void sortShipForNameCons() {
        System.out.println("Сортовані мікрооб'єкти за іменем:");
        System.out.print(listShipForName());
    }
    public static void sortShipForEnergyCons() {
        System.out.println("Сортовані мікрооб'єкти за рівнем енергії:");
        System.out.print(listShipForEnergy());
    }
    public static void sortShipForCoordCons() {
        System.out.println("Сортовані мікрооб'єкти за місцезнаходженням:");
        System.out.print(listShipForCoord());

    }

    //підрахунок за к-стю мікро/к-стю акт мікро/к-стб НЕ акт мікро
    private static String numShipMicro() {
        return Integer.toString(World.getarrayShip().size());
    }
    private static String numShipActive() {
        return Integer.toString(getActiveShip().size());
    }
    private static String numShipNonActive() {
        return Integer.toString(getarrayShip().size() - getActiveShip().size());
    }
    public static void countShipGraph() {
        Stage window1 = new Stage();
        window1.initModality(Modality.APPLICATION_MODAL);
        window1.setTitle("Підрахування");
        window1.centerOnScreen();
        VBox layout = new VBox(11);
        layout.setAlignment(Pos.CENTER);

        Label numLabel = new Label("Підрахунок об'єктів:");

        RadioButton numRB = new RadioButton("К-сть мікрооб");
        RadioButton numActiveRB = new RadioButton("К-сть активних мікрооб");
        RadioButton numNonActiveRB = new RadioButton("К-сть НЕактивних мікрооб");

        ToggleGroup toggleGroup = new ToggleGroup();
        numRB.setToggleGroup(toggleGroup);
        numRB.setSelected(true);
        numActiveRB.setToggleGroup(toggleGroup);
        numNonActiveRB.setToggleGroup(toggleGroup);

        Button doNumButton = new Button("Підрахувати");
        doNumButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage window2 = new Stage();
                window2.initModality(Modality.APPLICATION_MODAL);
                window2.setTitle("Підраховані об'єкти");
                window2.centerOnScreen();
                VBox layout = new VBox(11);
                layout.setAlignment(Pos.CENTER);

                Label label;
                Label valueNum;

                if (toggleGroup.getSelectedToggle().equals(numRB)) {
                    label = new Label("К-сть мікрооб'єктів:");
                    valueNum = new Label(numShipMicro());
                } else if (toggleGroup.getSelectedToggle().equals(numActiveRB)) {
                    label = new Label("К-сть активних мікрооб'єктів:");
                    valueNum = new Label(numShipActive());
                } else {
                    label = new Label("К-сть НЕ активних мікрооб'єктів:");
                    valueNum = new Label(numShipNonActive());
                }

                Button okButton = new Button("OK");
                okButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        window2.close();
                    }
                });

                layout.getChildren().addAll(label, valueNum, okButton);

                Scene scene = new Scene(layout, 100, 100);
                window2.setScene(scene);
                window2.showAndWait();
                window1.close();
            }
        });

        layout.getChildren().addAll(numLabel, numRB, numActiveRB, numNonActiveRB, doNumButton);

        Scene scene = new Scene(layout, 300, 200);
        window1.setScene(scene);
        window1.showAndWait();
    }
    public static void countShipMicroCons() {
        System.out.println("К-сть мікрооб'єктів:");
        System.out.println(numShipMicro());
    }
    public static void countShipActiveCons() {
        System.out.println("К-сть активних мікрооб'єктів:");
        System.out.println(numShipActive());
    }
    public static void countShipNonActiveCons() {
        System.out.println("К-сть не активних мікрооб'єктів:");
        System.out.println(numShipNonActive());
    }



    //робота з Meteor////////////////////////////////////

    //додавання об'єктів Meteor
    public static void addMeteor (int count) {
        for (int i = 0; i < count; i++)
            arrayMeteor.add(new Meteor(Main.WINDOW_WIDTH-10.0, Main.WINDOW_HEIGHT-10.0)); //(Main.getScreenWX(), Main.getScreenWY()));
    }
    //видалення об'єкта
    public static void removeMeteor(Meteor relMet) {
        relMet.remove();
        World.getArrayMeteor().remove(relMet);
    }



    //робота з AddingCLI/////////////////////////////////////

    private void ensureFXThread(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            Platform.runLater(action);
        }
    }

    public void CLIadd (int numOfShip, String name, int energy, double x, double y){
        ensureFXThread(() -> addParamShip(numOfShip, name, energy, x, y));
    }

    public void CLIredact (int index, String name, int energy, double x, double y){
        ensureFXThread(() -> changeParamShip(index, name, energy, x, y));
    }


    public void CLImove(double x, double y) {
        ensureFXThread(() -> moveActiveShip(x, y));
    }

    public void CLIescape() {
        ensureFXThread(() -> changeActiveShip());
    }

    public void CLIremove() {
        ensureFXThread(() -> removeActiveShip());
    }

    public void CLIcopy() {
        ensureFXThread(() -> {
            try {
                copyActiveShip();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void CLIfind(int index) {
        ensureFXThread(() -> findShipCons(index));
    }

    public void CLItoMacro(String name) {
        ensureFXThread(() -> shipToMacroCons(name));
    }

    public void CLInotToMacro() {
        ensureFXThread(() -> shipNotToMacroCons());
    }

    public void CLIsortName() {
        ensureFXThread(() -> sortShipForNameCons());
    }

    public void CLIsortEnergy() {
        ensureFXThread(() -> sortShipForEnergyCons());
    }

    public void CLIsortCoord() {
        ensureFXThread(() -> sortShipForCoordCons());
    }

    public void CLIcount() {
        ensureFXThread(() -> countShipMicroCons());
    }

    public void CLIcountAct() {
        ensureFXThread(() -> countShipActiveCons());
    }

    public void CLIcountNonAct() {
        ensureFXThread(() -> countShipNonActiveCons());
    }


    public void CLIexit() {
        ensureFXThread(() ->Platform.exit() );
    }
}
