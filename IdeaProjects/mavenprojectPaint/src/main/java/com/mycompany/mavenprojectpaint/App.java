package com.mycompany.mavenprojectpaint;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class App extends Application {

    private Shape figura;
    private double offsetX;
    private double offsetY;

    private boolean dibujando = false;
    private Polyline polyline;

    private static BorderPane root;
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        root = new BorderPane();
        Scene scene = new Scene(root, 500, 500);

        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: white;");
        root.setCenter(canvas);

        MenuBar menuBar = new MenuBar();
        Menu menuFiguras = new Menu("Figuras");

        MenuItem cuadradoItem = new MenuItem("Cuadrado");
        cuadradoItem.setOnAction(event -> {
            figura = createRectangle(100, 100, Color.RED);
            canvas.getChildren().add(figura);
        });

        MenuItem trianguloItem = new MenuItem("Triángulo");
        trianguloItem.setOnAction(event -> {
            figura = createTriangle(100, Color.RED);
            canvas.getChildren().add(figura);
        });

        MenuItem rectanguloItem = new MenuItem("Rectángulo");
        rectanguloItem.setOnAction(event -> {
            figura = createRectangle(150, 100, Color.RED);
            canvas.getChildren().add(figura);
        });

        MenuItem circuloItem = new MenuItem("Círculo");
        circuloItem.setOnAction(event -> {
            figura = createCircle(50, Color.RED);
            canvas.getChildren().add(figura);
        });

        MenuItem dibujarItem = new MenuItem("Linea   ");
        dibujarItem.setOnAction(event -> {
            dibujando = true;
            polyline = new Polyline();
            polyline.setStroke(Color.GREEN);
            canvas.getChildren().add(polyline);
        });

        MenuItem borrarTodoItem = new MenuItem("Borrar Todo");
        borrarTodoItem.setOnAction(event -> {
            canvas.getChildren().clear();
        });

        menuFiguras.getItems().addAll(dibujarItem, cuadradoItem, trianguloItem, rectanguloItem, circuloItem, borrarTodoItem);
        menuBar.getMenus().add(menuFiguras);
        root.setTop(menuBar);

        canvas.setOnMousePressed(event -> {
            if (figura != null) {
                offsetX = event.getX();
                offsetY = event.getY();
                figura.setLayoutX(offsetX);
                figura.setLayoutY(offsetY);
            }
        });

        canvas.setOnMouseDragged(event -> {
            if (figura != null) {
                double x = event.getX() - offsetX;
                double y = event.getY() - offsetY;
                figura.setLayoutX(x);
                figura.setLayoutY(y);
            }
        });

        canvas.setOnScroll(event -> {
            if (figura != null) {
                double delta = event.getDeltaY();
                double scaleX = figura.getScaleX() + delta * 0.01;
                double scaleY = figura.getScaleY() + delta * 0.01;
                figura.setScaleX(scaleX);
                figura.setScaleY(scaleY);
            }
        });

        canvas.setOnMouseMoved(event -> {
            if (dibujando) {
                double x = event.getX();
                double y = event.getY();
                polyline.getPoints().addAll(x, y);
            }
        });

        canvas.setOnMouseClicked(event -> {
            if (dibujando) {
                dibujando = false;
                polyline = null;
            }
        });

        primaryStage.setTitle("Paint");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Rectangle createRectangle(double width, double height, Color color) {
        Rectangle rectangle = new Rectangle(width, height, color);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        return rectangle;
    }

    private Shape createTriangle(double size, Color color) {
        double halfSize = size / 2;
        double height = Math.sqrt(3) * halfSize;

        double[] points = {
                0, height,
                -halfSize, 0,
                halfSize, 0
        };

        Polygon triangle = new Polygon(points);
        triangle.setFill(color);
        triangle.setStroke(Color.BLACK);
        triangle.setStrokeWidth(2);
        return triangle;
    }

    private Circle createCircle(double radius, Color color) {
        Circle circle = new Circle(radius, color);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        return circle;
    }

    public static void setRoot(String root) {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
