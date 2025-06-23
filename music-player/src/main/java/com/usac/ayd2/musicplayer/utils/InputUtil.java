package com.usac.ayd2.musicplayer.utils;

import com.usac.ayd2.musicplayer.config.ThemeManager;

import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class InputUtil {

    public static TextInputDialog returnStyle(String title, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);
        Stage inputStage = (Stage)dialog.getDialogPane().getScene().getWindow();
        inputStage.getIcons().add(new Image(InputUtil.class.getResourceAsStream("/images/logo-1.png")));
        ThemeManager.applyThemeToDialog(dialog);
        return dialog;
    }
    
}
