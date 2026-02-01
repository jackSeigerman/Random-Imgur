package com.randomimgur;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main entry point for the Random Image Generator application.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        RandomImgurApp app = new RandomImgurApp(primaryStage);
        Scene scene = new Scene(app.getRoot(), 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        
        primaryStage.setTitle("Random Image Generator");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        
        // Set app icon
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/RandomImageGeneratorLogo.png")));
        } catch (Exception e) {
            // Icon not found, continue without it
        }
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
