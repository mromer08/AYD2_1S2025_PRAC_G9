package com.usac.ayd2.musicplayer.controllers.auth;

import java.io.IOException;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import com.usac.ayd2.musicplayer.config.ThemeManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class AuthLayoutController {

    private FontIcon sunIcon = new FontIcon(FontAwesomeSolid.SUN);
    private FontIcon moonIcon = new FontIcon(FontAwesomeSolid.MOON);
    private boolean isDark = false;

    @FXML
    private Button switcher;

    @FXML
    private Tooltip switcherT;

    @FXML
    VBox views;

    @FXML
    private void initialize() {
        sunIcon.setFill(Color.WHITE);
        sunIcon.setIconSize(16);
        moonIcon.setFill(Color.WHITE);
        moonIcon.setIconSize(16);
        switcher.setGraphic(moonIcon);
        switcherT.setText("Tema Oscuro");
        switcherT.setShowDelay(new Duration(100));
        switcher.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setUserData(this);
            }
        });
        loadForm("login");
    }

    @FXML
    private void toggleTheme(ActionEvent event) {
        Scene scene = switcher.getScene();
        ThemeManager.toggleTheme(scene);
        isDark = !isDark;
        if (isDark) {
            switcher.setGraphic(sunIcon);
            switcherT.setText("Tema Claro");
        } else {
            switcher.setGraphic(moonIcon);
            switcherT.setText("Tema Oscuro");
        }
    }

    public void loadForm(String formName) {
        try {
            Node form = FXMLLoader.load(getClass().getResource("/fxml/" + formName + ".fxml"));
            views.getChildren().clear(); // 💡 Esta línea elimina el formulario anterior
            views.getChildren().add(switcher);
            views.getChildren().add(form);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
