package com.example.worldinspace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class Spaceship implements Cloneable, Comparable<Spaceship> {
    protected static Pane group;
    {
        group = World.getGroup();
    }

    public static final int shipImgSizeX = 50;
    public static final int shipImgSizeY = 50;

    private String name;
    private Label nameL;

    protected Baggage baggage = new Baggage(); //ємність

    protected Image imgShip; //зображення, яке передасться в граф об'єкт зображення
    private ImageView iShip; //граф об'єкт зображення

    private double x, y;
    private double aimx, aimy;

    private int energy;
    protected Line energyL = new Line();

    protected int health;
    protected Line healthL;

    private Polygon polygon; //граф об'єкт активованості
    {
        polygon = new Polygon();
        polygon.setStroke(Color.BLUEVIOLET);
        polygon.setFill(Color.ALICEBLUE);
        polygon.setOpacity(0);
    }
    private boolean isActivated; //буліан "чи активований (чи була нажата кнопка миші на мікро)"

    private boolean isProcessing;

    protected double speed;
    private boolean isProtected; //буліан "чи захищений мікро (чи знаходиться в бункері)"

    protected int numOfShip; //рівень мікро

    private ArrayList<Location> shipsMacro = new ArrayList<>(); //приналежність до макро

    public static Scanner scan;
    static {
        scan = new Scanner(System.in);
    }

    //конструктор///////////////////////////////////////////////////////////////////////////////////////////////////////
    public Spaceship (String name, int energy, int health, double x, double y) {
        //КООРДИНАТИ встановлюємо значення
        this.x = x;
        this.y = y;

        //ІМ'Я встановлюємо значення і розташування
        this.name = name;
        this.nameL = new Label(name + " " + Integer.toString(baggage.getCountOfMeteors()));
        this.nameL.setLayoutX(x);
        this.nameL.setLayoutY(y+shipImgSizeY);
        nameL.setTextFill(Color.WHITE);

        //ЗОБРАЖЕННЯ ініціалізуємо зображення, створюємо для нього картинку на екрані, задаємо розташування
        setImgShip();
        this.iShip = new ImageView(imgShip);
        iShip.setX(x);
        iShip.setY(y);

        //ЗДОРОВ'Я встановлюємо значення і розташування
        this.health = health;
        this.healthL = new Line(x, y-10, x+this.health/2, y-10);
        this.healthL.setStrokeWidth(5);
        this.healthL.setStroke(Color.DARKGREEN);

        //ЕНЕРГІЯ встановлюємо значення і розташування
        this.energy = energy;
        this.energyL = new Line(x, y-20, x+this.energy/2, y-20);
        this.energyL.setStrokeWidth(5);
        this.energyL.setStroke(Color.DARKBLUE);

        //АКТИВНІСТЬ встановлюємо точки полігону, змінюємо на false
        polygon.getPoints().addAll(this.x - 10, this.y - 30, this.x + 60,
                this.y - 30, this.x + 60, this.y + 75, this.x - 10, this.y + 75);
        changeIsActivated(false);

        //додаємо всі попередньо створені об'єкти до групи (контейнера) головного вікна
        group.getChildren().addAll(this.polygon, this.nameL, this.iShip, this.healthL, this.energyL);

        //ШВИДКІСТЬ встановлюємо значення
        setSpeed();
        //ЗАХИЩЕНІСТЬ встановлюємо значення
        this.isProtected = false;
        //РІВЕНЬ МІКРО ініціалізація
        setNumOfShip();

        isProcessing = false;

        //обробка дії (клацання мишкою на мікро) - зміна активності
        iShip.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
                    changeIsActivated();
            }
        });

        //виведення в консоль інфо створеного мікро
        System.out.println("Створено " + toString());
    }

    //конструктор///////////////////////////////////////////////////////////////////////////////////////////////////////
    public Spaceship (String name, int energy, double x, double y) {
        this(name, energy, 100, x, y); //0 = speed
    }

    //ініціалізація зображення (своя для кожного класу мікрооб'єктів)
    protected void setImgShip() {
        try {
            this.imgShip = new Image(Spaceship.class.getResource("Spaceship.png").toURI().toString(), shipImgSizeX, shipImgSizeY, false, false);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    //ініціалізація рівня (свій для кожного класу мікрооб'єктів), його гетер
    protected void setNumOfShip() {
        this.numOfShip = 0;
    }
    public int getNumOfShip() {
        return numOfShip;
    }

    //ініціалізація швидкості в пікселях (своя для кожного класу мікрооб'єктів), її гетер
    protected void setSpeed () {
        this.speed = 5.0;
    }
    public double getSpeed() {
        return speed;
    }

    //сетери і гетери
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        this.nameL.setText(this.name + " " + Integer.toString(baggage.getCountOfMeteors()));
    }
    public int getEnergy() {
        return energy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
        this.energyL.setEndX(x + this.energy/2);
    }
    private int getHealth() {
        return health;
    }
    protected void setHealth(int health) {
        this.health = health;
        healthL.setEndX(x + health/2);
    }
    public int getBaggage() {
        return baggage.getCountOfMeteors();
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public void setCoord(double x, double y) {
        this.x = x;
        this.y = y;

        this.nameL.setText(this.name + " " + Integer.toString(baggage.getCountOfMeteors()));
        //установка розташування імені
        this.nameL.setLayoutX(x);
        this.nameL.setLayoutY(y+shipImgSizeY);

        //установка розташування картинки
        iShip.setX(x);
        iShip.setY(y);

        //установка розташування полігону
        polygon.getPoints().set(0, x - 10);
        polygon.getPoints().set(1, y - 30);
        polygon.getPoints().set(2, x + 60);
        polygon.getPoints().set(3, y - 30);
        polygon.getPoints().set(4, x + 60);
        polygon.getPoints().set(5, y + 75);
        polygon.getPoints().set(6, x - 10);
        polygon.getPoints().set(7, y + 75);

        //установка розташування лінії здоров'я
        this.healthL.setStartX(x);
        this.healthL.setStartY(y-10);
        this.healthL.setEndX(x+this.health/2);
        this.healthL.setEndY(y-10);

        //установка розташування лінії енергії
        this.energyL.setStartX(x);
        this.energyL.setStartY(y-20);
        this.energyL.setEndX(x+this.energy/2);
        this.energyL.setEndY(y-20);
    }
    public boolean getIsActivated() {
        return isActivated;
    }
    public boolean getIsProtected() {
        return isProtected;
    }
    public void setIsProtected(boolean prot) {
        isProtected = prot;
    }

    public void addMacroToShip(Location location) {
        shipsMacro.add(location);
    }
    public void delMacroFromShip(Location location) {
        shipsMacro.remove(location);
    }
    public ArrayList<Location> getShipsMacro() {
        return shipsMacro;
    }

//    protected void energyToAtpole() {
//        energyL.setStroke(Color.DARKBLUE);
//    }
//    protected void atpoleToNull() {
//        group.getChildren().remove(energyL);
//    }

    public void addBaggage() {
        baggage.addCountOfMeteors();
        setName(this.name);
    }
    public void changeIsActivated() {
        if (isActivated) {
            isActivated = false;
            polygon.setOpacity(0);
        } else {
            isActivated = true;
            polygon.setOpacity(0.5);
        }
    }
    public void changeIsActivated(boolean isActivated) {
        if (!isActivated) {
            this.isActivated = false;
            polygon.setOpacity(0);
        } else {
            this.isActivated = true;
            polygon.setOpacity(0.5);
        }
    }
    public void healing() {
        if (health <= 99) {
            setHealth(health+1);
        }
    }
    public void damage() {
        if (health <= 20) {
            this.remove();
        } else {
            this.setHealth(health-20);
        }
    }



    //пересування мікрооб'єкту з його швидкістю
    public void move(double changeX, double changeY) {
        double x = this.x;
        double y = this.y;
        if (0.0 <= (x + changeX) && (x + changeX) <= Main.WINDOW_WIDTH-shipImgSizeX)
            x += changeX;
        if (0.0 <= (y + changeY) && (y + changeY) <= Main.WINDOW_HEIGHT-shipImgSizeY)
            y += changeY;
        setCoord(x, y);
    }

    //автоматичне пересування




    //повернення графічного примітиву у вигляді координат силуета мікрооб'єкта
    public Bounds getBoundsInParent() {
        return iShip.getBoundsInParent();
    }

    //перепис методу клонування об'єкта цього класу інтерфейсом Cloneable
    @Override
    public Object clone() throws CloneNotSupportedException {
        Spaceship cloneSpaceship = new Spaceship(this.getName(), this.getEnergy(), this.getX(), this.getY());
        cloneSpaceship.baggage.setCountOfMeteors(this.baggage.getCountOfMeteors());
        cloneSpaceship.setName(cloneSpaceship.name);
        cloneSpaceship.setHealth(this.health);
        return cloneSpaceship;
    }

    //перепис методу сортування масиву даного класу інтерфейсом Comparable
    @Override
    public int compareTo(Spaceship obj) {
        if (this.getEnergy() - obj.getEnergy() < 0) return -1;
        else if (this.getEnergy() - obj.getEnergy() > 0) return 1;
        else return 0;
    }

    //функція для порівняння двох мікрооб'єктів даного класу
    public boolean equals(Spaceship obj) {
        return Objects.equals(this.name, obj.name) &&
                this.energy == obj.getEnergy() &&
                this.health == obj.getHealth() &&
                this.speed == obj.getSpeed();
    }

    //функція для повернення всіх характеристик мікроооб'єкту у вигляді одного String
    public String toString() {
        return "Ship0" + this.numOfShip + ": " +
                " Ім'я = " + this.name +
                " Енергія = " + this.energy +
                " Здоров'я = " + this.health +
                " Швидкість = " + this.speed;
    }

    //функція для видалення даного графічного мікрооб'єкту
    public void remove() {
        Main.getGroup().getChildren().removeAll(this.iShip, this.nameL, this.energyL, this.healthL, this.polygon);
        World.getarrayShip().remove(this);
        for (Location location: World.getArrayLocation()) {
            if (location.getName().equals(shipsMacro.get(0).getName())) {
                location.delShip(this);
            }
        }
    }
}
