package com.usac.ayd2.musicplayer.router;

import java.io.IOException;

import com.usac.ayd2.musicplayer.config.ThemeManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Router {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void switchScene(String fxmlPath, double width, double height, String theme, boolean resizable)
            throws IOException {

        FXMLLoader loader = new FXMLLoader(Router.class.getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root, width, height);
        primaryStage.setFullScreen(false);
        primaryStage.setMaximized(false);
        primaryStage.setResizable(resizable);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        if (theme != null) {
            ThemeManager.applyInitialTheme(theme);
        }
        primaryStage.show();
    }

}
