package com.usac.ayd2.musicplayer.utils;

import com.usac.ayd2.musicplayer.config.ThemeManager;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertUtil {

    public static void showAlert(Alert.AlertType type, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(StringUtil.capitalizeWords(type.toString()));
        alert.setHeaderText(null);
        alert.setContentText(content);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(AlertUtil.class.getResourceAsStream("/images/logo-1.png")));
        ThemeManager.applyThemeToDialog(alert);
        alert.showAndWait();
    }
    
}
