package com.usac.ayd2.musicplayer.config;

import java.io.IOException;

import com.usac.ayd2.musicplayer.patterns.strategy.ThemeStrategy;
import com.usac.ayd2.musicplayer.router.Router;
import com.usac.ayd2.musicplayer.theme.DarkTheme;
import com.usac.ayd2.musicplayer.theme.LightTheme;

import javafx.scene.Scene;
import javafx.scene.control.Dialog;

public class ThemeManager {

    private static boolean isDark = false;
    private static ThemeStrategy currentTheme = new LightTheme();

    public static void toggleTheme(Scene scene) {
        isDark = !isDark;
        currentTheme = isDark ? new DarkTheme() : new LightTheme();
        currentTheme.applyTheme(scene);
        try {
            PersistenceManager.saveTheme(isDark ? "dark" : "light");
        } catch (IOException e) {
            System.err.println("Error al guardar el tema: " + e.getMessage());
        }
    }

    public static void applyInitialTheme(String themeName) {
        if ("dark".equalsIgnoreCase(themeName)) {
            isDark = true;
            currentTheme = new DarkTheme();
        } else {
            isDark = false;
            currentTheme = new LightTheme();
        }
        Scene scene = Router.getPrimaryStage().getScene();
        if (scene != null) {
            currentTheme.applyTheme(scene);
        }
    }

    public static boolean isDarkMode() {
        return isDark;
    }

    public static void applyThemeToDialog(Dialog<?> dialog) {
        Scene scene = dialog.getDialogPane().getScene();
        if (scene == null) return;
        scene.getStylesheets().clear();
        if (isDark) {
            scene.getStylesheets().add(ThemeManager.class.getResource("/styles/dark.css").toExternalForm());
        } else {
            scene.getStylesheets().add(ThemeManager.class.getResource("/styles/light.css").toExternalForm());
        }
    }

    public static String getCurrentTheme() {
        return isDark ? "Dark" : "Light";
    }

}
