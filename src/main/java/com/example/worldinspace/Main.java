package com.example.worldinspace;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    public static final double WINDOW_WIDTH = 1500;
    public static final double WINDOW_HEIGHT = 800;

    //головний екран
    private static Scene scene;
    private static Stage stage;

    //головна група, в якій містяться об'єкти головного екрану
    private static Pane group = new Pane();

    //гетери
    public static Scene getScene() {
        return scene;
    }
    public static Pane getGroup() {
        return group;
    }
    public static Stage getStage() {
        return stage;
    }

    //сетери
    public static void setStage(Stage stage0) {
        stage = stage0;
    }

    //ініціалізація головного вікна
    @Override
    public void start(Stage primaryStage) throws IOException {
        scene = new Scene(group, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage = primaryStage;
        primaryStage.centerOnScreen();

        Background background = new Background(new BackgroundImage(new Image(Main.class.getResource("background.jpg").toString(), WINDOW_WIDTH, WINDOW_HEIGHT, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
        group.setBackground(background);

        World world = new World(this, group, 30);

        //обробка дії (клацання мишкою на вікно)
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY))
                    WorkWithDialog.display();
            }
        });

        //обробка дії (нажаття кнопки клавіатури)
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                //обробка клавіші INSERT - додати новий мікрооб'єкт
                if (keyEvent.getCode().equals(KeyCode.INSERT)) {
                    AddDialog.display(); //додаємо об'єкт
                }
                //обробка клавіші DELETE - видалити активні мікрооб'єкти
                else if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                    World.removeActiveShip();
                }
                //обробка клавіші ESCAPE - деактивувати активовані мікрооб'єкти
                else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                    World.changeActiveShip();
                }
                else if (keyEvent.getCode().equals(KeyCode.V)) {
                    try {
                        World.copyActiveShip();
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    //обробка WASD для руху виділених мікрооб'єктів
                    if (keyEvent.getCode().equals(KeyCode.W) )
                    {
                        World.moveActiveShip(0.0, -1.0);
                    }
                    if (keyEvent.getCode().equals(KeyCode.A) )
                    {
                        World.moveActiveShip(-1.0, 0.0);
                    }
                    if (keyEvent.getCode().equals(KeyCode.S) )
                    {
                        World.moveActiveShip(0.0, 1.0);
                    }
                    if (keyEvent.getCode().equals(KeyCode.D) )
                    {
                        World.moveActiveShip(1.0, 0.0);
                    }
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                world.lifeCycle();
            }
        };

        animationTimer.start();

        AddingCLI cli = new AddingCLI(world);
        Thread cliThread = new Thread(cli::processCommandLine);
        cliThread.setDaemon(true);
        cliThread.start();
    }

    //функція main
    public static void main(String[] args) {
        launch(args);
    }
}