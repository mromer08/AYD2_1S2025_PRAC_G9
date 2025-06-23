package com.usac.ayd2.musicplayer.theme;

import com.usac.ayd2.musicplayer.patterns.strategy.ThemeStrategy;

import javafx.scene.Scene;

public class DarkTheme implements ThemeStrategy {

    @Override
    public void applyTheme(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("/styles/dark.css").toExternalForm());
    }
    
}
